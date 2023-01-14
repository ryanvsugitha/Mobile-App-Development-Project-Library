package com.example.projectlab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class register extends AppCompatActivity {

    private DataBase dataBase;

    private EditText etEmail, etPhone, etPassword;
    private Button btnSubmit;
    private TextView tvDOB;
    private CheckBox checkBox;

    Pattern letter = Pattern.compile("[a-zA-z]");
    Pattern digit = Pattern.compile("[0-9]");

    DatePickerDialog.OnDateSetListener setListener;

    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(calendar.YEAR);
    int month = calendar.get(calendar.MONTH);
    int day = calendar.get(calendar.DAY_OF_MONTH);
    int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.et_email);
        tvDOB = findViewById(R.id.date_of_birth);
        etPassword = findViewById(R.id.et_password);
        etPhone = findViewById(R.id.phone_number);
        checkBox = findViewById(R.id.cb_agree);
        btnSubmit = findViewById(R.id.bUtton_submit);

        dataBase = new DataBase(this);

        tvDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dpg = new DatePickerDialog(register.this, android.R.style.Theme_Holo_Dialog_MinWidth, setListener, year, month, day);
                dpg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dpg.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                age = 2022 - year;
                tvDOB.setText(date);
            }
        };

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked == true) {
                    Toast.makeText(register.this, "Anda setuju dengan persyaratan yang berlaku", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(register.this, "Anda menolak persyaratan yang berlaku", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String date = tvDOB.getText().toString();
                String phone = etPhone.getText().toString();
                String password = etPassword.getText().toString();

                Matcher hasLetter = letter.matcher(password);
                Matcher hasDigit = digit.matcher(password);

                if(email.isEmpty() || date.isEmpty() || phone.isEmpty() || password.isEmpty() ){
                    Toast.makeText(register.this, "Fields must be filled", Toast.LENGTH_SHORT).show();
                }
                else if(!checkBox.isChecked()){
                    Toast.makeText(register.this, "Terms and conditions must be accepted", Toast.LENGTH_SHORT).show();
                }
                else if(age<13){
                    Toast.makeText(register.this, "You must be at least 13 years old", Toast.LENGTH_SHORT).show();
                }
                else if(!phone.substring(0,3).equals("+62")){
                    Toast.makeText(register.this, "Phone number must start with +62", Toast.LENGTH_SHORT).show();
                }
                else if(phone.length()<10 || phone.length()>15){
                    Toast.makeText(register.this, "Phone number must contains 10-15 char", Toast.LENGTH_SHORT).show();
                }
                else if(!hasLetter.find() || !hasDigit.find()){
                    Toast.makeText(register.this, "Password must include at least 1 character and 1 number", Toast.LENGTH_SHORT).show();
                }
                else if(password.length()<=8) {
                    Toast.makeText(register.this, "Password must be more than 8 characters", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(dataBase.exist_email(email)){
                        Toast.makeText(register.this, "Email already used!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        dataBase.addUser(email, password, phone, date);
                        dataBase.close();
                        Toast.makeText(register.this, "User successfully registered!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(register.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}