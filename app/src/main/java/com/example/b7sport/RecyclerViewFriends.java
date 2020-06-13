package com.example.b7sport;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerViewFriends extends AppCompatActivity {
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerViewFriends;
    ProgressDialog pd;
    DatabaseReference reference;
    FriendsProfileAdapter adapeter;
    List<InfoFromDataBase> usersinfo = new ArrayList<>();
    boolean result = false;
    static String FriendNodekey;
    static String LoginNodekey;
    List<String> friendsemailslist;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_friends);
        friendsemailslist = new ArrayList<>();
        pd = new ProgressDialog(this);

        recyclerViewFriends = findViewById(R.id.recyclerView);
        recyclerViewFriends.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);

        recyclerViewFriends.setLayoutManager(layoutManager);

        getAllFriends();


    }

    public void getAllFriends(){
        pd.setTitle("טוען נתונים...");
        pd.show();
        pd.setCancelable(false);
        reference = FirebaseDatabase.getInstance().getReference("EDMT_FIREBASE");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data4 : dataSnapshot.getChildren()){
                    if(Login.Email.equals(data4.child("email").getValue())){
                        LoginNodekey = data4.getKey();
                        break;
                    }
                }
                ref = FirebaseDatabase.getInstance().getReference("EDMT_FIREBASE/"+LoginNodekey+"/Friends");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot d : dataSnapshot.getChildren()){
                            Map<String,Object> map = new HashMap<>();
                            friendsemailslist.add(d.child("FriendEmail").getValue().toString());
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                ref = FirebaseDatabase.getInstance().getReference("EDMT_FIREBASE");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot data1 : dataSnapshot.getChildren()){
                            if( i == friendsemailslist.size() ){
                                break;
                            }
                            else{

                                 if(data1.child("email").getValue().equals(friendsemailslist.get(i))){
                                       InfoFromDataBase data2 = data1.getValue(InfoFromDataBase.class);
                                       usersinfo.add(data2);
                                       i++;
                            }
                            }
                        }
                        adapeter = new FriendsProfileAdapter(RecyclerViewFriends.this, usersinfo);
                        recyclerViewFriends.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerViewFriends.setAdapter(adapeter);
                        pd.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}