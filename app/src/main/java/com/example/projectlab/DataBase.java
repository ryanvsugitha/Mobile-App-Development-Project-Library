package com.example.projectlab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {

    private Context context;

    public static final String DATABASE_NAME = "Database.db";
    public static final int DATABASE_VERSION = 1;

    public DataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String user = "CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "email TEXT, " +
                "password TEXT, " +
                "phone_number TEXT, " +
                "date_of_birth TEXT)";

        String book = "CREATE TABLE books (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "author TEXT, " +
                "cover TEXT, " +
                "synopsis TEXT)";

        String request = "CREATE TABLE requests (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "book_id INTEGER, " +
                "requester_id INTEGER, " +
                "receiver_id INTEGER, " +
                "latitude REAL,"+
                "longitude REAL" +
//                "FOREIGN KEY(book_id) REFERENCES books(id), " +
//                "FOREIGN KEY(requester_id) REFERENCES users(id), " +
//                "FOREIGN KEY(receiver_id) REFERENCES users(id))";
                ")";

        db.execSQL(user);
        db.execSQL(book);
        db.execSQL(request);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    void addUser(String email, String password, String phone_number, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv  = new ContentValues();

        cv.put("email", email);
        cv.put("password", password);
        cv.put("phone_number", phone_number);
        cv.put("date_of_birth", date);

        long result = db.insert("users", null, cv);
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "User Added", Toast.LENGTH_SHORT).show();
        }
    }

    void addBook(String name, String author, String cover, String synopsis){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv  = new ContentValues();

        cv.put("name", name);
        cv.put("author", author);
        cv.put("cover", cover);
        cv.put("synopsis", synopsis);

        long result = db.insert("books", null, cv);
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Books Added", Toast.LENGTH_SHORT).show();
        }
    }

    void addRequest(int req_id, String book_name, String latitude, String longitude){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues cv  = new ContentValues();

        String query = "SELECT id FROM books WHERE name = ?";
        String args[] = {book_name};
        Cursor cursor = db.rawQuery(query, args);

        cursor.moveToNext();
        int id = cursor.getInt(0);
        cursor.close();

        cv.put("book_id", id);
        cv.put("requester_id", req_id);
        cv.put("receiver_id", 0);
        cv.put("latitude", latitude);
        cv.put("longitude", longitude);

        long result = db.insert("requests", null, cv);
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Request Added", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean auth_user(String email, String password){
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        String args[] = {email, password};

        Cursor cursor = db.rawQuery(query, args);
        if(cursor.moveToNext()){
            cursor.close();
            return true;
        }
        else{
            return false;
        }
    }

    public boolean exist_email(String email) {
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT email FROM users WHERE email = ?";
        String args[] = {email};
        Cursor cursor = db.rawQuery(query, args);
        if(cursor.getCount() > 0){
            cursor.close();
            return true;
        }
        else{
            return false;
        }
    }

    public int getID(String email){
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT id FROM users WHERE email = ?";
        String args[] = {email};
        Cursor cursor = db.rawQuery(query, args);
        cursor.moveToNext();
        int id = cursor.getInt(0);
        return id;
    }

    public int getBookCount(){
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM books";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int getRequestCount(){
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM requests";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public String[][] bookList(int count){
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM books";
        Cursor cursor = db.rawQuery(query, null);

        String[][] book = new String[count][7];

        int counter = 0;
        if(cursor.moveToFirst()){
            do{
                book[counter][1] = cursor.getString(1);
                book[counter][2] = cursor.getString(2);
                book[counter][3] = cursor.getString(3);
                book[counter][4] = cursor.getString(4);
                counter += 1;
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        return book;
    }

    public String[][] requestList(int count){
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM requests INNER JOIN books ON requests.book_id=books.id";
        Cursor cursor = db.rawQuery(query, null);

        String[][] request = new String[count][7];

        int counter = 0;
        if(cursor.moveToFirst()){
            do{
                request[counter][1] = cursor.getString(9);
                request[counter][2] = cursor.getString(7);
                request[counter][3] = cursor.getString(8);
                if (cursor.getString(3).equals("0")){
                    request[counter][4] = "-";
                }
                request[counter][5] = cursor.getString(2);

                counter += 1;
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        return request;
    }

    public String[] book_info(String book_name){
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM books WHERE name = ?";
        String args[] = {book_name};
        Cursor cursor = db.rawQuery(query, args);

        String[] book = new String[7];

//        Toast.makeText(context, cursor.getString(1), Toast.LENGTH_SHORT).show();

        cursor.moveToFirst();
        book[1] = cursor.getString(1);
        book[2] = cursor.getString(2);
        book[3] = cursor.getString(3);
        book[4] = cursor.getString(4);

        cursor.close();
        return book;
    }
}