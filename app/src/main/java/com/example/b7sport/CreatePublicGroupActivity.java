package com.example.b7sport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class CreatePublicGroupActivity extends AppCompatActivity {
    DatabaseReference firebaseDatabase;
    public TextView textid, textName, textType, textStreet,textNeighborh,textActivity,textLighting,textSportType;//I dont know if I must add the lat and lon
    Button selctgrbtn;
    TextView secretTextView;
    EditText group_p_number,group_name;
    Arena arena;
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


        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Groups");


        selctgrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name =group_name.getText().toString().trim();
                int number = Integer.parseInt( group_p_number.getText().toString().trim());
                if(CheckGrName(name)==true && CheckNumber(number)==true) {
                    Group g = Group.makeGroup(name, name, number, false, arena);
                    firebaseDatabase.push().setValue(g);
                    Toast.makeText(CreatePublicGroupActivity.this, "Data inserted successfully", Toast.LENGTH_LONG).show();
                    Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                }
            }
        });

    }
    public boolean CheckGrName(String name)
    {
        if(name.equals(""))
        {
            group_name.setError("חובה למלות שדה זה");
            return false;
        }
        else
        {
            return true;
        }

    }

    public boolean CheckNumber(int n)
    {
        if(n > 0)
            return true;
        else
        {
            group_p_number.setError("מספר שחקנים חייב להיות גדול מאפס");
            return false;
        }

    }

}

