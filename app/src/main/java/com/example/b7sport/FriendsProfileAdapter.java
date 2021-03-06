package com.example.b7sport;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.List;

public class FriendsProfileAdapter extends RecyclerView.Adapter<FreindsProfileViewHolder>{
    AllUsers listActivity;
    List<InfoFromDataBase> infolist;
    Context context;
    final FirebaseDatabase data = FirebaseDatabase.getInstance();
    final DatabaseReference ref = data.getReference("EDMT_FIREBASE");
    static String Email;
    public FriendsProfileAdapter(Context listActivity,List<InfoFromDataBase> infolist){
        this.context = listActivity;
        this.infolist = infolist;
    }
    @NonNull
    @Override
    public FreindsProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View v = LayoutInflater.from(context).inflate(R.layout.activity_recycler, parent, false);
        //return new ViewHolder(v);
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_friendsprofile,parent,false);
        FreindsProfileViewHolder viewHolder = new FreindsProfileViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FreindsProfileViewHolder holder, final int position) {
        holder.fullname.setText(infolist.get(position).getFullName());
        holder.phonenumber.setText(infolist.get(position).getPhoneNumber());
        holder.email.setText(infolist.get(position).getEmail());
        holder.address.setText(infolist.get(position).getAddress());
        Email =infolist.get(position).getEmail();
        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(context,DeleteFriendUserProfile.class);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return infolist.size();
    }




}