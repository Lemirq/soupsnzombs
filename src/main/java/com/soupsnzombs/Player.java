package com.soupsnzombs;

public class Player {
    int money = 0;
    int health = 100;
    boolean isDead = false;
    int healthAmount;
    int moneyAmount;

    // Method to decrease health
    public void decreaseHealth(int healthAmount) {
        this.health -= healthAmount;
    }


    // Method to increase health
    public void increaseHealth(int healthAmount) {
        this.health += healthAmount;
    }


    // Method to add cash
    public void addMoney(int moneyAmount) {
        money += moneyAmount;
    }


    // Method to remove cash
    public void removeMoney(int moneyAmount) {
        money -= moneyAmount;
    }
}
