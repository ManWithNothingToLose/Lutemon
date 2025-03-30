package com.example.lutemon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LutemonStatsAdapter extends RecyclerView.Adapter<LutemonStatsAdapter.StatsViewHolder> {

    private Context context;
    private List<Lutemon> lutemonList;

    public LutemonStatsAdapter(Context context, List<Lutemon> lutemonList) {
        this.context = context;
        this.lutemonList = lutemonList;
    }

    @NonNull
    @Override
    public StatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_stats_lutemon, parent, false);
        return new StatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatsViewHolder holder, int position) {
        Lutemon l = lutemonList.get(position);

        holder.textName.setText(l.getName() + " (" + l.getColor() + ")");
        holder.textStats.setText("XP: " + l.getExperience() + " | Level: " + ((l.getExperience() / 5) + 1));
        holder.progressXp.setProgress(l.getExperience() % 5);
        holder.textDetails.setText("Wins: " + l.getWins() + " | Losses: " + l.getLosses() + " | Location: " + l.getLocation());

        switch (l.getColor()) {
            case "White":
                holder.image.setImageResource(R.drawable.lutemon_white);
                break;
            case "Green":
                holder.image.setImageResource(R.drawable.lutemon_green);
                break;
            case "Pink":
                holder.image.setImageResource(R.drawable.lutemon_pink);
                break;
            case "Orange":
                holder.image.setImageResource(R.drawable.lutemon_orange);
                break;
            case "Black":
                holder.image.setImageResource(R.drawable.lutemon_black);
                break;
            default:
                holder.image.setImageResource(R.drawable.dragongrey);
        }
    }

    @Override
    public int getItemCount() {
        return lutemonList.size();
    }

    public static class StatsViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView textName, textStats, textDetails;
        ProgressBar progressXp;

        public StatsViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageLutemonStat);
            textName = itemView.findViewById(R.id.textNameStat);
            textStats = itemView.findViewById(R.id.textStatsStat);
            textDetails = itemView.findViewById(R.id.textDetailsStat);
            progressXp = itemView.findViewById(R.id.progressXpStat);
        }
    }
}
