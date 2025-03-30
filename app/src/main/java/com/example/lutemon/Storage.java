package com.example.lutemon;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class Storage {

    private static Storage storage = null;

    private HashMap<Integer, Lutemon> allLutemons;
    private ArrayList<Lutemon> home;
    private ArrayList<Lutemon> training;
    private ArrayList<Lutemon> battle;

    private int nextId = 0;

    private static final String FILE_NAME = "lutemon_data.json";

    private Storage() {
        allLutemons = new HashMap<>();
        home = new ArrayList<>();
        training = new ArrayList<>();
        battle = new ArrayList<>();
    }

    public static Storage getInstance() {
        if (storage == null) {
            storage = new Storage();
        }
        return storage;
    }

    public void addLutemon(Lutemon lutemon, Context context) {
        lutemon.setId(nextId);
        allLutemons.put(nextId, lutemon);
        lutemon.setLocation("Home");
        home.add(lutemon);
        nextId++;
        saveData(context);
    }

    public ArrayList<Lutemon> getHomeLutemons() {
        return home;
    }

    public ArrayList<Lutemon> getTrainingLutemons() {
        return training;
    }

    public ArrayList<Lutemon> getBattleLutemons() {
        return battle;
    }

    public HashMap<Integer, Lutemon> getAllLutemons() {
        return allLutemons;
    }

    public ArrayList<Lutemon> getLutemonsByLocation(String location) {
        switch (location) {
            case "Home": return home;
            case "Training": return training;
            case "Battle": return battle;
            default: return new ArrayList<>();
        }
    }

    public void moveToHome(Lutemon l, Context context) {
        training.remove(l);
        battle.remove(l);
        if (!home.contains(l)) home.add(l);
        l.setLocation("Home");
        if (l.getCurrentHealth() <= 0) {
            l.heal();
        }
        saveData(context);
    }

    public void moveToTraining(Lutemon l, Context context) {
        home.remove(l);
        battle.remove(l);
        if (!training.contains(l)) training.add(l);
        l.setLocation("Training");
        saveData(context);
    }

    public void moveToBattle(Lutemon l, Context context) {
        if (l.getCurrentHealth() < l.getMaxHealth()) return; // Only full HP allowed
        home.remove(l);
        training.remove(l);
        if (!battle.contains(l)) battle.add(l);
        l.setLocation("Battle");
        saveData(context);
    }

    public void removeLutemon(Lutemon l, Context context) {
        home.remove(l);
        training.remove(l);
        battle.remove(l);
        allLutemons.values().remove(l);
        saveData(context);
    }

    public void saveData(Context context) {
        try {
            Gson gson = new Gson();
            File file = new File(context.getFilesDir(), FILE_NAME);
            FileWriter writer = new FileWriter(file);
            gson.toJson(this, writer);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadData(Context context) {
        try {
            File file = new File(context.getFilesDir(), FILE_NAME);
            if (!file.exists()) return;

            FileReader reader = new FileReader(file);
            Gson gson = new Gson();
            Type storageType = new TypeToken<Storage>() {}.getType();
            Storage loaded = gson.fromJson(reader, storageType);
            reader.close();

            if (loaded != null) {
                this.allLutemons = loaded.allLutemons;
                this.home = loaded.home;
                this.training = loaded.training;
                this.battle = loaded.battle;
                this.nextId = loaded.nextId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
