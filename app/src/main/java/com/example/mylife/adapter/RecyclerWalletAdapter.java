package com.example.mylife.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylife.data.DataWallet;
import com.example.mylife.R;

import java.util.ArrayList;

public class RecyclerWalletAdapter extends RecyclerView.Adapter<RecyclerWalletAdapter.ListViewHolder>{

    private ArrayList<DataWallet> list = new ArrayList<>();
//    private OnItemClickCallback onItemClickCallback;

    public void setData(ArrayList<DataWallet> items) {
        list.clear();
        list.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerWalletAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_wallet, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerWalletAdapter.ListViewHolder holder, int position) {
        DataWallet data = list.get(position);
        holder.tvActivity.setText(data.getActivity());
        holder.tvMoney.setText(data.getMoney());
        holder.tvdateDay.setText(data.getDate());
        if (data.getType().equals("incoming")){
            holder.tvplurormin.setText("+ Rp");
        }else{
            holder.tvplurormin.setText("- Rp");
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tvMoney, tvActivity, tvdateDay,tvplurormin;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvActivity = itemView.findViewById(R.id.dataActivity);
            tvMoney = itemView.findViewById(R.id.dataMoney);
            tvdateDay = itemView.findViewById(R.id.date);
            tvplurormin = itemView.findViewById(R.id.textView5);
        }
    }

}
