package com.echcherqaoui.khalid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class finderActivity extends AppCompatActivity {
    private EditText ipInput;
    private String ip;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finder);
        this.ipInput = findViewById(R.id.ipToFind);
        this.container = findViewById(R.id.container);

        findViewById(R.id.button).setOnClickListener(view -> {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            ip = ipInput.getText().toString();
            RequestQueue requestQueue = Volley.newRequestQueue(finderActivity.this);
            String url = "http://ipinfo.io/" + ip + "/geo";

            @SuppressLint("SetTextI18n") StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    List<String> details = new ArrayList<>();

                    details.add("Country: " + jsonObject.getString("country"));
                    details.add("Region: " + jsonObject.getString("region"));
                    details.add("City: " + jsonObject.getString("city"));
                    details.add("Timezone: " + jsonObject.getString("timezone"));

                    String localisation = jsonObject.getString("loc");

                    details.forEach(text -> {
                        TextView textView = new TextView(finderActivity.this);

                        textView.setText(text);
                        textView.setTextSize(15);
                        textView.setTypeface(null, Typeface.BOLD);
                        textView.setTextColor(Color.BLACK);
                        textView.setBackgroundColor(Color.parseColor( "#c8d1e3"));
                        textView.setPadding(120,20,0, 20);

                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );

                        layoutParams.setMargins(0,0,0, 20);
                        textView.setLayoutParams(layoutParams);

                        container.addView(textView);
                    });

                    Button MapBtn = new Button(finderActivity.this);
                    MapBtn.setText("Show Map");

                    MapBtn.setPadding(0, 30,0, 30);
                    container.addView(MapBtn);

                    MapBtn.setOnClickListener(view1 -> {
                        Intent intent = new Intent(view.getContext(), MapsActivity.class);
                        intent.putExtra("localisation", localisation);
                        startActivity(intent);
                    });

                }catch(Exception e){
                    throw new RuntimeException(e);
                }
            },error -> Log.e("API Error", error.toString()));

            requestQueue.add(request);
        });
    }
}