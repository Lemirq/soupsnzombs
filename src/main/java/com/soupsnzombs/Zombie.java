package com.soupsnzombs;

public class Zombie extends Entity {
    private int movementSpeed = 10;
    private int health = 100;
    public int moneyDropped = 10;
    public boolean alive = true;

    private void checkAlive() {
        if (health < 0) {
            alive = false;
        } else {
            alive = true;
        }
    }

    public void ZombieMovement() {
        while (alive) {
            x += movementSpeed;
            y += movementSpeed;
        }
    }

    public Zombie() {
        super(0, 0, 20, 20, 100, 10);
    }

}
