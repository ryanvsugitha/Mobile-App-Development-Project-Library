package com.example.projectlab;

public class DataBaseUsersModel {

    private int id;
    private String email;
    private String password;
    private String phone_number;
    private String date_of_birth;

    public DataBaseUsersModel(int id, String email, String password, String phone_number, String date_of_birth) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;
        this.date_of_birth = date_of_birth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }
}
