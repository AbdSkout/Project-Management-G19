package com.example.b7sport;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class GroupProfile extends AppCompatActivity {
    public TextView textid, textName, textType, textStreet,textNeighborh,textActivity,textLighting,textSportType,groupname ,numberofplayers,isprivate;//I dont know if I must add the lat and lon
    Button mJoinGroup,mCancelJpinGroup;
    String userID1;
    FirebaseDatabase firebaseDatabase;
    static  Group selectedgl;
    static String key;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;


    Button showmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_profile);
        firebaseDatabase = FirebaseDatabase.getInstance();

/*
        Bundle bundle = getIntent().getExtras();
        userID1 = bundle.getString("emailadd");
        final Intent myIntent = new Intent(GroupProfile.this, MainActivity.class);
        myIntent.putExtra("emailadd", userID1);
*/

        textName =findViewById(R.id.gr_name3);
//        linear1 = findViewById(R.id.linear3);

        textName =findViewById(R.id.gr_name3);

        textType = findViewById(R.id.gr_type3);
        textStreet = findViewById(R.id.gr_street3);
        textNeighborh =findViewById(R.id.gr_gr_neighbor3);
        textActivity = findViewById(R.id.gr_activity3);
        textSportType =findViewById(R.id.gr_sporttype3);
        textLighting = findViewById(R.id.gr_lighting3);
        textid = findViewById(R.id.gr_id3);
        groupname = findViewById(R.id.sg_grname3);
        numberofplayers = findViewById(R.id.sg_playersnumber3);
        isprivate = findViewById(R.id.sg_isprivate3);

        mJoinGroup = findViewById(R.id.JoinpGroup);
        showmap =findViewById(R.id.showmap);

        textid.setText(String.valueOf(GroupAdapter.selected_group.getArenaid()));
        textName.setText("שם מגרש : " + GroupAdapter.selected_group.getArenaname());
        textType.setText("סוג מגרש : " +String.valueOf(GroupAdapter.selected_group.getArenatype()));
        textStreet.setText("כביש : " +String.valueOf(GroupAdapter.selected_group.getArenastreet()));
        textNeighborh.setText("שכונה : " +GroupAdapter.selected_group.getArenaneighbor());
        textActivity.setText("פעילות : " +GroupAdapter.selected_group.getArenaactivity());
        textLighting.setText("תאורה : " +GroupAdapter.selected_group.getArenalighing());
        textSportType.setText("סוג ספורט : " +GroupAdapter.selected_group.getArenasport_type());
        groupname.setText("שם קבוצה: " + GroupAdapter.selected_group.getGroupname());
        numberofplayers.setText("מספר שחקנים בקבוצה: " + GroupAdapter.selected_group.getPlayersnumber());

/*
        Bundle bundle = getIntent().getExtras();
        userID1 = bundle.getString("emailadd");
*/
        final Intent myIntent = new Intent(GroupProfile.this, RecyclerViewGroup.class);
        myIntent.putExtra("emailadd", MainActivity.emailID);


        if(GroupAdapter.selected_group.isIsprivate())
            isprivate.setText("קבוצה פרטית");
        else
            isprivate.setText("קבוצה ציבורית");
        String groupp = GroupAdapter.selected_group.getNodeKey().toString();
        String GroupPath = "Groups" +"/"+ groupp;
        databaseReference = firebaseDatabase.getReference(GroupPath);
        databaseReference1 = firebaseDatabase.getReference(GroupPath +"/Participants");


        mCancelJpinGroup=findViewById(R.id.CancelJoin);
        mJoinGroup = findViewById(R.id.JoinpGroup);

        mCancelJpinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 startActivity(myIntent);
                 finish();
            }
        });
        mJoinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupid = String.valueOf(GroupAdapter.selected_group.getArenaid());
                final Map<String, Object> map = new HashMap<String, Object>();
                map.put("UserEmail", MainActivity.emailID);

                databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot data1 : dataSnapshot.getChildren()) {
                            if (MainActivity.emailID.equals(data1.child("UserEmail").getValue().toString())) {
                                Toast.makeText(GroupProfile.this, "Already in Group!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        databaseReference.child("Participants").push().setValue(map);
                        Toast.makeText(GroupProfile.this, "Joined to Group!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                mCancelJpinGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      //  startActivity(myIntent);
                    }
                });

                mJoinGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String groupid =String.valueOf(GroupAdapter.selected_group.getArenaid());
                        final  Map<String,Object> map = new HashMap<String,Object>();
                        map.put("UserEmail",MainActivity.emailID);

                        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot data1:dataSnapshot.getChildren()){
                                    if(MainActivity.emailID.equals( data1.child("UserEmail").getValue().toString())){
                                        Toast.makeText(GroupProfile.this, "Already in Group!", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                                databaseReference.child("Participants").push().setValue(map);
                                Toast.makeText(GroupProfile.this, "Joined to Group!", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });



                    }
                });



            }
        });





        showmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ArenaMapsActivity.class));
            }
        });
    }
            public void getCurrentNodeKey(){
                final String mGroupId = databaseReference.push().getKey();
                //databaseReference.child(mGroupId).setValue(new Group());
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String new_adsress = MainActivity.emailID;
                        //key = dataSnapshot.get
                        Toast.makeText(GroupProfile.this, mGroupId, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        }
