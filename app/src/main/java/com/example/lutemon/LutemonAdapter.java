package com.example.lutemon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LutemonAdapter extends RecyclerView.Adapter<LutemonAdapter.LutemonViewHolder> {

    private List<Lutemon> lutemonList;

    public LutemonAdapter(List<Lutemon> lutemonList) {
        this.lutemonList = lutemonList;
    }

    @NonNull
    @Override
    public LutemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lutemon, parent, false);
        return new LutemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LutemonViewHolder holder, int position) {
        Lutemon lutemon = lutemonList.get(position);
        holder.bind(lutemon);
    }

    @Override
    public int getItemCount() {
        return lutemonList.size();
    }

    // Moved the ViewHolder inside the Adapter class
    public static class LutemonViewHolder extends RecyclerView.ViewHolder {
        public TextView text1;
        public TextView text2;
        public TextView text3;

        public LutemonViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.text1);
            text2 = itemView.findViewById(R.id.text2);
            text3 = itemView.findViewById(R.id.text3);
        }

        public void bind(Lutemon lutemon) {
            if (lutemon != null) {
                text1.setText(lutemon.getName() + " ( " + lutemon.getColor() + " )");
                text2.setText("Attack: " + lutemon.getAttack());
                text3.setText("Defense: " + lutemon.getDefense());
            }
        }
    }
}