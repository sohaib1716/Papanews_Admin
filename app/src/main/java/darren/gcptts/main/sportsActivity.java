package darren.gcptts.main;

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

public class sportsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    GestureDetector mGestureDetector;
    List<PersonUtils> personUtilsList;


    private static String url = "http://papanews.in/PapaNews/getSports.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sports_activity);

        recyclerView = (RecyclerView) findViewById(R.id.recycleViewContainer);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        personUtilsList = new ArrayList<>();


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            Log.e("Politics array :: ", String.valueOf(array));
                            //traversing through all the object,,,,

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
                            mAdapter = new CustomRecyclerAdapter(sportsActivity.this, personUtilsList);
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

}
