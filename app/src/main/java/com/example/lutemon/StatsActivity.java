package com.example.lutemon;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewStats;
    private LutemonStatsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        recyclerViewStats = findViewById(R.id.recyclerViewStats);
        recyclerViewStats.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Lutemon> all = new ArrayList<>(Storage.getInstance().getAllLutemons().values());

        adapter = new LutemonStatsAdapter(this, all);
        recyclerViewStats.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
