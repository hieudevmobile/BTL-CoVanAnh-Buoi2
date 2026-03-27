package com.example.app_buoi2.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_buoi2.R;
import com.example.app_buoi2.data.entity.Theater;

import java.util.ArrayList;
import java.util.List;

public class TheaterAdapter extends RecyclerView.Adapter<TheaterAdapter.VH> {

    private final List<Theater> items = new ArrayList<>();

    public void submit(List<Theater> data) {
        items.clear();
        if (data != null) {
            items.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_theater, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Theater t = items.get(position);
        holder.name.setText(t.name);
        holder.address.setText(t.address);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        final TextView name;
        final TextView address;

        VH(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textName);
            address = itemView.findViewById(R.id.textAddress);
        }
    }
}
