package darren.gcptts.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.mannan.translateapi.Language;
import com.mannan.translateapi.TranslateAPI;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import darren.gcptts.BuildConfig;
import darren.gcptts.R;
import darren.googlecloudtts.GoogleCloudTTS;
import darren.googlecloudtts.GoogleCloudTTSFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Accept extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText t1, t2;
    Button browse, upload, srcselect;
    ImageView img, srcImage;
    Bitmap bitmap, srcBitmap;
    String encodeImageString, encodeSourceImage;
    EditText longphp, videophp, newsphp, locationphp;
    String catSelected, image_location;
    Button saved;
    CheckBox eng, hind;
    int[] check = {0};
    String audio = "0";
    DatePicker picker;
    Button btnGet;
    TextView tvw;
    String catcat;

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    Button setdate,delete_data;
    String Category,s4;



    private static final String url_politics = "http://papanews.in/PapaNews/uploadPolitics.php";
    private static final String url_entertain = "http://papanews.in/PapaNews/uploadEntertain.php";
    private static final String url_business = "http://papanews.in/PapaNews/uploadBusiness.php";
    private static final String url_startup = "http://papanews.in/PapaNews/uploadStartup.php";
    private static final String url_sports = "http://papanews.in/PapaNews/uploadSports.php";
    private static final String url_technology = "http://papanews.in/PapaNews/uploadTechnology.php";
    private static final String url = "http://papanews.in/PapaNews/deleteUpoloaded.php";
    private static final String url_international = "http://papanews.in/PapaNews/uploadInternational.php";
    private static final String url_miscel = "http://papanews.in/PapaNews/uploadMiscel.php";
    private static final String url_influences = "http://papanews.in/PapaNews/uploadInfluence.php";

    String[] users = {"Politics", "Technology", "Sports", "Startup", "Entertaintment",
            "Business","International","Influence","Miscellaneous"};

    //voice
    private MainViewModel mMainViewModel;
    GoogleCloudTTS googleCloudTTS;
    String enaudio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept);

        img = (ImageView) findViewById(R.id.img);
        srcImage = (ImageView) findViewById(R.id.srcimage);
        srcselect = (Button) findViewById(R.id.selectImage);
        upload = (Button) findViewById(R.id.upload);
        browse = (Button) findViewById(R.id.browse);
        saved = (Button) findViewById(R.id.savedPosts);
        eng = findViewById(R.id.English);
        hind = findViewById(R.id.hindi);
        longphp = (EditText) findViewById(R.id.longPhp);
        videophp = (EditText) findViewById(R.id.videoId);
        newsphp = (EditText) findViewById(R.id.NewsSource);
        locationphp = (EditText) findViewById(R.id.location);
        t1 = (EditText) findViewById(R.id.t1);
        t2 = (EditText) findViewById(R.id.t2);
        delete_data = (Button) findViewById(R.id.delete);

        //voice
        ButterKnife.bind(this);
        googleCloudTTS = GoogleCloudTTSFactory.create(BuildConfig.API_KEY);
        mMainViewModel = new MainViewModel(getApplication(), googleCloudTTS);
        //


        final String s1 = getIntent().getStringExtra("title");
        final String s2 = getIntent().getStringExtra("short");
        String s3 = getIntent().getStringExtra("image");
        s4 = getIntent().getStringExtra("id");
        String s5 = getIntent().getStringExtra("video");
        String s6 = getIntent().getStringExtra("audio");
        String s7 = getIntent().getStringExtra("srname");
        String s8 = getIntent().getStringExtra("srimage");
        String s9 = getIntent().getStringExtra("location");
        String s10 = getIntent().getStringExtra("language");
        String s11 = getIntent().getStringExtra("date");
        String s12 = getIntent().getStringExtra("long");
        Category = getIntent().getStringExtra("category");


        Log.d("catcat : ", "" + Category);

        t1.setText(s1);
        t2.setText(s2);

        Picasso.get().load(s3).placeholder(R.drawable.ic_launcher_background)
                .resize(550, 550)
                .into(img);

        Picasso.get().load(s8).placeholder(R.drawable.ic_launcher_background)
                .resize(550, 550)
                .into(srcImage);

        videophp.setText(s5);
        longphp.setText(s12);
        newsphp.setText(s7);
        locationphp.setText(s9);


        tvw=(TextView)findViewById(R.id.selectDate);
        tvw.setText(s11);
        picker=(DatePicker)findViewById(R.id.datePicker1);
        btnGet=(Button)findViewById(R.id.button1);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                picker.setVisibility(View.VISIBLE);
                picker.bringToFront();
                tvw.setText(picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear());

            }
        });

        delete_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_userData();
                Intent i = new Intent(Accept.this, userUploads.class);
                startActivity(i);
            }
        });


        final AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autocomplete);
        autoCompleteTextView.setAdapter(new PlaceAutoSuggestAdapter(Accept.this, android.R.layout.simple_list_item_1));

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
                Dexter.withActivity(Accept.this)
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
                Dexter.withActivity(Accept.this)
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
                uploaddatatodb();
            }
        });

        saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Accept.this, savedPosts.class);
                startActivity(i);

            }
        });

    }

    private void delete_userData() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                RequestQueue queue = Volley.newRequestQueue(Accept.this);

                StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("TAG", "RESPONSE IS " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(Accept.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // method to handle errors.
                        Toast.makeText(Accept.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/x-www-form-urlencoded; charset=UTF-8";
                    }

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("id", s4);
                        return params;
                    }
                };
                queue.add(request);
            }
        }, 200);

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
                    showDate(arg1, arg2+1, arg3);
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

    private void encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytesofimage = byteArrayOutputStream.toByteArray();
        encodeImageString = android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    private void encode(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytesofimage = byteArrayOutputStream.toByteArray();
        encodeSourceImage = android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }


    private void uploaddatatodb() {

        if (eng.isChecked()) {
            Toast.makeText(getApplicationContext(), "English", Toast.LENGTH_LONG).show();
            final String title = t1.getText().toString().trim();
            final String shdesc = t2.getText().toString().trim();
            final String longDescription = longphp.getText().toString().trim();
            final String videoyoutube = videophp.getText().toString().trim();
            final String newsSourcename = newsphp.getText().toString().trim();
            final String location = locationphp.getText().toString().trim();
            final String date = tvw.getText().toString().trim();


            //voice
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
                            String enaudio = mMainViewModel.getena();
                            Log.e("pppppp",enaudio);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
//                            makeToast("Speak failed " + e.getMessage(), true);
                            Log.e("TAG", "Speak failed", e);
                        }
                    });


            StringRequest request = new StringRequest(Request.Method.POST, catSelected, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    t1.setText("");
                    t2.setText("");
                    longphp.setText("");
                    videophp.setText("");
                    newsphp.setText("");
                    img.setImageResource(R.drawable.ic_launcher_foreground);
                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("t1", title);
                    map.put("t2", shdesc);
                    map.put("long", longDescription);
                    map.put("vid", videoyoutube);
                    map.put("sorname", newsSourcename);
                    map.put("upload", encodeImageString);
                    map.put("location", location);
                    map.put("language", "English");
                    map.put("audio", audio);
                    map.put("date", date);
                    map.put("sorimage", encodeSourceImage);
                    map.put("category", catcat);
                    return map;

                }
            };
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(request);
        }

        
        if (hind.isChecked()) {
            final String[] title = {t1.getText().toString().trim()};
            final String[] shdesc = {t2.getText().toString().trim()};
            final String[] longDescription = {longphp.getText().toString().trim()};
            final String videoyoutube = videophp.getText().toString().trim();
            final String[] newsSourcename = {newsphp.getText().toString().trim()};
            final String[] location = {locationphp.getText().toString().trim()};
            final String date = tvw.getText().toString().trim();


            //voice
            mMainViewModel.initTTSVoice("hi-IN", "hi-IN-Wavenet-C", 0.0f, 1.0f);
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
                            String enaudio = mMainViewModel.getena();
                            Log.e("pppppp",enaudio);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
//                            makeToast("Speak failed " + e.getMessage(), true);
                            Log.e("TAG", "Speak failed", e);
                        }
                    });
            Toast.makeText(getApplicationContext(), "Hindi", Toast.LENGTH_LONG).show();
            TranslateAPI translateAPI = new TranslateAPI(Language.AUTO_DETECT, Language.HINDI, title[0]);
            translateAPI.setTranslateListener(new TranslateAPI.TranslateListener() {
                @Override
                public void onSuccess(String translatedText) {
                    title[0] = translatedText;
                    Toast.makeText(Accept.this, title[0], Toast.LENGTH_SHORT).show();
                    Log.e("translated title :", title[0]);
                    check[0]++;
                }

                @Override
                public void onFailure(String ErrorText) {
                    Toast.makeText(Accept.this, "Failed to translate", Toast.LENGTH_SHORT).show();
                }
            });
            TranslateAPI translateAPI1 = new TranslateAPI(Language.AUTO_DETECT, Language.HINDI, shdesc[0]);
            translateAPI1.setTranslateListener(new TranslateAPI.TranslateListener() {
                @Override
                public void onSuccess(String translatedText) {
                    shdesc[0] = translatedText;
                    Log.e("translated short :", shdesc[0]);
                    check[0]++;
                }

                @Override
                public void onFailure(String ErrorText) {
                    Toast.makeText(Accept.this, "Failed to translate", Toast.LENGTH_SHORT).show();
                }
            });

            TranslateAPI translateAPI2 = new TranslateAPI(Language.AUTO_DETECT, Language.HINDI, longDescription[0]);
            translateAPI2.setTranslateListener(new TranslateAPI.TranslateListener() {
                @Override
                public void onSuccess(String translatedText) {
                    longDescription[0] = translatedText;
                    Log.e("translated long :", longDescription[0]);
                    check[0]++;
                }

                @Override
                public void onFailure(String ErrorText) {
                    Toast.makeText(Accept.this, "Failed to translate", Toast.LENGTH_SHORT).show();
                }
            });

            TranslateAPI translateAPI3 = new TranslateAPI(Language.AUTO_DETECT, Language.HINDI, newsSourcename[0]);
            translateAPI3.setTranslateListener(new TranslateAPI.TranslateListener() {
                @Override
                public void onSuccess(String translatedText) {
                    newsSourcename[0] = translatedText;
                    check[0]++;
                }

                @Override
                public void onFailure(String ErrorText) {
                    Toast.makeText(Accept.this, "Failed to translate", Toast.LENGTH_SHORT).show();
                }
            });

            TranslateAPI translateAPI4 = new TranslateAPI(Language.AUTO_DETECT, Language.HINDI, location[0]);
            translateAPI4.setTranslateListener(new TranslateAPI.TranslateListener() {
                @Override
                public void onSuccess(String translatedText) {
                    location[0] = translatedText;
                    check[0]++;
                }

                @Override
                public void onFailure(String ErrorText) {
                    Toast.makeText(Accept.this, "Failed to translate", Toast.LENGTH_SHORT).show();
                }
            });

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    StringRequest request = new StringRequest(Request.Method.POST, catSelected, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            t1.setText("");
                            t2.setText("");
                            longphp.setText("");
                            videophp.setText("");
                            newsphp.setText("");
                            img.setImageResource(R.drawable.ic_launcher_foreground);
                            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("t1", title[0]);
                            map.put("t2", shdesc[0]);
                            map.put("long", longDescription[0]);
                            map.put("vid", videoyoutube);
                            map.put("sorname", newsSourcename[0]);
                            map.put("upload", encodeImageString);
                            map.put("location", location[0]);
                            map.put("language", "Hindi");
                            map.put("audio", audio);
                            map.put("date", date);
                            map.put("sorimage", encodeSourceImage);
                            map.put("category", catcat);
                            return map;

                        }
                    };
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(request);

                }
            }, 5000);

        }

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rg5r1:
                if (checked)
                    audio = "0";
                break;
            case R.id.rg5r2:
                if (checked)
                    audio = "1";
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), "Selected User: " + users[position], Toast.LENGTH_SHORT).show();
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

        Geocoder geocoder = new Geocoder(Accept.this);
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
        Geocoder geocoder = new Geocoder(Accept.this);
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

}