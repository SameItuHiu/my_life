package com.example.mylife.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylife.R;
import com.example.mylife.data.DataWallet;
import com.example.mylife.data.DataWeekly;

import java.util.ArrayList;

public class RecyclerWeeklyAdapter extends RecyclerView.Adapter<RecyclerWeeklyAdapter.ListViewHolder>{

    private ArrayList<DataWeekly> list = new ArrayList<>();
//    private OnItemClickCallback onItemClickCallback;

    public void setData(ArrayList<DataWeekly> items) {
        list.clear();
        list.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerWeeklyAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_weekly, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerWeeklyAdapter.ListViewHolder holder, int position) {
        DataWeekly data = list.get(position);
        holder.tvActivity.setText(data.getActivity());
        holder.tvPlace.setText(data.getPlace());
        holder.tvTime.setText(data.getTime());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemClickCallback.onItemClicked(list.get(holder.getAdapterPosition()));
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlace, tvActivity, tvTime;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvActivity = itemView.findViewById(R.id.dataActivity);
            tvPlace = itemView.findViewById(R.id.dataLocation);
            tvTime = itemView.findViewById(R.id.dateTime);
        }
    }

//    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
//        this.onItemClickCallback = onItemClickCallback;
//    }
//    public interface OnItemClickCallback {
//        void onItemClicked(DataWeekly data);
//    }


}
