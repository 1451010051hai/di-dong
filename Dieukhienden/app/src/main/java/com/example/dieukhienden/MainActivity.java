package com.example.dieukhienden;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    EditText edtden1,edtden2;
    Button btnnhap;
    TextView textView;
    BufferedReader in = null;
    String baseUrl = "http://controlbms.com/api/points/get_all_point?device_key=DEVICE_6E94117044";
    RequestQueue requestQueue;
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       addcontrol();
        requestQueue = Volley.newRequestQueue(this);
        getRepoList();
        btnnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendWorkPostRequest();
                sendWorkPost1Request();
            }
        });
    }

    private void getRepoList() {
        // First, we insert the username into the repo url.
        // The repo url is defined in GitHubs API docs (https://developer.github.com/v3/repos/).
        this.url = this.baseUrl;

        // Next, we create a new JsonArrayRequest. This will use Volley to make a HTTP request
        // that expects a JSON Array Response.
        // To fully understand this, I'd recommend readng the office docs: https://developer.android.com/training/volley/index.html
        JsonObjectRequest arrReq = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Check the length of our response (to see if the user has any repos)
                        try {
                            JSONArray name = (JSONArray) response.get("data");
                            for(int i=0;i<name.length();i++){
                                System.out.println(name.getJSONObject(i).getString("id"));
                                if(Integer.parseInt(name.getJSONObject(i).getString("id")) == 370){
                                    System.out.println("log 370");
                                    edtden1.setText(name.getJSONObject(i).getString("point_value").toString());
                                }
                                else if(Integer.parseInt(name.getJSONObject(i).getString("id")) == 371){
                                    System.out.println("log 371");
                                    edtden2.setText(name.getJSONObject(i).getString("point_value").toString());
                                }
                            }
                            System.out.println(response.toString());
//                            edtden1.setText(name.get("id").toString());
//                            edtden2.setText(name.get("id").toString());

                        } catch (Exception ex) {
                           // e.printStackTrace(ex);
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // If there a HTTP error then add a note to our repo list
                        Log.e("Volley", error.toString());
                    }
                }
        );
        // Add the request we just defined to our request queue.
        // The request queue will automatically handle the request as soon as it can.
        requestQueue.add(arrReq);
    }
    private void sendWorkPostRequest() {

        JSONObject jsonBody = new JSONObject();
        String URL ="http://controlbms.com/api/points/update_all_point?device_key=DEVICE_6E94117044&id[370]="+edtden1.getText().toString() ;
        JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String name;
                try {
                    name = response.get("status").toString();
                    System.out.println(name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                onBackPressed();

            }
        });
        requestQueue.add(jsonOblect);

        // Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();

}
    private void sendWorkPost1Request() {

        JSONObject jsonBody = new JSONObject();
        String URL1 ="http://controlbms.com/api/points/update_all_point?device_key=DEVICE_6E94117044&id[371]="+edtden2.getText().toString() ;


        JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, URL1, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String name;
                try {
                    name = response.get("status").toString();
                    System.out.println(name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                onBackPressed();

            }
        });
        requestQueue.add(jsonOblect);

        // Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();

    }

    private void addcontrol() {
        edtden1=findViewById(R.id.edtden1);
        edtden2=findViewById(R.id.edtden2);
        btnnhap=findViewById(R.id.btnnhap);
    }
}
