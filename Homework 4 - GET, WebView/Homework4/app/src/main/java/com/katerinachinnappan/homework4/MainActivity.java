package com.katerinachinnappan.homework4;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
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

import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String url1, title1, subtitle1;

    private static final String LOG_TAG = "lv-ex";
    RequestQueue queue;

    private class ListElement {
        ListElement() {};

        ListElement(String tl, String subtl, String myURL) {
            title = tl;
            subtitle = subtl;
            url = myURL;
        }

        public String title;
        public String subtitle;
        public String url;
    }

    private ArrayList<ListElement> aList;

    private class MyAdapter extends ArrayAdapter<ListElement> {

        int resource;
        Context context;

        public MyAdapter(Context _context, int _resource, List<ListElement> items) {
            super(_context, _resource, items);
            resource = _resource;
            context = _context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            RelativeLayout newView;

            ListElement item = getItem(position);

            // Inflate a new view if necessary.
            if (convertView == null) {
                newView = new RelativeLayout(getContext());
                LayoutInflater vi = (LayoutInflater)
                        getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                vi.inflate(resource,  newView, true);
            } else {
                newView = (RelativeLayout) convertView;
            }

            // Fills in the view.
            TextView titleTextView = (TextView) newView.findViewById(R.id.title);
            TextView  subtitleTextView= (TextView) newView.findViewById(R.id.subtitle);

            titleTextView.setText(item.title);
            subtitleTextView.setText(item.subtitle);

            // Set a listener for the whole list item.
            newView.setTag(item.url);
            newView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Action to do when cell is clicked
                    String s = v.getTag().toString();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, s, duration);
                    toast.show();

                    // Intent to go to next activity passing url
                    Intent intent = new Intent(context, Main2Activity.class);
                    intent.putExtra("url", v.getTag().toString());
                    startActivity(intent);
                }
            });

            return newView;
        }
    }

    private MyAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aList = new ArrayList<ListElement>();
        aa = new MyAdapter(this, R.layout.list_element, aList);
        ListView myListView = (ListView) findViewById(R.id.listView);
        myListView.setAdapter(aa);
        aa.notifyDataSetChanged();
        loadCell();
    }
    public void clickRefresh (View v) {
        loadCell();
    }

    public void loadCell(){
        queue = Volley.newRequestQueue(this);
        // Instantiate the RequestQueue.

        String url = "https://luca-ucsc-teaching-backend.appspot.com/hw4/get_news_sites";

        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(LOG_TAG, "Received: " + response.toString());
                        try {
                            //create json array and iterate thru it
                            JSONArray arr = response.getJSONArray("news_sites");
                            aList.clear();
                            for(int i = 0; i < arr.length(); i++)
                            {
                                JSONObject news = arr.getJSONObject(i);
                                //parse the array into according strings
                                url1 = news.getString("url");
                                subtitle1 = news.getString("subtitle");
                                title1 = news.getString("title");
                                //if subtitle is null, assign an empty string and don't display it
                                if(subtitle1 == "null") subtitle1 = " ";

                                /*** debugging purpose here
                                Log.d(LOG_TAG, "Received url: " + url1);
                                Log.d(LOG_TAG, "Received title: " + title1);
                                Log.d(LOG_TAG, "Received subtitle: " + subtitle1);****/

                                //only include those in the list whose url or title aren't null
                                if(url1 != "null" && title1 != "null") {
                                    aList.add(new ListElement(title1, subtitle1, url1));
                                }
                            }
                            Toast toast = Toast.makeText(getBaseContext(), "New Headline added", Toast.LENGTH_SHORT);
                            toast.show();
                            aa.notifyDataSetChanged();
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
        queue.add(jsObjRequest);
    }
}