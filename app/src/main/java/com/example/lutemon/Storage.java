package com.example.lutemon;
import java.util.ArrayList;
import java.util.HashMap;
public class Storage {
    private static Storage storage = null;
    private HashMap<Integer, Lutemon> allLutemons;
    private ArrayList<Lutemon> home;
    private ArrayList<Lutemon> training;
    private ArrayList<Lutemon> battle;

    private int nextId = 0;

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

    public void addLutemon(Lutemon lutemon) {
        allLutemons.put(nextId, lutemon);
        home.add(lutemon);
        nextId++;
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
    public void moveToTraining(Lutemon lutemon) {
        if (home.remove(lutemon)) {
            training.add(lutemon);
        }
    }
    public void moveToHome(Lutemon lutemon) {
        if (training.remove(lutemon) || home.remove(lutemon)) {
            home.add(lutemon);
            lutemon.heal();
        }
    }
    public void moveToBattle(Lutemon lutemon) {
        if (training.remove(lutemon) || home.remove(lutemon)) {
            battle.add(lutemon);
        }
    }
    public void removeLutemon(Lutemon lutemon) {
        home.remove(lutemon);
        training.remove(lutemon);
        battle.remove(lutemon);
        allLutemons.values().remove(lutemon);;
    }
    public HashMap<Integer,Lutemon> getAllLutemons() {
        return allLutemons;
    }

}
