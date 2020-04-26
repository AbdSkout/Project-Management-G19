package com.example.b7sport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AllUsers extends AppCompatActivity {

    Button mBackbtn;
    //Recycler View
    RecyclerView mRecyclerView;
    List<InfoFromDataBase> usersinfo = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore fStore;
    UsersAdapter adapeter;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);


        mBackbtn = findViewById(R.id.backbtn);
        pd = new ProgressDialog(this);
        fStore = FirebaseFirestore.getInstance();
        //Intialazing
        mRecyclerView= findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        showdata();


        mBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), adminpage.class));
            }
        });


    }

    private void showdata() {
        pd.setTitle("טוען נתונים...");
        pd.show();
        pd.setCancelable(false);
        fStore.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        pd.dismiss();
                        for (DocumentSnapshot doc : task.getResult()) {
                            InfoFromDataBase info = new InfoFromDataBase(doc.getString("Email"),
                                    doc.getString("PhoneNumber"),
                                    doc.getString("FullName"));
                            usersinfo.add(info);
                        }
                        adapeter = new UsersAdapter(AllUsers.this, usersinfo);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        mRecyclerView.setAdapter(adapeter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(AllUsers.this, "Error Loading the Info!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
