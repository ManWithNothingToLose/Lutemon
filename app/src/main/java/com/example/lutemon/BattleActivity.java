package com.example.lutemon;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BattleActivity extends AppCompatActivity {

    private Spinner spinner1, spinner2;
    private Button buttonStart;
    private TextView textViewBattleLog;

    private ArrayList<Lutemon> battleLutemons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        spinner1 = findViewById(R.id.spinnerLutemon1);
        spinner2 = findViewById(R.id.spinnerLutemon2);
        buttonStart = findViewById(R.id.buttonStartBattle);
        textViewBattleLog = findViewById(R.id.textViewBattleLog);

        battleLutemons = Storage.getInstance().getBattleLutemons();

        if (battleLutemons.size() < 2) {
            Toast.makeText(this, "At least 2 Lutemons required in Battle Area.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        ArrayAdapter<Lutemon> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, battleLutemons);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        buttonStart.setOnClickListener(v -> startBattle());
    }

    private void startBattle() {
        Lutemon l1 = (Lutemon) spinner1.getSelectedItem();
        Lutemon l2 = (Lutemon) spinner2.getSelectedItem();

        if (l1 == l2) {
            Toast.makeText(this, "Choose two different Lutemons.", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder log = new StringBuilder();
        log.append("Battle Start!\n\n");

        Lutemon attacker = l1;
        Lutemon defender = l2;

        while (true) {
            log.append(attacker.getName()).append(" attacks ").append(defender.getName()).append("\n");

            int damage = attacker.getAttackPower() - defender.getDefense();
            if (damage < 0) damage = 0;

            boolean isDead = defender.takeDamage(damage);
            log.append(defender.getName()).append(" takes ").append(damage).append(" damage. Remaining HP: ")
                    .append(defender.getCurrentHealth()).append("\n");

            if (isDead) {
                log.append(defender.getName()).append(" has died!\n");
                log.append(attacker.getName()).append(" wins and gains 1 XP!\n");

                attacker.gainExperience();
                Storage.getInstance().moveToHome(attacker); // Heal winner
                Storage.getInstance().removeLutemon(defender); // Remove loser
                break;
            } else {
                log.append(defender.getName()).append(" survives!\n\n");
            }

            // Swap turns
            Lutemon temp = attacker;
            attacker = defender;
            defender = temp;
        }

        textViewBattleLog.setText(log.toString());
    }
}
