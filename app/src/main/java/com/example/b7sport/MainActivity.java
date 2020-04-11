package com.example.b7sport;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.MenuItem;
        import android.widget.TextView;

        import com.google.android.material.bottomnavigation.BottomNavigationView;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextView mName,mEmail,mPhonenumber;
//    private FirebaseDatabase database;
//    private DatabaseReference UserRef;

    private String userID1;
//    private static final String USERS = "EDMT_FIREBASE";
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth = FirebaseAuth.getInstance();
        mName = findViewById(R.id.textView5);

        Bundle bundle = getIntent().getExtras();
        userID1 = bundle.getString("email");

        final Intent myIntent = new Intent(MainActivity.this,Profile.class);
        myIntent.putExtra("email",userID1);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            //TODO : Fix Profile View
            //The navbar is not sending correct info to the actions
            //Try to add threading to make the process faster
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.profile:
                        startActivity(myIntent);
                        overridePendingTransition(0,0);
                        return true;
//                    case R.id.about:
//                        startActivity(new Intent(getApplicationContext(),About.class));
//                        overridePendingTransition(0,0);
//                        return true;
                    case R.id.home:
                        return true;
                }
                return false;
            }
        });

        //startActivity(myIntent);


//        mName = findViewById(R.id.FullName1);
//        mEmail = findViewById(R.id.Email1);
//        mPhonenumber = findViewById(R.id.PhoneNumber1);
//
//        database = FirebaseDatabase.getInstance();
//        UserRef = database.getReference(USERS);
//
//        UserRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot ds : dataSnapshot.getChildren()){
//                    if(ds.child("email").getValue().equals(userID1)){
//                            mName.setText(ds.child("name").getValue(String.class));
//                            mEmail.setText(ds.child("email").getValue(String.class));
//                            mPhonenumber.setText(ds.child("phoneNumber").getValue(String.class));
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

    }
}
