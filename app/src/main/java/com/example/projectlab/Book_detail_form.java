package com.example.projectlab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Book_detail_form extends AppCompatActivity {

    DataBase dataBase;
    private Button btn_request;
    private EditText et_latitude, et_longitude;
    private TextView tv_title, tv_author, tv_synopsis;
    private ImageView iv_cover;
    String[] bookinfo = new String[7];
    int ID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail_form);

        dataBase = new DataBase(this);

        btn_request = findViewById(R.id.btn_request);
        et_latitude = findViewById(R.id.et_latitude);
        et_longitude = findViewById(R.id.et_longitude);
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_author = (TextView)findViewById(R.id.tv_author);
        tv_synopsis = (TextView)findViewById(R.id.tv_synopsis);
        iv_cover = (ImageView)findViewById(R.id.iv_cover);

        ID = getIntent().getIntExtra("user_id", 0);

        String name = getIntent().getStringExtra("title");
        bookinfo = dataBase.book_info(name);

        tv_title.setText(bookinfo[1]);
        tv_author.setText(bookinfo[2]);
        tv_synopsis.setText(bookinfo[4]);
        iv_cover.setImageResource(getIntent().getIntExtra("id", 0));

        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String latitude = et_latitude.getText().toString();
                String longitude = et_longitude.getText().toString();

                if(latitude.isEmpty() || longitude.isEmpty()){
                    Toast.makeText(Book_detail_form.this, "Must fill all fields!", Toast.LENGTH_SHORT).show();
                }
                else{
                    dataBase.addRequest(ID, bookinfo[1], latitude, longitude);
                    Intent intent = new Intent(Book_detail_form.this, MainForm.class);
                    Toast.makeText(Book_detail_form.this, "Request made", Toast.LENGTH_SHORT).show();
                    intent.putExtra("user_id", ID);
                    startActivity(intent);
                }
            }
        });
    }
}