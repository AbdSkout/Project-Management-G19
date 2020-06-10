package com.example.b7sport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Rest_password extends AppCompatActivity {

    EditText email;
    EditText phone;
    EditText pass;
    Button send;
    final FirebaseDatabase data = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_password);
      email=findViewById(R.id.get_EMAIL);
      phone=findViewById(R.id.get_phone);
      pass=findViewById(R.id.get_newpass);
      send=findViewById(R.id.SEND_pass);
        final DatabaseReference ref = data.getReference("EDMT_FIREBASE");


        send.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              final String Email=email.getText().toString();
              final String Phone=phone.getText().toString();
              final String Pass=pass.getText().toString();
              int ok=1;
          if(Email.equals("") || Phone.equals("") || Pass.equals(""))
          {
              Toast.makeText(getApplicationContext(),"Please fill out all forms" ,Toast.LENGTH_SHORT).show();
              ok=0;
          }

           if(ok==1) {

               ref.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       int flag1 = 0;
                       for (DataSnapshot d : dataSnapshot.getChildren()) {
                           if (d.child("email").getValue().toString().equals(Email)) {

                               if (d.child("PhoneNumber").getValue().toString().equals(Phone)) {
                                   ref.child(d.getKey().toString()).child("password").setValue(Pass);
                                   flag1 = 1;
                               } else {
                                   Toast.makeText(getApplicationContext(), "the phonenumber not match the eamil", Toast.LENGTH_LONG).show();
                                  flag1=-1;
                                   break;
                               }
                           }
                       }

                       if(flag1==0)
                       {
                           Toast.makeText(getApplicationContext(),"Email not Vaild",Toast.LENGTH_SHORT).show();

                       }
                       if(flag1==1)
                       {
                           Toast.makeText(getApplicationContext(),"Password has been restored",Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(getApplicationContext(),Login.class));
                       }

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });
           }

          }
      });


    }
}
