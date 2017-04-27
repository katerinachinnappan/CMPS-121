package com.katerinachinnappan.homework3;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "homework3";
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //when you click GET button, make a GET request
    public void onClickGet(View v) {
        queue = Volley.newRequestQueue(this);
        final TextView string1 = (TextView) findViewById(R.id.detailView);

        // Instantiate the RequestQueue.
        String url = "https://luca-ucsc-teaching-backend.appspot.com/hw3/request_via_get";

        //key (token) + abracadabra
        String my_url = url + "?token=" + URLEncoder.encode("abracadabra");

        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, my_url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(LOG_TAG, "Received: " + response.toString());
                        try {
                            //parse GET done correctly from JSON object
                            String str = response.getString("result");
                            //display in the textview
                            string1.setText(str);
                        }
                        catch(JSONException e) {
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d(LOG_TAG, error.toString());
                    }
                });

        // In some cases, we don't want to cache the request.
        // jsObjRequest.setShouldCache(false);

        queue.add(jsObjRequest);

    }
    //when click on POST, call sendMsg and pass the message; abracadabra
    public void onClickPost(View v) {
        sendMsg("abracadabra");

    }
    private void sendMsg(final String msg) {
        queue = Volley.newRequestQueue(this);
        final TextView string1 = (TextView) findViewById(R.id.detailView);
        StringRequest sr = new StringRequest(Request.Method.POST,
                "https://luca-ucsc-teaching-backend.appspot.com/hw3/request_via_post",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(LOG_TAG, "Got:" + response);
                        try {
                            //convert the string formatted like JSON to an actual JSON object
                            JSONObject obj = new JSONObject(response);
                            //now parse the string to get only: POST done correclty
                            String str = obj.getString("result");
                            //display in the text view
                            string1.setText(str);
                        }
                        catch(JSONException e) {
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                //pass the message with its associated key; token
                params.put("token", msg);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);

    }
}
