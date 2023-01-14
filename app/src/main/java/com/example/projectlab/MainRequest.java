package com.example.projectlab;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectlab.adapter.RecyclerViewRequestsAdapter;
import com.example.projectlab.model.Requester;

import java.util.Vector;

public class MainRequest extends AppCompatActivity {

    private Vector<Requester> request = new Vector<>();
    private RecyclerView rvRequest;
    int userID;

    DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_request);

        rvRequest = findViewById(R.id.rv_requests);

        userID = getIntent().getIntExtra("user_id", 0);

        dataBase = new DataBase(this);

        int count = dataBase.getRequestCount();

        dataBase.requestList(count);

        String[][] requestList = new String[count][7];
        requestList = dataBase.requestList(count);

        for(int i = 0; i<count; i++){
            request.add(new Requester(requestList[i][1], requestList[i][2], requestList[i][3], requestList[i][4], requestList[i][5]));
        }

        rvRequest.setAdapter(new RecyclerViewRequestsAdapter(request, this, userID));
        rvRequest.setLayoutManager(new LinearLayoutManager(this));
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
                Intent intent = new Intent(MainRequest.this, MainRequest.class);
                intent.putExtra("user_id", userID);
                startActivity(intent);
                break;

            case R.id.menu_logout:
                Intent logoutIntent = new Intent(MainRequest.this, login.class);
                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(logoutIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
