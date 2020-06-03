package com.example.b7sport;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Random;

public class CreatePublicGroupActivity extends AppCompatActivity {
    DatabaseReference firebaseDatabase;

    public TextView textid, textName, textType, textStreet,textNeighborh,textActivity,textLighting,textSportType,secretcode;//I dont know if I must add the lat and lon
    Button selctgrbtn;
    TextView secretTextView;
    EditText group_p_number,group_name;

    RadioButton privateG;
    RadioButton publicG;
    Arena arena;
    int id;
    static boolean result = true;
    private FirebaseDatabase database;

    private Query UserRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_public_group);


        textName = findViewById(R.id.gr_name1);
        textType = findViewById(R.id.gr_type1);
        textStreet = findViewById(R.id.gr_street1);
        textNeighborh =findViewById(R.id.gr_gr_neighbor1);
        textActivity = findViewById(R.id.gr_activity1);
        textSportType = findViewById(R.id.gr_sporttype1);
        textLighting = findViewById(R.id.gr_lighting1);
        textid= findViewById(R.id.gr_id1);
        selctgrbtn= findViewById(R.id.createg1);
        group_name = findViewById(R.id.group_name);
        group_p_number = findViewById(R.id.players_number);
        privateG= findViewById(R.id.radioprivate);
        publicG= findViewById(R.id.radiopublic);
        secretcode=findViewById(R.id.secretcode);
        secretTextView= findViewById(R.id.secrettext);


        database = FirebaseDatabase.getInstance();
       // UserRef = database.getReference("Groups");

        //put the values ...
        privateG.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked) {
                    secretcode.setVisibility(View.INVISIBLE);
                    secretTextView.setVisibility(View.INVISIBLE);
                }
                else{
                    secretcode.setVisibility(View.VISIBLE);
                    secretTextView.setVisibility(View.VISIBLE);
                }

            }
        });
        //put the values ...


        arena= RecyclerViewArena.groundList.get(ArenaAdapter.id);
        textName.setText("שם מגרש : " + arena.getName());
        textType.setText("סוג מגרש : " +String.valueOf(arena.getType()));
        textStreet.setText("כביש : " +String.valueOf(arena.getStreet()));
        textNeighborh.setText("שכונה : " +arena.getNeighbor());
        textActivity.setText("פעילות : " +arena.getActivity());
        textLighting.setText("תאורה : " +arena.getLighing());
        textSportType.setText("סוג ספורט : " +arena.getSport_type());
        textid.setVisibility(View.INVISIBLE);



        //firebaseDatabase = FirebaseDatabase.getInstance().getReference("Groups");

        selctgrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String name =group_name.getText().toString();
                String n = group_p_number.getText().toString();
                if(CheckGrName(name)==true && CheckNumber(n)==true) {
                    int number = Integer.parseInt( group_p_number.getText().toString());
                    boolean isPrivate=!(publicG.isChecked());
                    Group g = Group.makeGroup(name, Integer.toString(id) , number, isPrivate, arena);

                    //                    synchronized (firebaseDatabase) {}
                    if(isPrivate)
                        g.setSecretcode(secretcode.getText().toString());
                    else g.setSecretcode("");

                    FirebaseDatabase fbase = FirebaseDatabase.getInstance();
                    UserRef = FirebaseDatabase.getInstance().getReference("Groups");
                   // reference.addValueEventListener(new ValueEventListener() {
              //      ChechValidName(name);
             //       show(name);
                    /*
                    UserRef.addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot data : dataSnapshot.getChildren()){
                                if(data.child("groupname").getValue().toString().equals(name)){
                                    result =  false;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
*/
                    CheckGroupName(name);
                    if(result){
                        firebaseDatabase = FirebaseDatabase.getInstance().getReference("Groups");
                        firebaseDatabase.push().setValue(g);
                    Toast.makeText(CreatePublicGroupActivity.this, "Data inserted successfully", Toast.LENGTH_LONG).show();
                    Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("emailadd",MainActivity.emailID);
                    startActivity(intent);
                    }else{
                        Toast.makeText(CreatePublicGroupActivity.this, "Group with same name!", Toast.LENGTH_LONG).show();

                    }
                }
//                else startActivity();
            }
        });

    }/*
=======
        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Groups");

>>>>>>> master
        selctgrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name =group_name.getText().toString();
                String n = group_p_number.getText().toString();
                if(CheckGrName(name)==true && CheckNumber(n)==true) {
<<<<<<< HEAD

                    final FirebaseDatabase data = FirebaseDatabase.getInstance();
                    //final DatabaseReference ref1 = data.getReference("Groups");
                    final DatabaseReference ref = data.getReference("Groups");
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot data1 : dataSnapshot.getChildren()) {
                                if (group_name.getText().toString().equals(data1.child("groupname").getValue().toString())) {
                                    Toast.makeText(CreatePublicGroupActivity.this, "There is group with the same name!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });

                    //ref.setValue(Integer.toString(id+1));
                  //  getId();
=======
                    getId();
>>>>>>> master
                    int number = Integer.parseInt( group_p_number.getText().toString());
                    boolean isPrivate=!(publicG.isChecked());
                    Group g = Group.makeGroup(name, Integer.toString(id) , number, isPrivate, arena);

                    //                    synchronized (firebaseDatabase) {}
                    if(isPrivate)
                        g.setSecretcode(secretcode.getText().toString());
                    else g.setSecretcode("");

                    firebaseDatabase.push().setValue(g);
                    Toast.makeText(CreatePublicGroupActivity.this, "Data inserted successfully", Toast.LENGTH_LONG).show();
                    Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
//                else startActivity();
            }
        });

<<<<<<< HEAD
    }*/

    public boolean CheckGrName(String name)
    {
        if(name.equals("") || name == null)
        {
            //   group_name.setError("חובה למלות שדה זה");
         //   group_name.setError("חובה למלות שדה זה");
            return false;
        }
        else
        {
            return true;
        }

    }

    public boolean CheckNumber(String num)
    {
        if(!num.equals("") && num !=null)
        {
            int n = Integer.parseInt(num);
            if(n > 0)
                return true;
            else
            {
                //     group_p_number.setError("מספר שחקנים חייב להיות גדול מאפס");
           //     group_p_number.setError("מספר שחקנים חייב להיות גדול מאפס");
                return false;
            }
        }
        else {
            //group_p_number.setError("חייב למלא השדה הזה ");
            return false;
        }

    }

    public void ChechValidName(final String groupname){
        FirebaseDatabase fbase = FirebaseDatabase.getInstance();
        DatabaseReference reference = fbase.getReference().child("Groups");


        
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data1:dataSnapshot.getChildren()){
                    ArrayList<String> cityList = new ArrayList<>();
                    cityList.add(data1.getKey());
                    if(groupname.equals( data1.child("groupname").getValue().toString())){
                        result = false;
                        break;
                    }
                }
                /*
                databaseReference.child("Participants").push().setValue(map);
                Toast.makeText(GroupProfile.this, "Joined to Group!", Toast.LENGTH_SHORT).show();
*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void CheckGroupName(final String newname){
        DatabaseReference ref;
        ref=database.getReference("Groups");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    if(newname.equals(data.child("groupname").getValue().toString())){
                        result = false;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void getId()
    {
        final FirebaseDatabase data = FirebaseDatabase.getInstance();
        //final DatabaseReference ref1 = data.getReference("Groups");
        final DatabaseReference ref = data.getReference("Groups/id");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data1 : dataSnapshot.getChildren()) {
                    if (MainActivity.emailID.equals(data1.child("groupname").getValue().toString())) {
                           Toast.makeText(CreatePublicGroupActivity.this, "Already in Group!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    id = Integer.parseInt(dataSnapshot.getValue().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        ref.setValue(Integer.toString(id+1));

    }

    public void show(final String email) {
        UserRef = FirebaseDatabase.getInstance().getReference().child("Groups");
        UserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String new_adsress = textName.getText().toString().trim();
                String name1="";
                String name2;
                for (DataSnapshot d : dataSnapshot.getChildren())
                {
                    name2=d.child("groupname").getValue().toString();
                    if(email.equals(name2))
                    {
                        result = false;
                        Toast.makeText(CreatePublicGroupActivity.this, "Already in Group!", Toast.LENGTH_SHORT).show();
                        /*
                        name1=d.getKey().toString();
                        ref1.child(name1).child("address").setValue(new_adsress);
                        Toast.makeText(Update_Adress.this, "Address Updated!", Toast.LENGTH_SHORT).show();
                        */
                        break;
                    }
                }
                //startActivity(new Intent(getApplicationContext(),Profile.class));

                    id = Integer.parseInt(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    //    });
    //    ref.setValue(Integer.toString(id+1));

    }





