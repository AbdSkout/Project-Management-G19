package com.example.b7sport;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewFriends extends AppCompatActivity {
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerViewFriends;
    ProgressDialog pd;
    DatabaseReference reference;
    UsersAdapter adapeter;
    List<InfoFromDataBase> usersinfo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_friends);

        pd = new ProgressDialog(this);

        recyclerViewFriends = findViewById(R.id.RecyclerViewFriends);
        recyclerViewFriends.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);

        recyclerViewFriends.setLayoutManager(layoutManager);

        show();


    }

    public void show(){
        pd.setTitle("טוען נתונים...");
        pd.show();
        pd.setCancelable(false);

        reference = FirebaseDatabase.getInstance().getReference().child("EDMT_FIREBASE");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    InfoFromDataBase data1 = data.getValue(InfoFromDataBase.class);
                    usersinfo.add(data1);
                }
                adapeter = new UsersAdapter(RecyclerViewFriends.this, usersinfo);
                recyclerViewFriends.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerViewFriends.setAdapter(adapeter);
                pd.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RecyclerViewFriends.this, "Error Loading the Info!", Toast.LENGTH_SHORT).show();

            }
        });
    }


}