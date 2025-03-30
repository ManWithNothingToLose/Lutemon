package com.example.lutemon;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonCreate = findViewById(R.id.buttonCreate);
        Button buttonList = findViewById(R.id.buttonList);
        Button buttonMove = findViewById(R.id.buttonMove);
        Button buttonTrain = findViewById(R.id.buttonTrain);
        Button buttonBattle = findViewById(R.id.buttonBattle);

        buttonCreate.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateLutemonActivity.class);
            startActivity(intent);
        });

        buttonList.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListLutemonActivity.class);
            startActivity(intent);
        });

        buttonMove.setOnClickListener(v -> {
            Intent intent = new Intent(this, MoveLutemonActivity.class);
            startActivity(intent);
        });

        buttonTrain.setOnClickListener(v -> {
            Intent intent = new Intent(this, TrainLutemonActivity.class);
            startActivity(intent);
        });

        buttonBattle.setOnClickListener(v -> {
            Intent intent = new Intent(this, BattleActivity.class);
            startActivity(intent);
        });

    }
}