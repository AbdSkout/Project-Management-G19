package com.example.b7sport;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.awt.font.TextAttribute;
import java.io.FileNotFoundException;

public class blockuser extends AppCompatActivity {

    EditText Name;
    Button but_block;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blockuser);

           Name=findViewById(R.id.nblock);
           but_block=findViewById(R.id.Bblock);

           but_block.setOnClickListener(new View.OnClickListener(){


               @Override
               public void onClick(View v) {
                   DatabaseReference ref = database.getReference("EDMT_FIREBASE");

                     ref.addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                               String name;
                                int flag=0;
                             for (DataSnapshot d : dataSnapshot.getChildren())
                             {
                                    name=d.child("Email").getValue().toString();
                                    if(name.equals(Name) )
                                    {
                                        flag=1;


                                    }

                             }
                           if(flag==1)
                               Log.d("","user is here");

                         }


                         @Override
                         public void onCancelled(@NonNull DatabaseError databaseError) {

                         }


                     });




               }


           });


    }

}
