package com.example.b7sport;

import android.content.Context;
import android.content.Intent;
import android.hardware.ConsumerIrManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.awt.font.TextAttribute;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ArenaAdapter extends  RecyclerView.Adapter<ArenaAdapter.ViewHolder> implements Filterable {
    public AppCompatActivity z= new AppCompatActivity();
    private Context context;
    private List<Arena> listfull;
    private List<Arena> list;
    private Arena arena;
    static int id;

    public ArenaAdapter(Context context, List<Arena> listfull) {
        this.context = context;
        this.listfull = listfull;
        list=new ArrayList<>(listfull);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_singleitem, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        arena = listfull.get(position);
//
//        holder.textName.setText(arena.getName());
//        holder.textType.setText(String.valueOf(arena.getType()));
//        holder.textStreet.setText(String.valueOf(arena.getStreet()));
//        holder.textNeighborh.setText(arena.getNeighbor());
//        holder.textActivity.setText(arena.getActivity());
//        holder.textLighting.setText(arena.getLighing());
//        holder.textSportType.setText(arena.getSport_type());
        holder.textid.setText(String.valueOf(arena.getId()));
        holder.textName.setText("שם מגרש : " + arena.getName());
        holder.textType.setText("סוג מגרש : " +String.valueOf(arena.getType()));
        holder.textStreet.setText("כביש : " +String.valueOf(arena.getStreet()));
        holder.textNeighborh.setText("שכונה : " +arena.getNeighbor());
        holder.textActivity.setText("פעילות : " +arena.getActivity());
        holder.textLighting.setText("תאורה : " +arena.getLighing());
        holder.textSportType.setText("סוג ספורט : " +arena.getSport_type());

        holder.linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent  = new Intent(context,CreatePublicGroupActivity.class);
                int x =Integer.parseInt(holder.textid.getText().toString());
                Toast.makeText(context, "Item Number "+x +" selected..", Toast.LENGTH_SHORT).show();
                id=x;
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return listfull.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textid, textName, textType, textStreet,textNeighborh,textActivity,textLighting,textSportType;//I dont know if I must add the lat and lon
        LinearLayout linear1;

        public ViewHolder(View itemView ) {
            super(itemView);

            textName = itemView.findViewById(R.id.gr_name);
            linear1 = itemView.findViewById(R.id.linear1);
            textType = itemView.findViewById(R.id.gr_type);
            textStreet = itemView.findViewById(R.id.gr_street);
            textNeighborh = itemView.findViewById(R.id.gr_gr_neighbor);
            textActivity = itemView.findViewById(R.id.gr_activity);
            textSportType = itemView.findViewById(R.id.gr_sporttype);
            textLighting = itemView.findViewById(R.id.gr_lighting);
            textid=itemView.findViewById(R.id.gr_id);


        }
    }



    @Override
    public Filter getFilter() {
        return arenaFilter;
    }
    private Filter arenaFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Arena> filteredlist = new ArrayList<>();
            if (constraint == null || constraint.length()==0) {
                filteredlist.addAll(listfull);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Arena item: listfull){
                    if(item.getName().toLowerCase().contains(filterPattern)){
                        filteredlist.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values=filteredlist;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((Collection<? extends Arena>) results.values);

            notifyDataSetChanged();
        }
    };
}
