package com.example.b7sport;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.widget.TextView;

        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextView mName,mEmail,mPhonenumber;
    private FirebaseDatabase database;
    private DatabaseReference UserRef;
    private String userID1;
    private static final String USERS = "EDMT_FIREBASE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();



        userID1 = bundle.getString("email");

        mName = findViewById(R.id.FullName);
        mEmail = findViewById(R.id.Email);
        mPhonenumber = findViewById(R.id.PhoneNumber);

        database = FirebaseDatabase.getInstance();
        UserRef = database.getReference(USERS);

        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if(ds.child("email").getValue().equals(userID1)){
                            mName.setText(ds.child("name").getValue(String.class));
                            mEmail.setText(ds.child("email").getValue(String.class));
                            mPhonenumber.setText(ds.child("phoneNumber").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
