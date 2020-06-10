package com.example.b7sport;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;




import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b7sport.Update_Adress;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class OtherUserProfile extends AppCompatActivity {
    private TextView mName,mEmail,mPhonenumber,mAddress;
    Button mUpdateAdrressbtn,mUploadProfilePic,mAddFriend;
    private FirebaseDatabase database;
    private DatabaseReference UserRef,UserRef1;
    FirebaseFirestore fStore;
    ImageView mProfilePictore;
    StorageReference storageReference;
    static String nodeKey;
    boolean result = false;

    // static String photoProvider = MainActivity.emailID;
    FirebaseAuth fAuth;
    private static final String USERS = "EDMT_FIREBASE";
    ProgressDialog pd;
    static String userID1 = EmailAdapter.selecteduser.userEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);

        Bundle bundle = getIntent().getExtras();

        // final String userID1 = bundle.getString("emailadd");


        fAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);

        final Intent myIntent = new Intent(OtherUserProfile.this,MainActivity.class);
        final Intent Address_intent = new Intent(OtherUserProfile.this, Update_Adress.class);

        myIntent.putExtra("emailadd",userID1);
        mName = findViewById(R.id.FullName1);
        mEmail = findViewById(R.id.Email1);
        mPhonenumber = findViewById(R.id.PhoneNumber1);
        mAddress = findViewById(R.id.Address1);
        mAddFriend = findViewById(R.id.add_friend);
        database = FirebaseDatabase.getInstance();

        UserRef1 = database.getReference("EDMT_FIREBASE");
        //UserRef = database.getReference("EDMT_FIREBASE");
        mProfilePictore = findViewById(R.id.ProfileImage);
        //Init Database
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        pd.setTitle("טוען נתונים...") ;
        pd.show();
        pd.setCancelable(false);
        show(userID1);

        if(CheckIfFriends()){
            return;
        }else{
        final Map<String,Object> map = new HashMap<>();
        map.put("FriendEmail",Login.Email);
        final Map<String,Object> map1 = new HashMap<>();
        map1.put("FriendEmail",EmailAdapter.selecteduser.userEmail);
        mAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot data : dataSnapshot.getChildren()){

                        if(EmailAdapter.selecteduser.userEmail.equals(data.child("email").getValue().toString())){
                            nodeKey = data.getKey();
                        }
                        }
                        UserRef1.child(nodeKey + "/Friends").push().setValue(map);
                        for(DataSnapshot data : dataSnapshot.getChildren()) {

                            if (Login.Email.equals(data.child("email").getValue().toString())) {
                                nodeKey = data.getKey();
                            }
                        }
                        UserRef1.child(nodeKey + "/Friends").push().setValue(map1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode==RESULT_OK){
            Uri imageUri = data.getData();
            //  mProfilePictore.setImageURI(imageUri);

            uploadProfilePhoto(imageUri);
        }
    }

    public void uploadProfilePhoto(Uri imageUri){
        final StorageReference fileRef = storageReference.child(EmailAdapter.selecteduser.userEmail);
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //     Toast.makeText(Profile.this,"תמונה הועליתה בהצלחה!",Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(mProfilePictore);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(OtherUserProfile.this,"Faild for some reason!",Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void show(final String email) {
        StorageReference profileRef = storageReference.child(EmailAdapter.selecteduser.userEmail);
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(mProfilePictore);
            }
        });


        UserRef = FirebaseDatabase.getInstance().getReference("EDMT_FIREBASE");
        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data1:dataSnapshot.getChildren())
                    if(email.equals( data1.child("email").getValue().toString())){
                        mName.setText(data1.child("FullName").getValue().toString());
                        mEmail.setText(data1.child("email").getValue().toString());
                        mPhonenumber.setText(data1.child("PhoneNumber").getValue().toString());
                        mAddress.setText(data1.child("address").getValue().toString());
                        pd.dismiss();
                        break;
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    boolean CheckIfFriends(){
         int flag = -1;
        UserRef1 = database.getReference("EDMT_FIREBASE");
            UserRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot data : dataSnapshot.getChildren()) {

                        if (Login.Email.equals(data.child("email").getValue().toString())) {
                             for(DataSnapshot d : data.child("Friends").getChildren() )
                             {
                                    if(d.child("FriendEmail").getValue().toString().equals(EmailAdapter.selecteduser.userEmail));
                                 {
                                     mAddFriend.setVisibility(View.INVISIBLE);
                                 }
                             }

                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        String UserID = "EDMT_FIREBASE/"+ nodeKey +"/Friends";
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(UserID);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot data : dataSnapshot.getChildren()){
                        Log.d("asd1","TRUE");
                        if(Login.Email.equals(data.child("FriendEmail").getValue().toString())){
                            mAddFriend.setVisibility(View.INVISIBLE);
                            result = true;
                            Log.d("asd","TRUE");
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return result;
    }

}
