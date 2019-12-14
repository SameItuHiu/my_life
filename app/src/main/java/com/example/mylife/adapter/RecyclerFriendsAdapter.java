package com.example.mylife.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylife.R;
import com.example.mylife.data.DataFriends;
import com.example.mylife.data.DataWeekly;

import java.util.ArrayList;

public class RecyclerFriendsAdapter extends RecyclerView.Adapter<RecyclerFriendsAdapter.ListViewHolder>{

    private ArrayList<DataFriends> list = new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;

    public void setData(ArrayList<DataFriends> items) {
        list.clear();
        list.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerFriendsAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_friends, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerFriendsAdapter.ListViewHolder holder, int position) {
        DataFriends data = list.get(position);
        holder.tvName.setText(data.getName());
        holder.tvStatus.setText(data.getStatus());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(list.get(holder.getAdapterPosition()));

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvStatus;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.name);
            tvStatus = itemView.findViewById(R.id.status);
        }
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }
    public interface OnItemClickCallback {
        void onItemClicked(DataFriends data);
    }

}
