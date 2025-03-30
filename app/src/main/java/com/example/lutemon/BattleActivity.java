package com.example.lutemon;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BattleActivity extends AppCompatActivity {

    private ListView listView;
    private Button buttonFight, buttonNextAttack, buttonReset;
    private TextView textResult;
    private ImageView imageLutemon1, imageLutemon2;
    private ProgressBar progressHP1, progressHP2;

    private Lutemon fighter1, fighter2;
    private ArrayList<Lutemon> battleLutemons;
    private ArrayList<String> lutemonNames;
    private ArrayAdapter<String> adapter;

    private int round = 1;
    private boolean battleInProgress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        listView = findViewById(R.id.listBattleLutemons);
        buttonFight = findViewById(R.id.buttonFight);
        buttonNextAttack = findViewById(R.id.buttonNextAttack);
        buttonReset = findViewById(R.id.buttonResetBattle);
        textResult = findViewById(R.id.textResult);
        imageLutemon1 = findViewById(R.id.imageLutemon1);
        imageLutemon2 = findViewById(R.id.imageLutemon2);
        progressHP1 = findViewById(R.id.progressHP1);
        progressHP2 = findViewById(R.id.progressHP2);

        buttonNextAttack.setEnabled(false);
        loadLutemons();

        buttonFight.setOnClickListener(v -> {
            ArrayList<Lutemon> selected = new ArrayList<>();
            for (int i = 0; i < listView.getCount(); i++) {
                if (listView.isItemChecked(i)) {
                    selected.add(battleLutemons.get(i));
                }
            }

            if (selected.size() != 2) {
                Toast.makeText(this, "Select exactly 2 Lutemons to fight!", Toast.LENGTH_SHORT).show();
                return;
            }

            fighter1 = selected.get(0);
            fighter2 = selected.get(1);

            if (fighter1.getCurrentHealth() <= 0 || fighter2.getCurrentHealth() <= 0) {
                Toast.makeText(this, "A defeated Lutemon cannot fight until healed!", Toast.LENGTH_LONG).show();
                return;
            }

            imageLutemon1.setImageResource(getLutemonImage(fighter1));
            imageLutemon2.setImageResource(getLutemonImage(fighter2));

            progressHP1.setMax(fighter1.getMaxHealth());
            progressHP1.setProgress(fighter1.getCurrentHealth());
            progressHP2.setMax(fighter2.getMaxHealth());
            progressHP2.setProgress(fighter2.getCurrentHealth());

            textResult.setText(fighter1.getName() + " VS " + fighter2.getName() + "\n");
            round = 1;
            battleInProgress = true;
            buttonNextAttack.setEnabled(true);
        });

        buttonNextAttack.setOnClickListener(v -> doNextAttack());

        buttonReset.setOnClickListener(v -> {
            for (Lutemon l : new ArrayList<>(Storage.getInstance().getBattleLutemons())) {
                l.heal();
                Storage.getInstance().moveToHome(l, this);
            }

            Toast.makeText(this, "All Lutemons healed and returned to Home.", Toast.LENGTH_SHORT).show();
            textResult.setText("");
            imageLutemon1.setImageDrawable(null);
            imageLutemon2.setImageDrawable(null);
            progressHP1.setProgress(0);
            progressHP2.setProgress(0);
            buttonNextAttack.setEnabled(false);
            listView.clearChoices();
            loadLutemons();
        });
    }

    private void doNextAttack() {
        if (!battleInProgress || fighter1 == null || fighter2 == null) return;

        StringBuilder log = new StringBuilder(textResult.getText());
        log.append("Round ").append(round).append(":\n");

        int damageTo2 = Math.max(0, fighter1.getAttack() - fighter2.getDefense());
        boolean f2Fainted = fighter2.takeDamage(damageTo2);
        log.append(fighter1.getName()).append(" attacks ").append(fighter2.getName())
                .append(" for ").append(damageTo2).append(" dmg.\n");
        animateHpBar(progressHP2, fighter2.getCurrentHealth());

        if (f2Fainted) {
            log.append(fighter2.getName()).append(" is defeated!\n");
            fighter1.gainExperience();
            fighter1.addWin();
            fighter2.addLoss();
            Storage.getInstance().moveToHome(fighter2, this);
            Toast.makeText(this, fighter1.getName() + " wins! 🏆", Toast.LENGTH_LONG).show();
            buttonNextAttack.setEnabled(false);
            battleInProgress = false;
            textResult.setText(log.toString());
            fadeInLogEntry();
            return;
        }

        int damageTo1 = Math.max(0, fighter2.getAttack() - fighter1.getDefense());
        boolean f1Fainted = fighter1.takeDamage(damageTo1);
        log.append(fighter2.getName()).append(" attacks ").append(fighter1.getName())
                .append(" for ").append(damageTo1).append(" dmg.\n");
        animateHpBar(progressHP1, fighter1.getCurrentHealth());

        if (f1Fainted) {
            log.append(fighter1.getName()).append(" is defeated!\n");
            fighter2.gainExperience();
            fighter2.addWin();
            fighter1.addLoss();
            Storage.getInstance().moveToHome(fighter1, this);
            Toast.makeText(this, fighter2.getName() + " wins! 🏆", Toast.LENGTH_LONG).show();
            buttonNextAttack.setEnabled(false);
            battleInProgress = false;
        }

        round++;
        textResult.setText(log.toString());
        fadeInLogEntry();
    }

    private void loadLutemons() {
        battleLutemons = Storage.getInstance().getBattleLutemons();
        lutemonNames = new ArrayList<>();
        for (Lutemon l : battleLutemons) {
            String label = l.getName() + " (" + l.getColor() + ")";
            if (l.getCurrentHealth() <= 0) label += " - 🩹 healing";
            lutemonNames.add(label);
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, lutemonNames);
        listView.setAdapter(adapter);
    }

    private int getLutemonImage(Lutemon l) {
        switch (l.getColor()) {
            case "White": return R.drawable.lutemon_white;
            case "Green": return R.drawable.lutemon_green;
            case "Pink": return R.drawable.lutemon_pink;
            case "Orange": return R.drawable.lutemon_orange;
            case "Black": return R.drawable.lutemon_black;
            default: return R.drawable.dragongrey;
        }
    }

    private void animateHpBar(ProgressBar bar, int newValue) {
        ObjectAnimator animator = ObjectAnimator.ofInt(bar, "progress", bar.getProgress(), newValue);
        animator.setDuration(500);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    private void fadeInLogEntry() {
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(400);
        fadeIn.setFillAfter(true);
        textResult.startAnimation(fadeIn);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadLutemons();
    }
}
