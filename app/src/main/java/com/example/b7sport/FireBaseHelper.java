package com.example.b7sport;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FireBaseHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferrence;
    private List<Info> infos = new ArrayList<Info>();

   // public interface DataStatus
    //TODO המשך קוד מפו
    public FireBaseHelper(){
        mDatabase = FirebaseDatabase.getInstance();
        mReferrence = mDatabase.getReference("EDMT_FIREBASE");
    }

    public void readData(){
        mReferrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                infos.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot key :dataSnapshot.getChildren()){
                    keys.add(key.getKey());
                    Info info = key.getValue(Info.class);
                    infos.add(info);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
