package com.example.app_buoi2.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_buoi2.R;
import com.example.app_buoi2.data.model.ShowtimeRow;
import com.example.app_buoi2.util.DateFormats;

import java.util.ArrayList;
import java.util.List;

public class ShowtimeAdapter extends RecyclerView.Adapter<ShowtimeAdapter.VH> {

    public interface OnPickListener {
        void onPick(ShowtimeRow row);
    }

    private final List<ShowtimeRow> items = new ArrayList<>();
    private final OnPickListener listener;

    public ShowtimeAdapter(OnPickListener listener) {
        this.listener = listener;
    }

    public void submit(List<ShowtimeRow> data) {
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
                        .inflate(R.layout.item_showtime, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ShowtimeRow r = items.get(position);
        holder.movie.setText(r.movieTitle);
        holder.theater.setText(r.theaterName);
        holder.time.setText(DateFormats.formatShow(r.startTimeMillis));
        holder.itemView.setOnClickListener(v -> listener.onPick(r));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        final TextView movie;
        final TextView theater;
        final TextView time;

        VH(@NonNull View itemView) {
            super(itemView);
            movie = itemView.findViewById(R.id.textMovie);
            theater = itemView.findViewById(R.id.textTheater);
            time = itemView.findViewById(R.id.textTime);
        }
    }
}
