package com.beny.drinkwaterreminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;

    public MyAdapter(Context context) {
        this.context = context;

     }

    @NonNull

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row , parent , false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
        holder.arrayListTxt.setText( DrinkedList.Drinked.get(position).toString());

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "swipe!", Toast.LENGTH_SHORT).show();

//                int index = position;
                int ml = DrinkedList.Drinked.get(position).getMl();

                DrinkedList.Drinked.remove(position);
//                DrinkedList.Drinked.remove(holder.getAbsoluteAdapterPosition());
                notifyDataSetChanged();
                notifyItemRemoved(position);
                List l = new List();
                l.saveData(context);
                l.transfer_ml(ml);
                //send index with shared prefrences to home fragment
//                HomeFragment fragment = new HomeFragment();
//                fragment.deleteCup(ml);




            }
        });

    }


    @Override
    public int getItemCount() {
        return DrinkedList.Drinked.size();
    }


//    public void setArrayList (ArrayList<DrinkedList> drinked){
//        this.drinked = drinked ;
//        notifyDataSetChanged();
//    }
//    public ArrayList<DrinkedList> getArrayList(){
//        return drinked;
//    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView arrayListTxt ;
        Button deleteBtn ;

        public MyViewHolder(View itemView) {
            super(itemView);
            arrayListTxt = itemView.findViewById(R.id.arrayListText);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);


        }
    }


//    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
//        @Override
//        public boolean onMove( RecyclerView recyclerView,  RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//            return false;
//        }
//
//        @Override
//        public void onSwiped( RecyclerView.ViewHolder viewHolder, int direction) {
//            drinked.remove(viewHolder.getAbsoluteAdapterPosition());
//            notifyDataSetChanged();
//        }
//    };
}
