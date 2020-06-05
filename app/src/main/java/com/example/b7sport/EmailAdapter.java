package com.example.b7sport;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class EmailAdapter extends RecyclerView.Adapter<EmailAdapter.ViewHolder> implements Filterable {

    public AppCompatActivity z = new AppCompatActivity();
    private Context context;
    private List<String> list;
    private List<String> fulllist;
    static int id;

    public EmailAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;


    }

    @NonNull
    @Override
    public EmailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.single_email, parent, false);
        return new EmailAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.email.setText(list.get(position));
    }


    @Override
    public int getItemCount() {
        return list.size();

    }




    @Override
    public Filter getFilter() {
        return arenaFilter;
    }
    private Filter arenaFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(fulllist);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (String item : fulllist) {
                    if (item.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    public void setfullValue(ArrayList<String> groundList)
    {
        fulllist=new ArrayList<>(groundList);
    }





    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView email;
        LinearLayout linear1;

        public ViewHolder(View itemView) {
            super(itemView);

            email = itemView.findViewById(R.id.single_email);

        }
    }

}
//package com.example.b7sport;
//
//public class EmailAdapter {
//}




