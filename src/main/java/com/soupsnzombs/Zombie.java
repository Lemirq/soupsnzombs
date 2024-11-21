package com.soupsnzombs;

public class Zombie extends Entity {
    private int positionX, positionY;
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
            positionX += movementSpeed;
            positionY += movementSpeed;
        }
    }

    public Zombie() {
        super(20, 20, 100, 10);
        this.positionX = 0;
        this.positionY = 0;
    }

}
