package com.example.projectlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {

    private DataBase dataBase;
    private EditText email, password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dataBase = new DataBase(this);

        email = findViewById(R.id.et_try_email);
        password = findViewById(R.id.et_try_password);
        login = findViewById(R.id.btn_try_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String in_email = email.getText().toString();
                String in_password = password.getText().toString();

                if (dataBase.auth_user(in_email, in_password)){
                    int getID = dataBase.getID(in_email);
                    dataBase.close();
                    Toast.makeText(login.this, "You Logged in", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(login.this, MainForm.class);
                    intent.putExtra("user_id", getID);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(login.this, "Invalid data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}