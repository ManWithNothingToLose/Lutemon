package com.example.lutemon;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerViewHome;
    private LutemonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Enable back button on top bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Lutemons at Home");
        }

        recyclerViewHome = findViewById(R.id.recyclerViewHome);
        recyclerViewHome.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<Lutemon> homeLutemons = Storage.getInstance().getLutemonsByLocation("Home");

        adapter = new LutemonAdapter(homeLutemons, "Home", this);
        recyclerViewHome.setAdapter(adapter);
    }

    // Handle back button in toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and go back
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
