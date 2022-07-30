package com.varsitycollege.imbizoappwil02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerCollectionAdapter extends RecyclerView.Adapter<recyclerCollectionAdapter.MyViewHolder> {

    //Global Variable
    private ArrayList<Collection> tempCollectionList;
    Context context;
    private OnCollectionClickListerner mListener;

    //Constructor
    public recyclerCollectionAdapter(ArrayList<Collection> collectList,Context context)
    {
        this.tempCollectionList = collectList;
        this.context =context;
    }

    //Interface of onclicklistener
    public interface OnCollectionClickListerner {
        void onCollectionClick(int position);
    }

    //Method set the onclicklistener
    public void setOnCollectionClickListerner(OnCollectionClickListerner listener){
        mListener =listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_category,parent,false);
        return new MyViewHolder(itemView,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String name = tempCollectionList.get(position).getCollectionName();
        holder.txt_categoryName.setText(name);
    }

    @Override
    public int getItemCount() {
        return tempCollectionList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_categoryName;
        OnCollectionClickListerner OnCollectionClickListerner;
        public MyViewHolder(@NonNull View itemView,OnCollectionClickListerner listerner) {
            super(itemView);
            txt_categoryName= itemView.findViewById(R.id.txt_category);
            this.OnCollectionClickListerner = OnCollectionClickListerner;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listerner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listerner.onCollectionClick(position);
                        }
                    }
                }
            });
        }
    }
}
