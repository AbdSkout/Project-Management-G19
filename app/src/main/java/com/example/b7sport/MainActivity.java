package com.example.b7sport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mName,mEmail,mPhonenumber;
    TextView mComplaint;
    Button mLogOutButton;
    private String userID1;
    FirebaseAuth fAuth;
    ProgressDialog dialog;
    FirebaseDatabase database;
    DatabaseReference reference;


    //Recycler View
    RecyclerView mRecyclerView;
    List<InfoFromDataBase> usersinfo = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore fStore;
    UsersAdapter adapeter;
    ProgressDialog pd;


    TextView mChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        pd = new ProgressDialog(this);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("0");
        mLogOutButton = findViewById(R.id.LogOutBtn);
        fAuth = FirebaseAuth.getInstance();
        mName = findViewById(R.id.textView5);
        mComplaint = findViewById(R.id.textView10);
        //Init Database
        fStore = FirebaseFirestore.getInstance();



        //Intialazing
        //mRecyclerView= findViewById(R.id.recyclerView);
        //mRecyclerView.setHasFixedSize(true);
        //layoutManager = new LinearLayoutManager(this);
        //mRecyclerView.setLayoutManager(layoutManager);
        //showdata();


                          mChange = findViewById(R.id.chanpassmainv);
                          mChange.setOnClickListener(new View.OnClickListener() {                                 
                              @Override                                                                           
                              public void onClick(View v) {                                                       
                                  startActivity(new Intent(getApplicationContext(), ChangePassword.class));       
                              }                                                                                   
                          });                                                                                     

        mChange = findViewById(R.id.chanpassmainv);
        mChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChangePassword.class));
            }
        });

        Bundle bundle = getIntent().getExtras();
        userID1 = bundle.getString("emailadd");

        final Intent intent1 = new Intent(MainActivity.this,Complaint.class);
        intent1.putExtra("emailadd",userID1);

        mComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent1);
            }
        });


        dialog = new ProgressDialog(this);
        mLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                FirebaseAuth.getInstance().signOut();
                dialog.dismiss();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }});




        final Intent myIntent = new Intent(MainActivity.this, Profile.class);
        myIntent.putExtra("emailadd", userID1);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            //TODO : Fix Profile View
            //The navbar is not sending correct info to the actions
            //Try to add threading to make the process faster
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.profile:
                        startActivity(myIntent);
                        overridePendingTransition(0, 0);
                        return true;
//                    case R.id.about:
//                        startActivity(new Intent(getApplicationContext(),About.class));
//                        overridePendingTransition(0,0);
//                        return true;
                    case R.id.home:
                        return true;
                }
                return false;
            }
        });
    }



  /*  private void showdata() {
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
                        adapeter = new UsersAdapter(MainActivity.this, usersinfo);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        mRecyclerView.setAdapter(adapeter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(MainActivity.this, "Error Loading the Info!", Toast.LENGTH_SHORT).show();
                    }
                });
    }*/

}