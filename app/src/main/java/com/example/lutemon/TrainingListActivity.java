package com.example.lutemon;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TrainingListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTraining;
    private LutemonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_list);

        // Enable top ActionBar back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Training Area");
        }

        recyclerViewTraining = findViewById(R.id.recyclerViewTraining);
        recyclerViewTraining.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<Lutemon> trainingLutemons = Storage.getInstance().getLutemonsByLocation("Training");

        adapter = new LutemonAdapter(trainingLutemons, "Training", this);
        recyclerViewTraining.setAdapter(adapter);
    }

    // Handle ActionBar back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
