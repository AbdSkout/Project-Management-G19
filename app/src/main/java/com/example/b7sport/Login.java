package com.example.b7sport;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class Login extends AppCompatActivity {
    EditText mEmail,mPassword;
    TextView mRegisterActivity,mPasswordRecovery;
    Button mLoginButton;
    FirebaseAuth fAuth;
   static int flag=-1;
    ProgressDialog dialog;
     public  static String  Email;


    Logic l = new Logic();
    final String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);


        dialog = new ProgressDialog(this);
        final FirebaseDatabase data = FirebaseDatabase.getInstance();
        final DatabaseReference ref = data.getReference("EDMT_FIREBASE");
        mEmail = findViewById(R.id.EmailText);
        mPassword = findViewById(R.id.PasswordText);
        mRegisterActivity = findViewById(R.id.RegisterLink);
        mLoginButton = findViewById(R.id.LoginButton);
        mPasswordRecovery = findViewById(R.id.PasswordRecovery);
        fAuth = FirebaseAuth.getInstance();

        Intent intent1 = new Intent(Login.this,MainActivity.class);
        Intent intent2 = new Intent(Login.this,MainActivity.class);

        String emailas  = mEmail.getText().toString();
       intent1.putExtra("emailadd",emailas);


        mLoginButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                flag=-1;
                final String email = mEmail.getText().toString().trim();
                Email=email;
                final String password = mPassword.getText().toString().trim();
                final Intent myIntent = new Intent(view.getContext(),MainActivity.class);
                myIntent.putExtra("emailadd",email);
                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required");
                    return;
                }
                if(email.equals("admin")&&password.equals("admin")){
                    Intent intent = new Intent(view.getContext(),adminpage.class);
                    Toast.makeText(Login.this,"Loged in Successfully.",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();

                }
                else {
                    final String[] Email = new String[1];
                    final String[] Password = new String[1];
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                               for(DataSnapshot d :  dataSnapshot.getChildren())
                               {
                                   Email[0] =d.child("email").getValue().toString();
                                   Password[0] =d.child("password").getValue().toString();
                                   if(Email[0].equals(email) && Password[0].equals(password) )
                                   {
                                       if(d.child("flag").getValue().equals("0")) {
                                           Toast.makeText(getApplicationContext(), "Sign-in successful", Toast.LENGTH_LONG).show();
                                           startActivity(myIntent);
                                           return;
                                       }
                                       else
                                       {
                                           Toast.makeText(getApplicationContext(),"YOU ARE BLOCKED",Toast.LENGTH_LONG).show();
                                           return;
                                       }

                                   }
                               }
                               if(flag==-1)
                               {
                                   Toast.makeText(getApplicationContext(),"this user not Existing",Toast.LENGTH_LONG).show();
                               }

                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError databaseError) {

                           }
                       });


                }
            }
        });




        mRegisterActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(getApplicationContext(),Register.class));
                finish();

            }
        });
        mPasswordRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Rest_password.class));


            }
        });

    }



    public boolean EmailisEmpty(String email){
        if(TextUtils.isEmpty(email)){
            mEmail.setError("חובה למלות שדה זה");
            return true;
        }
        return false;
    }
    public boolean EmailRegexCheck(String email){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()){
            mEmail.setError("The Format of the email must be example@example.com");
            return true;
        }
        return false;
    }
    public boolean PasswordIsEmpty(String email){
        if(TextUtils.isEmpty(email)){
            mPassword.setError("חובה למלות שדה זה");
            return true;
        }
        return false;
    }
 static void   isblock(final String email)
  {
      FirebaseDatabase data;
      data = FirebaseDatabase.getInstance();
      final DatabaseReference ref = data.getReference("Block");

      ref.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              for(DataSnapshot d :dataSnapshot.getChildren())
              {

                  Log.d("info",d.getValue().toString());
                  if(email.equals(d.getValue().toString())) {
                      flag = 1;
                      Log.d("info","we enter");
                      break;
                  }




              }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });

  }



}