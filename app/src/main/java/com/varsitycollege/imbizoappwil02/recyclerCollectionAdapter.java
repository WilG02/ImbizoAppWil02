package com.varsitycollege.imbizoappwil02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class recyclerCollectionAdapter extends RecyclerView.Adapter<recyclerCollectionAdapter.MyViewHolder> {
        //implements Filterable {

    //Global Variable
    ArrayList<Collection> tempCollectionList;
    Context context;
    private OnCollectionClickListener mListener;


    //------
    /*CollectionClickListener collectionClickListener;
    public ArrayList<Collection> getTempCollectionListFilter = new ArrayList<>();
    ArrayList<Collection> collections = new ArrayList<>();*/

    //------

    //Constructor
    public recyclerCollectionAdapter(ArrayList<Collection> collectList,Context context)
    {
       // this.getTempCollectionListFilter = collectList;
        this.tempCollectionList = collectList;
        this.context =context;
    }

    //https://www.geeksforgeeks.org/searchview-in-android-with-recyclerview/
    // method for filtering our recyclerview items.
    public void filterList(ArrayList<Collection> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        this.tempCollectionList = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

  /*  @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() ==0 ){
                    filterResults.values = tempCollectionList;
                    filterResults.count = tempCollectionList.size();
                }else{
                    String searchText = constraint.toString().toLowerCase();
                    for (Collection item:tempCollectionList) {
                        //if to check what  search must match
                        //item.getCategoryDescription().toLowerCase().contains(searchText)
                        if (item.getCategoryName().toLowerCase().contains(searchText)){
                            if (collections.size()>1){
                                collections.add(item);
                            }
                            //else{
                                collections.add(item);
                            //}
                        }
                    }
                    filterResults.values = collections;
                    filterResults.count = collections.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                tempCollectionList = (ArrayList<Collection>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }*/

    //---------
    //https://www.youtube.com/watch?v=MWlxFccYit8

    //Interface of onclicklistener
    public interface CollectionClickListener {
        void selectedCollection(Collection collect);
    }

    //Constructor
    public recyclerCollectionAdapter(ArrayList<Collection> collectList,Context context, CollectionClickListener collectionClickListener)
    {
        this.tempCollectionList = collectList;
        this.context =context;
        //this.collectionClickListener = collectionClickListener;
    }



    //---------



    //Interface of onclicklistener
    public interface OnCollectionClickListener {
        void onCollectionClick(int position);
    }

    //Method set the onclicklistener
    public void setOnCollectionClickListener(OnCollectionClickListener listener){
        mListener =listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_categoryName;
        OnCollectionClickListener OnCollectClickListener;

        public MyViewHolder(@NonNull View itemView,OnCollectionClickListener listener) {
            super(itemView);
            txt_categoryName= itemView.findViewById(R.id.txt_category);
            this.OnCollectClickListener = OnCollectClickListener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onCollectionClick(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public recyclerCollectionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_category,parent,false);
        return new MyViewHolder(itemView,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Collection collection = tempCollectionList.get(position);
        String name = tempCollectionList.get(position).getCategoryName();
        holder.txt_categoryName.setText(name);

        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectionClickListener.selectedCollection(collection);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return tempCollectionList.size();
    }
}
