package com.example.app_buoi2.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_buoi2.R;
import com.example.app_buoi2.data.entity.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.VH> {

    private final List<Movie> items = new ArrayList<>();

    public void submit(List<Movie> data) {
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
                        .inflate(R.layout.item_movie, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Movie m = items.get(position);
        holder.title.setText(m.title);
        holder.meta.setText(
                holder.itemView
                        .getContext()
                        .getString(R.string.movie_meta_format, m.durationMinutes, m.synopsis));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        final TextView title;
        final TextView meta;

        VH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textTitle);
            meta = itemView.findViewById(R.id.textMeta);
        }
    }
}
