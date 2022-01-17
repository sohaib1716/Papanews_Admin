package darren.gcptts.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.mannan.translateapi.Language;
import com.mannan.translateapi.TranslateAPI;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import darren.gcptts.BuildConfig;
import darren.gcptts.R;
import darren.googlecloudtts.GoogleCloudTTS;
import darren.googlecloudtts.GoogleCloudTTSFactory;
import darren.googlecloudtts.parameter.AudioConfig;
import darren.googlecloudtts.parameter.AudioEncoding;
import darren.googlecloudtts.parameter.VoiceSelectionParams;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static darren.gcptts.main.global.enaudio;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextInputEditText t1,longphp;
    TextInputEditText  t2;
    Button browse, upload, srcselect;
    ImageView img, srcImage;
    Bitmap bitmap, srcBitmap;
    String encodeImageString, encodeSourceImage;
    EditText videophp, newsphp, locationphp,starttime,endtime;
    String catSelected, image_location;
    Button saved;
    CheckBox eng, hind;
    int[] check = {0};
    String audio = "1";
    DatePicker picker;
    Button btnGet;
    String catcat;
    TextView tvw;
    ProgressBar progressBar;
    int k;

    CheckBox sel_sports, sel_entertain, sel_business, sel_startup, sel_tech,
            sel_politics, sel_international, sel_miscell, sel_influence;

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    Button setdate, playAudio;

//    List<String> global.selected_Cateory = new ArrayList<>();

    private static final String url_politics = "http://papanews.in/PapaNews/uploadPolitics.php";
    private static final String url_entertain = "http://papanews.in/PapaNews/uploadEntertain.php";
    private static final String url_business = "http://papanews.in/PapaNews/uploadBusiness.php";
    private static final String url_startup = "http://papanews.in/PapaNews/uploadStartup.php";
    private static final String url_sports = "http://papanews.in/PapaNews/uploadSports.php";
    private static final String url_technology = "http://papanews.in/PapaNews/uploadTechnology.php";
    private static final String url_international = "http://papanews.in/PapaNews/uploadInternational.php";
    private static final String url_miscel = "http://papanews.in/PapaNews/uploadMiscel.php";
    private static final String url_influences = "http://papanews.in/PapaNews/uploadInfluence.php";

    String[] users = {"Politics", "Technology", "Sports", "Startup", "Entertaintment",
            "Business", "International", "Influence", "Miscellaneous"};

    //voice
    private MainViewModel mMainViewModel;
    GoogleCloudTTS googleCloudTTS;
    String enaudioen = "hello";
    String enaudiohi = "";
    String enen = "";
    private MediaPlayer mMediaPlayer;
    Button review_news;
    String languageselect = "English";

    Button re_hindi, re_english;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = (ImageView) findViewById(R.id.img);
        srcImage = (ImageView) findViewById(R.id.srcimage);
        srcselect = (Button) findViewById(R.id.selectImage);
        upload = (Button) findViewById(R.id.upload);
        browse = (Button) findViewById(R.id.browse);
        saved = (Button) findViewById(R.id.savedPosts);
        eng = findViewById(R.id.English);
        hind = findViewById(R.id.hindi);
        re_english = findViewById(R.id.english_review);
        re_hindi = findViewById(R.id.hindi_review);
        playAudio = findViewById(R.id.play_audio);
        progressBar = findViewById(R.id.progress);

        starttime = findViewById(R.id.startTime);
        endtime = findViewById(R.id.endTime);


//        select category
        sel_business = findViewById(R.id.business_sel);
        sel_entertain = findViewById(R.id.entertain_sel);
        sel_influence = findViewById(R.id.influences_sel);
        sel_international = findViewById(R.id.international_sel);
        sel_startup = findViewById(R.id.startup_sel);
        sel_miscell = findViewById(R.id.miscell_sel);
        sel_tech = findViewById(R.id.tech_sel);
        sel_sports = findViewById(R.id.sports_sel);
        sel_politics = findViewById(R.id.politics_sel);


        //voice
        ButterKnife.bind(this);
        googleCloudTTS = GoogleCloudTTSFactory.create(BuildConfig.API_KEY);
        mMainViewModel = new MainViewModel(getApplication(), googleCloudTTS);
        //

        tvw = (TextView) findViewById(R.id.selectDate);
        picker = (DatePicker) findViewById(R.id.datePicker1);


        longphp = (TextInputEditText) findViewById(R.id.longPhp);
        videophp = (EditText) findViewById(R.id.videoId);
        newsphp = (EditText) findViewById(R.id.NewsSource);
        locationphp = (EditText) findViewById(R.id.location);
        t1 = (TextInputEditText) findViewById(R.id.t1);
        t2 = (TextInputEditText) findViewById(R.id.t2);


        re_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertEnglish();
                languageselect = "English";
            }
        });

        re_hindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertHindi();
                languageselect = "Hindi";
                re_english.setBackgroundColor(Color.parseColor("#251F1F"));
                re_hindi.setBackgroundColor(Color.parseColor("#F4931D"));

            }
        });



        playAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] longDescription = {longphp.getText().toString().trim()};

                mMainViewModel.initTTSVoice("en-IN", "en-IN-Wavenet-C", 0.0f, 1.0f);
                mMainViewModel.speak(longDescription[0])
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                            }

                            @Override
                            public void onComplete() {
//                            makeToast("Speak success", false);
                                enen = mMainViewModel.getena();
                                Log.e("convertedaudio :: ", enen);

                                mMediaPlayer = new MediaPlayer();
                                try {
                                    mMediaPlayer.setDataSource(enen);
                                    mMediaPlayer.prepare();
                                    mMediaPlayer.start();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
//                            makeToast("Speak failed " + e.getMessage(), true);
                                Log.e("TAG", "Speak failed in hindi", e);
                            }
                        });
            }
        });


        final AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autocomplete);
        autoCompleteTextView.setAdapter(new PlaceAutoSuggestAdapter(MainActivity.this, android.R.layout.simple_list_item_1));

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Address : ", autoCompleteTextView.getText().toString());
                LatLng latLng = getLatLngFromAddress(autoCompleteTextView.getText().toString());
                if (latLng != null) {
                    Log.d("Lat Lng : ", " " + latLng.latitude + " " + latLng.longitude);
                    Address address = getAddressFromLatLng(latLng);
                    if (address != null) {
                        Log.d("Address : ", "" + address.toString());
                        Log.d("Address Line : ", "" + address.getAddressLine(0));
                        Log.d("Phone : ", "" + address.getPhone());
                        Log.d("Pin Code : ", "" + address.getPostalCode());
                        Log.d("Feature : ", "" + address.getFeatureName());
                        Log.d("More : ", "" + address.getLocality());
                    } else {
                        Log.d("Adddress", "Address Not Found");
                    }
                } else {
                    Log.d("Lat Lng", "Lat Lng Not Found");
                }

            }
        });


        Spinner spin = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);


        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(MainActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Browse Image"), 1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        srcselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(MainActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Browse Image"), 2);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker.setVisibility(View.VISIBLE);
                picker.bringToFront();
                tvw.setText(picker.getDayOfMonth() + "/" + (picker.getMonth() + 1) + "/" + picker.getYear());


                uploaddatatodb();
            }
        });

        saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, savedPosts.class);
                startActivity(i);

            }
        });


    }

    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri filepath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);
            } catch (Exception ex) {
            }
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            Uri filepath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                srcImage.setImageBitmap(bitmap);
                encode(bitmap);
            } catch (Exception ex) {
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encode(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytesofimage = byteArrayOutputStream.toByteArray();
        encodeSourceImage = android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }


    private void encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytesofimage = byteArrayOutputStream.toByteArray();
        encodeImageString = android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    private void convertEnglish() {
        final String[] title = {t1.getText().toString().trim()};
        final String[] shdesc = {t2.getText().toString().trim()};
        final String[] longDescription = {longphp.getText().toString().trim()};
        final String[] newsSourcename = {newsphp.getText().toString().trim()};
        final String[] location = {locationphp.getText().toString().trim()};


        re_english.setBackgroundColor(Color.parseColor("#F4931D"));
        re_hindi.setBackgroundColor(Color.parseColor("#251F1F"));

        if (!title[0].equals("")) {
            TranslateAPI translateAPI = new TranslateAPI(Language.AUTO_DETECT, Language.ENGLISH, title[0]);
            translateAPI.setTranslateListener(new TranslateAPI.TranslateListener() {
                @Override
                public void onSuccess(String translatedText) {
                    title[0] = translatedText;
                    Toast.makeText(MainActivity.this, title[0], Toast.LENGTH_SHORT).show();
                    Log.e("translated title :", title[0]);
                    t1.setText(title[0]);
                    check[0]++;
                }

                @Override
                public void onFailure(String ErrorText) {
                    Toast.makeText(MainActivity.this, ErrorText, Toast.LENGTH_SHORT).show();
                }
            });
        }


        if (!shdesc[0].equals("")) {
            TranslateAPI translateAPI1 = new TranslateAPI(Language.AUTO_DETECT, Language.ENGLISH, shdesc[0]);
            translateAPI1.setTranslateListener(new TranslateAPI.TranslateListener() {
                @Override
                public void onSuccess(String translatedText) {
                    shdesc[0] = translatedText;
                    Log.e("translated short :", shdesc[0]);
                    t2.setText(shdesc[0]);
                    check[0]++;
                }

                @Override
                public void onFailure(String ErrorText) {
                    Toast.makeText(MainActivity.this, ErrorText, Toast.LENGTH_SHORT).show();
                }
            });
        }


        if (!longDescription[0].equals("")) {
            TranslateAPI translateAPI2 = new TranslateAPI(Language.AUTO_DETECT, Language.ENGLISH, longDescription[0]);
            translateAPI2.setTranslateListener(new TranslateAPI.TranslateListener() {
                @Override
                public void onSuccess(String translatedText) {
                    longDescription[0] = translatedText;
                    Log.e("translated long :", longDescription[0]);
                    check[0]++;
                    longphp.setText(longDescription[0]);

                }

                @Override
                public void onFailure(String ErrorText) {
                    Toast.makeText(MainActivity.this, ErrorText, Toast.LENGTH_SHORT).show();
                }
            });
        }


        if (!newsSourcename[0].equals("")) {
            TranslateAPI translateAPI3 = new TranslateAPI(Language.AUTO_DETECT, Language.ENGLISH, newsSourcename[0]);
            translateAPI3.setTranslateListener(new TranslateAPI.TranslateListener() {
                @Override
                public void onSuccess(String translatedText) {
                    newsSourcename[0] = translatedText;
                    check[0]++;
                    newsphp.setText(newsSourcename[0]);
                }

                @Override
                public void onFailure(String ErrorText) {
                    Toast.makeText(MainActivity.this, "Failed to translate", Toast.LENGTH_SHORT).show();
                }
            });
        }


        if (!location[0].equals("")) {
            TranslateAPI translateAPI4 = new TranslateAPI(Language.AUTO_DETECT, Language.ENGLISH, location[0]);
            translateAPI4.setTranslateListener(new TranslateAPI.TranslateListener() {
                @Override
                public void onSuccess(String translatedText) {
                    location[0] = translatedText;
                    check[0]++;
                    locationphp.setText(location[0]);
                }

                @Override
                public void onFailure(String ErrorText) {
                    Toast.makeText(MainActivity.this, "Failed to translate", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void convertHindi() {
        final String[] title = {t1.getText().toString().trim()};
        final String[] shdesc = {t2.getText().toString().trim()};
        final String[] longDescription = {longphp.getText().toString().trim()};
        final String[] newsSourcename = {newsphp.getText().toString().trim()};
        final String[] location = {locationphp.getText().toString().trim()};

        Log.e("titleitle", title[0]);


        if (!title[0].equals("")) {
            TranslateAPI translateAPI = new TranslateAPI(Language.AUTO_DETECT, Language.HINDI, title[0]);
            translateAPI.setTranslateListener(new TranslateAPI.TranslateListener() {
                @Override
                public void onSuccess(String translatedText) {
                    title[0] = translatedText;
                    Toast.makeText(MainActivity.this, title[0], Toast.LENGTH_SHORT).show();
                    Log.e("translated title :", title[0]);
                    t1.setText(title[0]);
                    check[0]++;
                }

                @Override
                public void onFailure(String ErrorText) {
                    Toast.makeText(MainActivity.this, ErrorText, Toast.LENGTH_SHORT).show();
                }
            });
        }


        if (!shdesc[0].equals("")) {
            TranslateAPI translateAPI1 = new TranslateAPI(Language.AUTO_DETECT, Language.HINDI, shdesc[0]);
            translateAPI1.setTranslateListener(new TranslateAPI.TranslateListener() {
                @Override
                public void onSuccess(String translatedText) {
                    shdesc[0] = translatedText;
                    Log.e("translated short :", shdesc[0]);
                    t2.setText(shdesc[0]);
                    check[0]++;
                }

                @Override
                public void onFailure(String ErrorText) {
                    Toast.makeText(MainActivity.this, ErrorText, Toast.LENGTH_SHORT).show();
                }
            });
        }


        if (!longDescription[0].equals("")) {
            TranslateAPI translateAPI2 = new TranslateAPI(Language.AUTO_DETECT, Language.HINDI, longDescription[0]);
            translateAPI2.setTranslateListener(new TranslateAPI.TranslateListener() {
                @Override
                public void onSuccess(String translatedText) {
                    longDescription[0] = translatedText;
                    Log.e("translated long :", longDescription[0]);
                    check[0]++;
                    longphp.setText(longDescription[0]);

                }

                @Override
                public void onFailure(String ErrorText) {
                    Toast.makeText(MainActivity.this, ErrorText, Toast.LENGTH_SHORT).show();
                }
            });
        }


        if (!newsSourcename[0].equals("")) {
            TranslateAPI translateAPI3 = new TranslateAPI(Language.AUTO_DETECT, Language.HINDI, newsSourcename[0]);
            translateAPI3.setTranslateListener(new TranslateAPI.TranslateListener() {
                @Override
                public void onSuccess(String translatedText) {
                    newsSourcename[0] = translatedText;
                    check[0]++;
                    newsphp.setText(newsSourcename[0]);
                }

                @Override
                public void onFailure(String ErrorText) {
                    Toast.makeText(MainActivity.this, "Failed to translate", Toast.LENGTH_SHORT).show();
                }
            });
        }


        if (!location[0].equals("")) {
            TranslateAPI translateAPI4 = new TranslateAPI(Language.AUTO_DETECT, Language.HINDI, location[0]);
            translateAPI4.setTranslateListener(new TranslateAPI.TranslateListener() {
                @Override
                public void onSuccess(String translatedText) {
                    location[0] = translatedText;
                    check[0]++;
                    locationphp.setText(location[0]);
                }

                @Override
                public void onFailure(String ErrorText) {
                    Toast.makeText(MainActivity.this, "Failed to translate", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void selectcat(){
        global.selected_Cateory.clear();
        if (sel_politics.isChecked()) {
            global.selected_Cateory.add(url_politics);
        }
        if (sel_influence.isChecked()) {
            global.selected_Cateory.add(url_influences);
        }
        if (sel_sports.isChecked()) {
            global.selected_Cateory.add(url_sports);
        }
        if (sel_entertain.isChecked()) {
            global.selected_Cateory.add(url_entertain);
        }
        if (sel_business.isChecked()) {
            global.selected_Cateory.add(url_business);
        }
        if (sel_tech.isChecked()) {
            global.selected_Cateory.add(url_technology);
        }
        if (sel_international.isChecked()) {
            global.selected_Cateory.add(url_international);
        }
        if (sel_startup.isChecked()) {
            global.selected_Cateory.add(url_startup);
        }
        if (sel_miscell.isChecked()) {
            global.selected_Cateory.add(url_miscel);
        }
    }


    private void uploaddatatodb() {

        progressBar.setVisibility(View.VISIBLE);
        selectcat();


        Log.e("selected Category ", String.valueOf(global.selected_Cateory));

        Toast.makeText(getApplicationContext(), "Uploading News in " + languageselect, Toast.LENGTH_LONG).show();
        final String title = t1.getText().toString().trim();
        final String shdesc = t2.getText().toString().trim();
        final String longDescription = longphp.getText().toString().trim();
        final String videoyoutube = videophp.getText().toString().trim();
        final String newsSourcename = newsphp.getText().toString().trim();
        final String location = locationphp.getText().toString().trim();
        final String date = tvw.getText().toString().trim();
        final String start_time = starttime.getText().toString().trim();
        final String end_time = endtime.getText().toString().trim();

//        EDVLXK5vkc8=!13=!18

        final String final_youtube = videoyoutube + "=!" + start_time + "=!" + end_time;


        Log.e("checkinglength long :: " , String.valueOf(longDescription.length()));
        Log.e("checkinglength short :: " , String.valueOf(shdesc.length()));


        mMainViewModel.initTTSVoice("en-IN", "en-IN-Wavenet-C", 0.0f, 1.0f);
        mMainViewModel.speak(longDescription)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onComplete() {
//                            makeToast("Speak success", false);
                        enaudioen = mMainViewModel.getena();
                        Log.e("pppppp", enaudioen);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
//                            makeToast("Speak failed " + e.getMessage(), true);
                        Log.e("TAG", "Speech failed in english", e);
                    }
                });



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (k = 0; k < global.selected_Cateory.size(); k++) {
                    Log.e("selected cat list ", String.valueOf(global.selected_Cateory.get(k)));
                    StringRequest request = new StringRequest(Request.Method.POST, global.selected_Cateory.get(k), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            t1.setText("");
//                            t2.setText("");
//                            longphp.setText("");
//                            videophp.setText("");
//                            newsphp.setText("");
//                            img.setImageResource(R.drawable.ic_launcher_foreground);
                            if(response.equals("File Uploaded Successfully")){
                                progressBar.setVisibility(View.GONE);
                            }

                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("t1", title);
                            map.put("t2", shdesc);
                            map.put("long", longDescription);
                            map.put("vid", final_youtube);
                            map.put("sorname", newsSourcename);
                            map.put("upload", encodeImageString);
                            map.put("location", location);
                            map.put("language", languageselect);
                            map.put("audio", audio);
                            map.put("date", date);
                            map.put("sorimage", encodeSourceImage);
                            map.put("converted", enaudioen);
                            return map;
                        }
                    };
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(request);
                }
            }
        }, 10000);
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rg5r1:
                if (checked)
                    audio = "1";
                break;
            case R.id.rg5r2:
                if (checked)
                    audio = "0";
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), "Categorie Selected: " + users[position], Toast.LENGTH_SHORT).show();
//        String[] users = { "Politics", "Technology", "Sports", "Startup", "Entertaintment", "Business" };

        if (users[position].equals("Politics")) {
            catSelected = url_politics;
            catcat = "politics";
        }
        if (users[position].equals("Technology")) {
            catSelected = url_technology;
            catcat = "tech";
        }
        if (users[position].equals("Sports")) {
            catSelected = url_sports;
            catcat = "sports";
        }
        if (users[position].equals("Startup")) {
            catSelected = url_startup;
            catcat = "startup";
        }
        if (users[position].equals("Entertaintment")) {
            catSelected = url_entertain;
            catcat = "entertain";
        }
        if (users[position].equals("Business")) {
            catSelected = url_business;
            catcat = "business";
        }
        if (users[position].equals("International")) {
            catSelected = url_international;
            catcat = "international";
        }
        if (users[position].equals("Influence")) {
            catSelected = url_influences;
            catcat = "influence";
        }
        if (users[position].equals("Miscellaneous")) {
            catSelected = url_miscel;
            catcat = "miscell";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private LatLng getLatLngFromAddress(String address) {

        Geocoder geocoder = new Geocoder(MainActivity.this);
        List<Address> addressList;

        try {
            addressList = geocoder.getFromLocationName(address, 1);
            if (addressList != null) {
                Address singleaddress = addressList.get(0);
                LatLng latLng = new LatLng(singleaddress.getLatitude(), singleaddress.getLongitude());
                return latLng;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    private Address getAddressFromLatLng(LatLng latLng) {
        Geocoder geocoder = new Geocoder(MainActivity.this);
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 5);
            if (addresses != null) {
                Address address = addresses.get(0);
                return address;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private void makeToast(String text, boolean longShow) {
        Toast.makeText(this, text, (longShow) ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }
}