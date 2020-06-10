package com.example.b7sport;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Complaint extends AppCompatActivity {
    EditText mEntireMsg;
    Button mSendmsg,mCancelbtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        mEntireMsg = findViewById(R.id.entire_msg);
        mSendmsg = findViewById(R.id.send_btn);
        mCancelbtn = findViewById(R.id.cancelsend_btn);
        TextView admin= findViewById(R.id.textView12);
        TextView msg=findViewById(R.id.admin_ans);
       admin.setVisibility(View.INVISIBLE);
        msg.setVisibility(View.INVISIBLE);
       show_ans(admin,msg);

        Bundle bundle = getIntent().getExtras();

        final String userID1 = bundle.getString("emailadd");

        final Intent intent1 = new Intent(Complaint.this,MainActivity.class);
        intent1.putExtra("emailadd",userID1);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Complains");


        mSendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                String messsage = mEntireMsg.getText().toString().trim();
                if(TextUtils.isEmpty(messsage)){
                    mEntireMsg.setError("הודעה ריקה,נא לכתוב משהו!");
                    return;
                }
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("Message",messsage);
                map.put("Email",userID1);

                databaseReference.push().setValue(map);
                Toast.makeText(Complaint.this,"תלונה נשלחה בהצלחה!",Toast.LENGTH_SHORT).show();
                startActivity(intent1);
                //finish();
                }catch(Exception e){
                    Toast.makeText(Complaint.this,"",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mCancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent1);
                finish();
            }
        });





    }


    public  void show_ans(final TextView  admin, final TextView msg)
    {
        final String[] help = new String[1];
        final int[] flag = {0};
        final FirebaseDatabase data = FirebaseDatabase.getInstance();
        final DatabaseReference ref = data.getReference("Complains_ANS");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot d:dataSnapshot.getChildren())
                {
                    if(d.child("email").getValue().toString().equals(Login.Email))
                    {
                        admin.setVisibility(View.VISIBLE);
                        msg.setVisibility(View.VISIBLE);
                        msg.setText(d.child("ans").getValue().toString());
                }

            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
