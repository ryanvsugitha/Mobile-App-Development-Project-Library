package com.example.projectlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private DataBase dataBase;
    private Button btnRegister, btnLogin, btnGetBook;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegister = findViewById(R.id.btn_register);
        btnLogin = findViewById(R.id.btn_login);
        btnGetBook = findViewById(R.id.get_book_data);

        dataBase = new DataBase(this);

        requestQueue = Volley.newRequestQueue(MainActivity.this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, login.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, register.class);
                startActivity(intent);
            }
        });

        btnGetBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonParse();
            }
        });
    }

    private void jsonParse(){
        String url = "https://api.npoint.io/a5a68261175f4aef2a87";

        JsonObjectRequest request = new JsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("users");
                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject users = jsonArray.getJSONObject(i);

                                String name = users.getString("name");
                                String author = users.getString("author");
                                String synopsis = users.getString("synopsis");
                                String cover = users.getString("cover");

                                dataBase.addBook(name, author, cover, synopsis);
                                }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue.add(request);
    }
}