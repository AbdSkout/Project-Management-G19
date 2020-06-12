package com.example.b7sport;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText mFullName,mEmail,mPassword,mPhonenumber,mAddress;
    Button mRegisterbtn;
    TextView alreadyRegistered;
    FirebaseAuth fAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseFirestore fStore;
    String UserID;
    int flag=0;
    public final String TAG = "TAG";

    String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
     Logic l=new Logic();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.fName);
        mEmail = findViewById(R.id.eMail);
        mPassword = findViewById(R.id.passWord);
        mPhonenumber = findViewById(R.id.phoneNumber2);
        mRegisterbtn = findViewById(R.id.Registerbtn);
        alreadyRegistered = findViewById(R.id.alreadyRegistred);
        mAddress = findViewById(R.id.address);

        final FirebaseDatabase data = FirebaseDatabase.getInstance();
        final DatabaseReference ref = data.getReference("EDMT_FIREBASE");
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        mRegisterbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                flag=0;
                final Intent myIntent = new Intent(view.getContext(),MainActivity.class);
                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                final String Name = mFullName.getText().toString().trim();
                final String PhoneNumber = mPhonenumber.getText().toString().trim();
                final String Address = mAddress.getText().toString().trim();

                myIntent.putExtra("email",email);
                if(l.EmailRequired(email)) return;
                if(l.PasswordIsEmpty(password)) return;
                if(l.PasswordLength(password)) return;
                if(l.EmailRegex(email)){
                     mEmail.setError("The Format of the email must be example@example.com");
                    return;
                }
                if(l.CheckName(Name)) return;
                if(l.EmailRegex(email)) return;
                if(l.CheckName(Name)) return;
                final Info INfo =new Info(email,PhoneNumber,Name,password,"0","0");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot d: dataSnapshot.getChildren())
                        {
                             if(d.child("email").getValue().toString().equals(email))
                             {
                                 flag=1;
                             }


                        }
                    if(flag==1)
                    {

                        Toast.makeText(getApplicationContext(),"this user already exist",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        ref.push().setValue(INfo);
                        Toast.makeText(getApplicationContext(),"User created",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(),Login.class));
                    }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
        alreadyRegistered.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();

            }
        });

    }

    public boolean emptyfiled( EditText F)
    {
        if(F.getText().equals(""))
            return false;
        else
            return true;


    }


}
