package com.example.app_buoi2.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_buoi2.R;
import com.example.app_buoi2.data.model.TicketRow;
import com.example.app_buoi2.util.DateFormats;

import java.util.ArrayList;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.VH> {

    private final List<TicketRow> items = new ArrayList<>();

    public void submit(List<TicketRow> data) {
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
                        .inflate(R.layout.item_ticket, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        TicketRow r = items.get(position);
        holder.seat.setText(holder.itemView.getContext().getString(R.string.ticket_seat_format, r.seat));
        holder.detail.setText(
                holder.itemView
                        .getContext()
                        .getString(
                                R.string.ticket_detail_format,
                                r.movieTitle,
                                r.theaterName,
                                DateFormats.formatShow(r.startTimeMillis)));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        final TextView seat;
        final TextView detail;

        VH(@NonNull View itemView) {
            super(itemView);
            seat = itemView.findViewById(R.id.textSeat);
            detail = itemView.findViewById(R.id.textDetail);
        }
    }
}
