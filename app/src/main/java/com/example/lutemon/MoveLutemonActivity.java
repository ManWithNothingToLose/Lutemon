package com.example.lutemon;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MoveLutemonActivity extends AppCompatActivity {

    private Spinner spinnerSource, spinnerTarget;
    private ListView listView;
    private Button buttonMove;

    private ArrayAdapter<Lutemon> listAdapter;
    private ArrayList<Lutemon> currentList = new ArrayList<>();
    private String[] areas = {"Home", "Training", "Battle"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_lutemon);
        

        spinnerSource = findViewById(R.id.spinnerSource);
        spinnerTarget = findViewById(R.id.spinnerTarget);
        listView = findViewById(R.id.listViewLutemons);
        buttonMove = findViewById(R.id.buttonMove);

        ArrayAdapter<String> areaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, areas);
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSource.setAdapter(areaAdapter);
        spinnerTarget.setAdapter(areaAdapter);

        spinnerSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                updateListView(getLutemonsFrom(spinnerSource.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        buttonMove.setOnClickListener(v -> {
            int selectedIndex = listView.getCheckedItemPosition();

            if (selectedIndex == AdapterView.INVALID_POSITION) {
                Toast.makeText(this, "No Lutemon selected!", Toast.LENGTH_SHORT).show();
                return;
            }

            Lutemon selected = currentList.get(selectedIndex);
            String source = spinnerSource.getSelectedItem().toString();
            String target = spinnerTarget.getSelectedItem().toString();

            if (source.equals(target)) {
                Toast.makeText(this, "Source and target cannot be the same.", Toast.LENGTH_SHORT).show();
                return;
            }

            moveLutemon(selected, target,this);
            Toast.makeText(this, selected.getName() + " moved to " + target, Toast.LENGTH_SHORT).show();
            updateListView(getLutemonsFrom(source));
        });
    }

    private ArrayList<Lutemon> getLutemonsFrom(String area) {
        switch (area) {
            case "Home": return Storage.getInstance().getHomeLutemons();
            case "Training": return Storage.getInstance().getTrainingLutemons();
            case "Battle": return Storage.getInstance().getBattleLutemons();
            default: return new ArrayList<>();
        }
    }

    private void moveLutemon(Lutemon l, String targetArea, Context context) {
        switch (targetArea) {
            case "Home":
                Storage.getInstance().moveToHome(l, context);
                break;
            case "Training":
                Storage.getInstance().moveToTraining(l, context);
                break;
            case "Battle":
                Storage.getInstance().moveToBattle(l, context);
                break;
        }
    }


    private void updateListView(ArrayList<Lutemon> list) {
        currentList = new ArrayList<>(list);
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, currentList);
        listView.setAdapter(listAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.clearChoices();
        listAdapter.notifyDataSetChanged();
    }


}
