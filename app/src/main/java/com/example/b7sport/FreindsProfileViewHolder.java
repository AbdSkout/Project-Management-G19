package com.example.b7sport;


import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.core.content.ContextCompat.startActivity;

public class FreindsProfileViewHolder extends RecyclerView.ViewHolder {
    static String DeletedEmail;
    TextView fullname,email,phonenumber,address;
    View mView;
    LinearLayout linear;
    public FreindsProfileViewHolder(@NonNull View itemView){
        super(itemView);

        mView = itemView;
        linear = itemView.findViewById(R.id.FriendLinear);
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        fullname = itemView.findViewById(R.id.Nameid);
        email = itemView.findViewById(R.id.Emailaddress);
        phonenumber = itemView.findViewById(R.id.PhoneNumber);
        address = itemView.findViewById(R.id.Address111);

    }

    private ViewHolder.ClickListener mClickListener;

    public interface ClickListener{
        void onItemClick(View view , int position);

    }
    public void setonClickListener(ViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }

}
