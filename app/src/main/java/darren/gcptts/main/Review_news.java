package darren.gcptts.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mannan.translateapi.Language;
import com.mannan.translateapi.TranslateAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import darren.gcptts.R;

public class Review_news extends AppCompatActivity {

    EditText title,shortdesc,longdesc,location,source;
    Button hindi,English;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_news);

        title = findViewById(R.id.title_re);
        shortdesc = findViewById(R.id.short_re);
        longdesc = findViewById(R.id.long_re);
        location = findViewById(R.id.location_re);
        source = findViewById(R.id.source_re);


        hindi = findViewById(R.id.hindi);
        English = findViewById(R.id.english);

        final String[] tit = new String[1];
        final String[] shdesc = new String[1];
        final String[] longDescription = new String[1];
        final String[] locat = new String[1];
        final String[] newsSourcename = new String[1];


        hindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tit[0] = title.getText().toString().trim();
                shdesc[0] = shortdesc.getText().toString().trim();
                longDescription[0] = longdesc.getText().toString().trim();
                locat[0] = location.getText().toString().trim();
                newsSourcename[0] = source.getText().toString().trim();


                TranslateAPI translateAPI = new TranslateAPI(Language.AUTO_DETECT, Language.HINDI, tit[0]);
                translateAPI.setTranslateListener(new TranslateAPI.TranslateListener() {
                    @Override
                    public void onSuccess(String translatedText) {
                        tit[0] = translatedText;
                        Toast.makeText(Review_news.this,  tit[0], Toast.LENGTH_SHORT).show();
                        Log.e("translated title :",  tit[0]);
                        title.setText(tit[0]);
                    }

                    @Override
                    public void onFailure(String ErrorText) {
                        Toast.makeText(Review_news.this, "Failed to translate", Toast.LENGTH_SHORT).show();
                    }
                });



//                Intent i = new Intent(Review_news.this, MainActivity.class);
//                i.putExtra("title", tit[0]);
//                i.putExtra("short_description", shdesc[0]);
//                i.putExtra("long_description", longDescription[0]);
//                i.putExtra("location", locat[0]);
//                i.putExtra("news_source", newsSourcename[0]);
//                startActivity(i);

            }
        });



    }
}