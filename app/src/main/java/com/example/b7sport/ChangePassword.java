package com.example.b7sport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.PrintStreamPrinter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.PrintStream;
import java.time.Clock;

public class ChangePassword extends AppCompatActivity {
        EditText NewPass,ConfirmNewPass;
        Button mBack,mChangePass;
        FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        NewPass = findViewById(R.id.newpass);
        ConfirmNewPass = findViewById(R.id.confirmnewpass);
        mBack = findViewById(R.id.Back);
        mChangePass = findViewById(R.id.changePsswordbtn);
        auth=FirebaseAuth.getInstance();

        //Back to the main page
        mBack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        mChangePass.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
//                                               String email = "yaserni@ac.sc.ac.il";//must save the mail ...
//                                               String password = mCurrentPass.getText().toString();

                                               FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                               String newp = NewPass.getText().toString();
                                               String cnewp = ConfirmNewPass.getText().toString();

                                               if (newp.equals(cnewp))
                                               {
                                                   if (user!=null)
                                                       user.updatePassword(NewPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                           @Override
                                                           public void onComplete(@NonNull Task<Void> task) {
                                                               if(task.isSuccessful())
                                                               {
                                                                   Toast.makeText((getApplicationContext()),"Your password has been changed..",Toast.LENGTH_LONG);
                                                               }
                                                               else
                                                               {
                                                                   System.out.println("error");
                                                                   Toast.makeText((getApplicationContext()),"Your password could not be changed..",Toast.LENGTH_LONG);
                                                               }
                                                           }
                                                       });

                                               }
                                               else
                                               {
                                                   Toast.makeText((getApplicationContext()),"Your password could not be changed..",Toast.LENGTH_LONG);
                                               }
                                               }

                                       }




        );

    }
//    public boolean checkpassword(String s)
//    {
//        if (s.length()<7)
//            return false;
//        return true;
//
//    }
//public void change(View v)
//{
//    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//    if (user!=null)
//        user.updatePassword().addOnCompleteListener(new OnCompleteListener<Void>() {
//        });
//
//}


}
