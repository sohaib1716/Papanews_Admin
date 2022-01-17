package darren.gcptts.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

import darren.gcptts.R;

public class savedPosts extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    GestureDetector mGestureDetector;
    List<PersonUtils> personUtilsList;

    Button tech_news,politic_news,startup_nwes,sports_news,entertain_news,business_news,international,influence,miscellan;


    private static String url = "http://papanews.in/PapaNews/getBusiness.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_select);

        tech_news = findViewById(R.id.technology);
        politic_news = findViewById(R.id.politics);
        startup_nwes = findViewById(R.id.startup);
        sports_news = findViewById(R.id.sports);
        entertain_news = findViewById(R.id.entertain);
        business_news = findViewById(R.id.business);
        international = findViewById(R.id.international);
        influence = findViewById(R.id.influences);
        miscellan = findViewById(R.id.Miscellaneous);


        tech_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(savedPosts.this, TechActivity.class);
                startActivity(i);
            }
        });


        politic_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(savedPosts.this, politicsActivity.class);
                startActivity(i);
            }
        });


        startup_nwes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(savedPosts.this, startupActivity.class);
                startActivity(i);
            }
        });


        sports_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(savedPosts.this, sportsActivity.class);
                startActivity(i);
            }
        });


        entertain_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(savedPosts.this, entertainActivity.class);
                startActivity(i);
            }
        });

        business_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(savedPosts.this, BusinessActivity.class);
                startActivity(i);
            }
        });

        international.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(savedPosts.this, international.class);
                startActivity(i);
            }
        });
        influence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(savedPosts.this, influences.class);
                startActivity(i);
            }
        });
        miscellan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(savedPosts.this, miscellaneous.class);
                startActivity(i);
            }
        });

    }

}

