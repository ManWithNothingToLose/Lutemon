package com.example.lutemon;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    private TextView textHomeCount, textTrainingCount, textBattleCount;
    private CardView cardCreate, cardHome, cardTraining, cardBattle, cardStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load saved data
        Storage.getInstance().loadData(this);

        // Link TextViews
        textHomeCount = findViewById(R.id.textHomeCount);
        textTrainingCount = findViewById(R.id.textTrainingCount);
        textBattleCount = findViewById(R.id.textBattleCount);

        // Link CardViews
        cardCreate = findViewById(R.id.cardCreate);
        cardHome = findViewById(R.id.cardHome);
        cardTraining = findViewById(R.id.cardTraining);
        cardBattle = findViewById(R.id.cardBattle);
        cardStats = findViewById(R.id.cardStats);

        // Card click listeners
        cardCreate.setOnClickListener(v -> startActivity(new Intent(this, CreateLutemonActivity.class)));
        cardHome.setOnClickListener(v -> startActivity(new Intent(this, HomeActivity.class)));
        cardTraining.setOnClickListener(v -> startActivity(new Intent(this, TrainingListActivity.class)));
        cardBattle.setOnClickListener(v -> startActivity(new Intent(this, BattleActivity.class)));
        cardStats.setOnClickListener(v -> startActivity(new Intent(this, StatsActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCounts();
    }

    private void updateCounts() {
        textHomeCount.setText(String.valueOf(Storage.getInstance().getHomeLutemons().size()));
        textTrainingCount.setText(String.valueOf(Storage.getInstance().getTrainingLutemons().size()));
        textBattleCount.setText(String.valueOf(Storage.getInstance().getBattleLutemons().size()));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Storage.getInstance().saveData(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Storage.getInstance().saveData(this);
    }
}
