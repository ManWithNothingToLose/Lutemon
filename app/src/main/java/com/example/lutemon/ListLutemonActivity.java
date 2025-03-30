package com.example.lutemon;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListLutemonActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LutemonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recyclerViewLutemons);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Lutemon> all = new ArrayList<>(Storage.getInstance().getAllLutemons().values());
        adapter = new LutemonAdapter(all, "All", this);

        recyclerView.setAdapter(adapter);

    }
}
