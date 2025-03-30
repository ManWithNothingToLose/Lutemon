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
        setContentView(R.layout.activity_list_lutemons);

        recyclerView = findViewById(R.id.recyclerViewLutemons);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Lutemon> lutemons = new ArrayList<>(Storage.getInstance().getAllLutemons().values());
        adapter = new LutemonAdapter(lutemons);
        recyclerView.setAdapter(adapter);
    }
}
