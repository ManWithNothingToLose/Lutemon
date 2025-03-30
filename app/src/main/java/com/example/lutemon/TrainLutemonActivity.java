package com.example.lutemon;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TrainLutemonActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LutemonAdapter adapter;
    private Button buttonTrainAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_lutemon);

        recyclerView = findViewById(R.id.recyclerViewTraining);
        buttonTrainAll = findViewById(R.id.buttonTrainAll);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateAdapter();

        buttonTrainAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Lutemon> trainingLutemons = Storage.getInstance().getTrainingLutemons();

                if (trainingLutemons.isEmpty()) {
                    Toast.makeText(TrainLutemonActivity.this, "No Lutemons to train!", Toast.LENGTH_SHORT).show();
                    return; // Exit the onClick method early
                }

                for (Lutemon lutemon : trainingLutemons) {
                    lutemon.train();
                }

                Toast.makeText(TrainLutemonActivity.this, "Lutemons trained!", Toast.LENGTH_SHORT).show();
                updateAdapter();
            }
        });
    }

    private void updateAdapter() {
        ArrayList<Lutemon> trainingLutemons = Storage.getInstance().getTrainingLutemons();
        adapter = new LutemonAdapter(trainingLutemons);
        recyclerView.setAdapter(adapter);
    }
}