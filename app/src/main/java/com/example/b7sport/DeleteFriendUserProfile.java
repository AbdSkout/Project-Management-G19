package com.example.b7sport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class DeleteFriendUserProfile extends AppCompatActivity {
    private TextView mName,mEmail,mPhonenumber,mAddress;
    Button mAddFriend;
    private FirebaseDatabase database;
    private DatabaseReference UserRef,UserRef1;
    FirebaseFirestore fStore;
    ImageView mProfilePictore;
    StorageReference storageReference;
    static String nodeKey;
    boolean result = false;
    final FirebaseDatabase data = FirebaseDatabase.getInstance();
    final DatabaseReference ref = data.getReference();
    // static String photoProvider = MainActivity.emailID;
    FirebaseAuth fAuth;
    private static final String USERS = "EDMT_FIREBASE";
    ProgressDialog pd;
   // static String userID1 = EmailAdapter.selecteduser.userEmail;
    static String Email =  FriendsProfileAdapter.Email;
    final String LogedIn = Login.Email;
    static String LoggedInKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_friend_user_profile);

        Bundle bundle = getIntent().getExtras();

        fAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);

        final Intent myIntent = new Intent(DeleteFriendUserProfile.this,MainActivity.class);
        //final Intent Address_intent = new Intent(DeleteFriendUserProfile.this, Update_Adress.class);

       // myIntent.putExtra("emailadd",userID1);
        mName = findViewById(R.id.FullName1);
        mEmail = findViewById(R.id.Email1);
        mPhonenumber = findViewById(R.id.PhoneNumber1);
        mAddress = findViewById(R.id.Address1);
        mAddFriend = findViewById(R.id.removeFriend);
        database = FirebaseDatabase.getInstance();
        UserRef = database.getReference();
        UserRef1 = database.getReference("EDMT_FIREBASE");
        mProfilePictore = findViewById(R.id.ProfileImage);
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        pd.setTitle("טוען נתונים...") ;
        pd.show();
        pd.setCancelable(false);
        show(Email);

            mAddFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot data : dataSnapshot.getChildren()){
                                if(LogedIn.equals(data.child("email").getValue())){
                                    LoggedInKey = data.getKey();
                                    break;
                                }
                            }
                            UserRef = database.getReference("EDMT_FIREBASE/"+LoggedInKey+"/Friends");
                            UserRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(DataSnapshot data1 : dataSnapshot.getChildren()){
                                        if(data1.child("FriendEmail").getValue().toString().equals(Email)){
                                            delete("EDMT_FIREBASE/"+LoggedInKey+"/Friends/"+data1.getKey());
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });
      //  }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode==RESULT_OK){
            Uri imageUri = data.getData();
            uploadProfilePhoto(imageUri);
        }
    }

    public void uploadProfilePhoto(Uri imageUri){
        final StorageReference fileRef = storageReference.child(Email);
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
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
                Toast.makeText(DeleteFriendUserProfile.this,"Faild for some reason!",Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void show(final String email) {
        StorageReference profileRef = storageReference.child(Email);
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

    public  void delete (final String key) {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ref.child(key).removeValue();
                        startActivity(new Intent(getApplicationContext(),RecyclerViewFriends.class));
                    }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
}
