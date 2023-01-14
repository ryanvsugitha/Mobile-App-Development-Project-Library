package com.example.projectlab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.projectlab.adapter.RecyclerViewBooksAdapter;
import com.example.projectlab.model.Book;

import java.util.Vector;

public class MainForm extends AppCompatActivity{

    private Vector<Book> books = new Vector<>();
    private RecyclerView rvBooks;
    int userID;

    DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_form);

        rvBooks = findViewById(R.id.rv_books);

        userID = getIntent().getIntExtra("user_id", 0);

        dataBase = new DataBase(this);

        int count = dataBase.getBookCount();

        String[][] bookList = new String[count][7];
        bookList = dataBase.bookList(count);

        for(int i = 0; i<count; i++){
            books.add(new Book(bookList[i][3], bookList[i][1], bookList[i][2], bookList[i][4]));
        }

        rvBooks.setAdapter(new RecyclerViewBooksAdapter(books, this, userID));
        rvBooks.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_vaq:
                Intent intent = new Intent(MainForm.this, MainRequest.class);
                intent.putExtra("user_id", userID);
                startActivity(intent);
                break;

            case R.id.menu_logout:
                Intent logoutIntent = new Intent(MainForm.this, login.class);
                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(logoutIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
