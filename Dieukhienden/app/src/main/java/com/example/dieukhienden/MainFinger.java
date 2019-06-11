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


public class MainFinger extends AppCompatActivity {
    EditText edtden1,edtden2;
    Button btnnhap,btnLoad;
    TextView textView;
    TextView txtTimeDoBN,txtTimeVangBN,txtTimeDoDT,txtTimeVangDT;
    Integer so1,so2,s3,s4;
    BufferedReader in = null;
    String baseUrl = "http://controlbms.com/api/points/get_all_point?device_key=DEVICE_6E94117044";
    RequestQueue requestQueue;
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_finger);
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
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRepoList();
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

                                if(Integer.parseInt(name.getJSONObject(i).getString("id")) == 370){
                                    so1 = Integer.parseInt(name.getJSONObject(i).getString("point_value").toString());
                                    Integer timeDendo = so1 + 3;
                                    edtden1.setText(so1.toString());
                                    txtTimeDoDT.setText(timeDendo.toString());
                                    txtTimeVangDT.setText("3");
                                }
                                else if(Integer.parseInt(name.getJSONObject(i).getString("id")) == 371){
                                    so2 = Integer.parseInt(name.getJSONObject(i).getString("point_value").toString());
                                    Integer timeDendoBN = so2 + 3;
                                    txtTimeDoBN.setText(timeDendoBN.toString());
                                    txtTimeVangBN.setText("3");
                                    edtden2.setText(so2.toString());
                                }
                            }

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
                    s3 = Integer.parseInt(edtden1.getText().toString());
                    Integer timeDendoDTTemp = s3 + 3;
                    txtTimeDoDT.setText(timeDendoDTTemp.toString());
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
                    s4 = Integer.parseInt(edtden2.getText().toString());
                    Integer timeDendoBNTemp = s4 + 3;
                    txtTimeDoBN.setText(timeDendoBNTemp.toString());
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
        txtTimeDoBN = findViewById(R.id.txtTimeDoBN);
        txtTimeVangBN = findViewById(R.id.txtTimeVangBN);
        txtTimeDoDT = findViewById(R.id.txtTimeDoDT);
        txtTimeVangDT = findViewById(R.id.txtTimeVangDT);
        btnnhap=findViewById(R.id.btnnhap);
        btnLoad = findViewById(R.id.btnLoad);
    }
}
