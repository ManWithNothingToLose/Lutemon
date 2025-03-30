package com.example.lutemon;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LutemonAdapter extends RecyclerView.Adapter<LutemonAdapter.LutemonViewHolder> {

    private List<Lutemon> lutemonList;
    private String currentLocation;
    private Context context;

    public LutemonAdapter(List<Lutemon> lutemonList, String currentLocation, Context context) {
        this.lutemonList = lutemonList;
        this.currentLocation = currentLocation;
        this.context = context;
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

        holder.imageOptions.setOnClickListener(v -> showMoveDialog(context, lutemon, position));
    }

    @Override
    public int getItemCount() {
        return lutemonList.size();
    }

    private void showMoveDialog(Context context, Lutemon lutemon, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Move " + lutemon.getName() + " to:");

        List<String> options = new ArrayList<>();
        if (!currentLocation.equals("Home")) options.add("Home");
        if (!currentLocation.equals("Training")) options.add("Training");
        if (!currentLocation.equals("Battle") && lutemon.getCurrentHealth() == lutemon.getMaxHealth())
            options.add("Battle"); // Only fully healed can join
        options.add("Cancel");

        String[] items = options.toArray(new String[0]);

        builder.setItems(items, (dialog, which) -> {
            String selected = items[which];
            if (selected.equals("Cancel")) return;

            // ✅ First: move in Storage
            switch (selected) {
                case "Home":
                    Storage.getInstance().moveToHome(lutemon, context);
                    break;
                case "Training":
                    Storage.getInstance().moveToTraining(lutemon, context);
                    break;
                case "Battle":
                    Storage.getInstance().moveToBattle(lutemon, context);
                    break;
            }

            // ✅ Then: update local list to reflect new Storage state
            lutemonList.remove(lutemon);
            notifyDataSetChanged(); // safest call after state change

            Toast.makeText(context, lutemon.getName() + " moved to " + selected, Toast.LENGTH_SHORT).show();
        });

        builder.show();
    }


    public static class LutemonViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageLutemon;
        public TextView textName, textStats, text1, text2, text3, textLevel;
        public ImageView imageOptions;
        public ProgressBar progressXp;

        public LutemonViewHolder(@NonNull View itemView) {
            super(itemView);
            imageLutemon = itemView.findViewById(R.id.imageLutemon);
            textName = itemView.findViewById(R.id.textName);
            textStats = itemView.findViewById(R.id.textStats);
            text1 = itemView.findViewById(R.id.text1);
            text2 = itemView.findViewById(R.id.text2);
            text3 = itemView.findViewById(R.id.text3);
            textLevel = itemView.findViewById(R.id.textLevel);
            imageOptions = itemView.findViewById(R.id.imageOptions);
            progressXp = itemView.findViewById(R.id.progressXp);
        }

        public void bind(Lutemon lutemon) {
            textName.setText(lutemon.getName());
            textStats.setText("Color: " + lutemon.getColor() + "\nXP: " + lutemon.getExperience());
            text1.setText("Attack: " + lutemon.getAttack());
            text2.setText("Defense: " + lutemon.getDefense());
            text3.setText("HP: " + lutemon.getCurrentHealth() + "/" + lutemon.getMaxHealth());

            int level = (lutemon.getExperience() / 5) + 1;
            textLevel.setText("Level: " + level);

            progressXp.setMax(5);
            progressXp.setProgress(lutemon.getExperience() % 5);

            switch (lutemon.getColor()) {
                case "White":
                    imageLutemon.setImageResource(R.drawable.lutemon_white);
                    break;
                case "Green":
                    imageLutemon.setImageResource(R.drawable.lutemon_green);
                    break;
                case "Pink":
                    imageLutemon.setImageResource(R.drawable.lutemon_pink);
                    break;
                case "Orange":
                    imageLutemon.setImageResource(R.drawable.lutemon_orange);
                    break;
                case "Black":
                    imageLutemon.setImageResource(R.drawable.lutemon_black);
                    break;
                default:
                    imageLutemon.setImageResource(R.drawable.dragongrey);
                    break;
            }
        }
    }
}
