package darren.gcptts.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import darren.gcptts.R;

public class TechActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    GestureDetector mGestureDetector;
    List<PersonUtils> personUtilsList;
    String ref = "";


    private static String url = "http://papanews.in/PapaNews/getTech.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tech_acticity);

        recyclerView = (RecyclerView) findViewById(R.id.recycleViewContainer);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        personUtilsList = new ArrayList<>();

        //Adding Data into ArrayList
//        personUtilsList.add(new PersonUtils("Todd Miller","Project Manager"));
//        personUtilsList.add(new PersonUtils("Bradley Matthews","Senior Developer"));
//        personUtilsList.add(new PersonUtils("Harley Gibson","Lead Developer"));
//        personUtilsList.add(new PersonUtils("Gary Thompson","Lead Developer"));
//        personUtilsList.add(new PersonUtils("Corey Williamson","UI/UX Developer"));
//        personUtilsList.add(new PersonUtils("Samuel Jones","Front-End Developer"));
//        personUtilsList.add(new PersonUtils("Michael Read","Backend Developer"));
//        personUtilsList.add(new PersonUtils("Robert Phillips","Android Developer"));
//        personUtilsList.add(new PersonUtils("Albert Stewart","Web Developer"));
//        personUtilsList.add(new PersonUtils("Wayne Diaz","Junior Developer"));

//        personUtilsList.add(new PersonUtils("Wayne Diaz","Junior Developer"));

        if(global.refresh==1){
            global.refresh = 0;
            onRestart();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            Log.e("Politics array :: ", String.valueOf(array));
                            //traversing through all the object

                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                                Log.e("Product array :: ", product.getString("title"));
                                //adding the product to product list
                                personUtilsList.add(new PersonUtils(
                                        product.getString("title"),
                                        product.getString("shortDesc"),
                                        product.getString("image"),
                                        product.getString("id"),
                                        product.getString("longDesc"),
                                        product.getString("videoId"),
                                        product.getString("audioType"),
                                        product.getString("language"),
                                        product.getString("sourceImage"),
                                        product.getString("sourceName"),
                                        product.getString("date"),
                                        product.getString("location"),
                                        product.getString("category")
                                ));

                            }

                            Collections.reverse(personUtilsList);
                            mAdapter = new CustomRecyclerAdapter(TechActivity.this, personUtilsList);
                            mAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(mAdapter);

                            //creating adapter object and setting it to recyclerview
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        //adding our stringrequest to queue
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
