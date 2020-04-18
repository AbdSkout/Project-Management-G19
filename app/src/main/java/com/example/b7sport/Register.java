package com.example.b7sport;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    EditText mFullName,mEmail,mPassword,mPhonenumber;
    Button mRegisterbtn;
    TextView alreadyRegistered;
    FirebaseAuth fAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseFirestore fStore;
    String UserID;
    public final String TAG = "TAG";

    String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.fName);
        mEmail = findViewById(R.id.eMail);
        mPassword = findViewById(R.id.passWord);
        mPhonenumber = findViewById(R.id.phoneNumber);
        mRegisterbtn = findViewById(R.id.Registerbtn);
        alreadyRegistered = findViewById(R.id.alreadyRegistred);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("EDMT_FIREBASE");
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
//        if(fAuth.getCurrentUser()!=null){
//            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//            finish();
//        }

        mRegisterbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                final String email = mEmail.getText().toString().trim();
                final  String password = mPassword.getText().toString().trim();
                final   String Name = mFullName.getText().toString().trim();
                final  String PhoneNumber = mPhonenumber.getText().toString().trim();
                if(EmailRequired(email)) return;
                if(PasswordIsEmpty(password)) return;
                if(PasswordLength(password)) return;
                if(EmailRegex(email)) return;
                if(CheckName(Name)) return;
               // Info info = new Info(email,PhoneNumber,Name,password);
            //    databaseReference.push().setValue(info);
                final Intent myIntent = new Intent(view.getContext(),MainActivity.class);
                myIntent.putExtra("email",email);
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "User Created.", Toast.LENGTH_SHORT).show();
                            UserID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentrefernce = fStore.collection("users").document(UserID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("FullName", Name);
                            user.put("Email", email);
                            user.put("PhoneNumber", PhoneNumber);
                            user.put("Password", password);
                           // Info info = new Info(email,PhoneNumber,Name,password,UserID);

                           // databaseReference.push().setValue(info);

                            documentrefernce.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG,"onSuccess : user Profile is created for" + UserID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: "+ e.toString());
                                }
                            });

                            startActivity(myIntent);

//                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(Register.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        alreadyRegistered.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

    }

    public boolean PasswordIsEmpty(String Password){
        if(TextUtils.isEmpty(Password)){
            mPassword.setError("חובה למלות שדה זה");
            return true;
        }
        return false;
    }

    public boolean PasswordLength(String Password){
        if(Password.length()<=6){
            mPassword.setError("על הסיסמה להיות לפחות 7 אותיות");
            return true;
        }
        return false;
    }
    public boolean EmailRequired(String Email){
        if(TextUtils.isEmpty(Email)){
            mEmail.setError("חובה למלות שדה זה");
            return true;
        }
        return false;
    }

    public boolean EmailRegex(String Email){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(Email);

        if(!matcher.matches()){
            mEmail.setError("The Format of the email must be example@example.com");
            return true;
        }
        return false;
    }
    public boolean CheckName(String name){
        if(TextUtils.isEmpty(name)){
            mFullName.setError("חובה למלות שדה זה");
            return true;
        }
        return false;
    }
//    public void CheckAll(String Name,String Email,String Password){
//        if(TextUtils.isEmpty(Name) && TextUtils.isEmpty(Email) && TextUtils.isEmpty(Password)){
//            mFullName.setError("חייב למלות שדה של שם");
//            mEmail.setError("המייל דרוש");
//            mPassword.setError("סיסמה דרושה");
//            return;
//        }
//
//    }

}
