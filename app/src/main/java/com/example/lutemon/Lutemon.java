package com.example.lutemon;

public abstract class Lutemon {
    protected String name;
    protected String color;
    protected int attack;
    protected int defense;
    protected int maxHealth;
    protected int currentHealth;
    protected int experience;

    public Lutemon(String name, String color, int attack, int defense, int maxHealth) {
        this.name = name;  //getters
        this.color = color;
        this.attack = attack;
        this.defense = defense;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.experience = 0;
    }
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

    public void train() {
        experience ++;
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
}
