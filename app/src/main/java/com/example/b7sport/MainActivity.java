package com.example.b7sport;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.app.ListActivity;
        import android.app.ProgressDialog;
        import android.content.ClipData;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.provider.ContactsContract;
        import android.view.LayoutInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

       // import com.example.b7sport.ViewModel.UserViewModel;
        import com.firebase.ui.database.FirebaseRecyclerAdapter;
        import com.firebase.ui.database.FirebaseRecyclerOptions;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.android.gms.tasks.Task;
        import com.google.android.material.bottomnavigation.BottomNavigationView;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.Query;
        import com.firebase.ui.firestore.FirestoreRecyclerOptions;

        import com.google.firebase.database.ValueEventListener;
        import com.google.firebase.firestore.DocumentReference;
        import com.google.firebase.firestore.FirebaseFirestore;

        import com.firebase.ui.firestore.FirestoreRecyclerOptions;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.firestore.CollectionReference;
        import com.google.firebase.firestore.DocumentSnapshot;
        import com.google.firebase.firestore.EventListener;
        import com.google.firebase.firestore.FirebaseFirestoreException;
        import com.google.firebase.firestore.QueryDocumentSnapshot;
        import com.google.firebase.firestore.QuerySnapshot;
        import com.google.firebase.firestore.auth.User;

        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

<<<<<<< HEAD
    private TextView mName,mEmail,mPhonenumber;
    Button mLogOutButton;
    private String userID1;
    FirebaseAuth fAuth;
    ProgressDialog dialog;
    FirebaseDatabase database;
    DatabaseReference reference;


    //Recycler View
    RecyclerView mRecyclerView;
    List<InfoFromDataBase> usersinfo = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore fStore;
    UsersAdapter adapeter;
    ProgressDialog pd;


=======
    TextView mChange;
>>>>>>> Yaser

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD


        pd = new ProgressDialog(this);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("0");
        mLogOutButton = findViewById(R.id.LogOutBtn);
        fAuth = FirebaseAuth.getInstance();
        mName = findViewById(R.id.textView5);

        //Init Database
        fStore = FirebaseFirestore.getInstance();

        //Intialazing
        mRecyclerView= findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        showdata();



        dialog = new ProgressDialog(this);
        mLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                FirebaseAuth.getInstance().signOut();
                dialog.dismiss();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
=======
        mChange = findViewById(R.id.chanpassmainv);
      mChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ChangePassword.class));
>>>>>>> Yaser
            }
        });


<<<<<<< HEAD
        Bundle bundle = getIntent().getExtras();
        userID1 = bundle.getString("emailadd");

        final Intent myIntent = new Intent(MainActivity.this, Profile.class);
        myIntent.putExtra("emailadd", userID1);

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
                        overridePendingTransition(0, 0);
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
    }

    private void showdata() {
        pd.setTitle("טוען נתונים...") ;
        pd.show();
        pd.setCancelable(false);
             fStore.collection("users")
                     .get()
                     .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                         @Override
                         public void onComplete(@NonNull Task<QuerySnapshot> task) {
                           pd.dismiss();
                           for(DocumentSnapshot doc: task.getResult()){
                               InfoFromDataBase info = new InfoFromDataBase(doc.getString("Email"),
                                       doc.getString("PhoneNumber"),
                                       doc.getString("FullName"));
                                usersinfo.add(info);
                           }
                           adapeter = new UsersAdapter(MainActivity.this,usersinfo)  ;
                           mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                           mRecyclerView.setAdapter(adapeter);
                         }
                     })
                     .addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                             pd.dismiss();
                               Toast.makeText(MainActivity.this,"Error Loading the Info!",Toast.LENGTH_SHORT).show();
                         }
                     }) ;
    }
    }

    /*
    public void loadNotes(final View v){
        final List<InfoFromDataBase> data=new ArrayList<>();
        usersRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        //String data = "";
                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            InfoFromDataBase infodata = new InfoFromDataBase();
                            InfoFromDataBase note = documentSnapshot.toObject(InfoFromDataBase.class);
                            infodata.setFullName(note.getFullName());
                            infodata.getFullName();
                            infodata.setEmail(note.getEmail());
                            infodata.getEmail();
                            infodata.setPhoneNumber(note.getPhoneNumber());
                            infodata.getPhoneNumber();

                            data.add(infodata);
                        }
                    }
                });
                    recyclerView = findViewById(R.id.recyclerView);
                    recyclerView.setHasFixedSize(true);
                    layoutManager = new LinearLayoutManager(this);
                    recyclerView.setLayoutManager(layoutManager);
                    mAdapter = new Info (MainActivity.this,data);
                    recyclerView.setAdapter(mAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
=======




>>>>>>> Yaser
    }

        public void logout (View view){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }
}
*/

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

        //  }

//    private void showList(){
//        FirebaseRecyclerOptions option = new FirebaseRecyclerOptions.Builder<InfoFromDataBase>()
//                .setQuery(reference,InfoFromDataBase.class).build();
//
//        adapter = new FirebaseRecyclerAdapter<InfoFromDataBase, UserViewModel>(option) {
//            @Override
//            protected void onBindViewHolder(@NonNull UserViewModel holder, int position, @NonNull InfoFromDataBase model) {
//                holder.txt1.setText(model.getName());
//                holder.txt2.setText(model.getPhoneNumber());
//                holder.txt3.setText(model.getEmail());
//            }
//
//            @NonNull
//            @Override
//            public UserViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.activity_recycler,parent,false);
//                return new UserViewModel(view);
//            }
//        };
//
//        adapter.startListening();
//        adapter.notifyDataSetChanged();
//        recyclerView.setAdapter(adapter);
//    }
//}
