package com.soupsnzombs.entities.zombies;

import com.soupsnzombs.entities.Entity;

public class Zombie extends Entity {
    private int movementSpeed = 10;
    private int health = 100;
    public int moneyDropped = 10;
    public int pointsDropped = 10;
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

    public void ChasePlayer(Player player) {
        int playerX = player.getBounds().x;
        int playerY = player.getBounds().y;

        if (x < playerX) {
            x += movementSpeed;
        } else if (x > playerX) {
            x -= movementSpeed;
        }

        if (y < playerY) {
            y += movementSpeed;
        } else if (y > playerY) {
            y -= movementSpeed;
        }
    }


}
