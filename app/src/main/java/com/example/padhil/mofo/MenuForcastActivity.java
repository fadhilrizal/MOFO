package com.example.padhil.mofo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MenuForcastActivity extends AppCompatActivity {

    TextView t1,t2,t3,t4,t5,t6;
    String url;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_forcast);
        t1 = (TextView)findViewById(R.id.txtDate);
        t2 = (TextView)findViewById(R.id.txtName);
        t3 = (TextView)findViewById(R.id.txtSuhu);
        t4 = (TextView)findViewById(R.id.txtDesc);
        t5 = (TextView)findViewById(R.id.txtWind);
        t6 = (TextView)findViewById(R.id.txtHumid);

        Intent intent = getIntent();
        url = (String) intent.getStringExtra("url");
        name = (String) intent.getStringExtra("name");

        find_weather();
    }

    private void find_weather() {
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject mainobject = response.getJSONObject("main");
                    JSONArray array = response.getJSONArray("weather");
                    //JSONArray array2 = response.getJSONArray("wind");
                    JSONObject object = array.getJSONObject(0);
                    // JSONObject object2 = array2.getJSONObject(0);
                    String temp = String.valueOf(mainobject.getDouble("temp"));
                    String description = object.getString("description");
                    //String city = response.getString("name");
                    //String wind = object2.getString("speed");
                    // String humid = String.valueOf(mainobject.get("humidity"));
                    t4.setText(description);
                    t2.setText(name);
                    // t5.setText(wind);
                    // t6.setText(humid);

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE/MM/dd");
                    String formatdate = sdf.format(calendar.getTime());
                    t1.setText(formatdate);

                    double temp_int = Double.parseDouble(temp);
                    double centi = (temp_int-32)/1.8000;
                    centi = Math.round(centi);
                    int i = (int)centi;
                    t3.setText(String.valueOf(i));


                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jor);
    }
}
