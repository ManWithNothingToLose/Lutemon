package com.example.lutemon;

public abstract class Lutemon {
    protected String name;
    protected String color;
    protected int attack;
    protected int defense;
    protected int maxHealth;
    protected int currentHealth;
    protected int experience;
    private int wins = 0;
    private int losses = 0;
    private String location = "Home";
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Lutemon(String name, String color, int attack, int defense, int maxHealth) {
        this.name = name;
        this.color = color;
        this.attack = attack;
        this.defense = defense;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.experience = 0;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getAttack() {
        return attack + experience;
    }

    public int getDefense() {
        return defense;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getExperience() {
        return experience;
    }

    public String getLocation() {
        return location;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    // Setters
    public void setLocation(String location) {
        this.location = location;
    }

    public void addWin() {
        wins++;
    }

    public void addLoss() {
        losses++;
    }

    // Training logic
    public void train() {
        experience++;
        if (experience % 5 == 0) {
            maxHealth += 1;
            attack += 1;
            defense += 1;
            currentHealth = maxHealth; // heal on level up
        }
    }

    public void heal() {
        currentHealth = maxHealth;
    }

    public boolean takeDamage(int damage) {
        currentHealth -= damage;
        return currentHealth <= 0;
    }

    public void gainExperience() {
        experience++;
    }

    public int getAttackPower() {
        return attack + experience;
    }

    @Override
    public String toString() {
        return name + " (" + color + ")";
    }
}
