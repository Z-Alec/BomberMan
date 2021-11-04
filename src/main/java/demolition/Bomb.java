package demolition;

import java.util.*;
import demolition.App;
import demolition.Players.Player;
import processing.core.PImage;

public class Bomb {

    private int placedTime;
    private int spriteTimer;

    private int x;
    private int y;
    private App app;
    private PImage sprite;
    private int index = 0;
    private boolean exploded = false;

    private ArrayList<String> explosionTiles = new ArrayList<>();

    public Bomb(int x, int y, int time, App app) {
        this.x = x;
        this.y = y;
        this.placedTime = time;
        this.spriteTimer = time;
        this.app = app;
        this.sprite = app.loadImage("src/main/resources/red_enemy/red_down1.png");

    }

    enum bombStatus {
        TICKING, DONE, BOMBGUYCAUGHT
    }

    public bombStatus tick() {

        // If bomb is still ticking
        if (index != 8) {
            // Cycle through the 9 bomb sprites
            if (app.millis() - spriteTimer > 250) {
                index += 1;
                spriteTimer = app.millis();
            }

            this.sprite = app.BombSprites.get(index);
            blankTile();

            return bombStatus.TICKING;
            // Else if the bomb has already exploded
        } else {
            if (!exploded) {
                showExplosion();
                exploded = true;
            }

            return explosionTick();
        }

    }

    public bombStatus explosionTick() {
        if (app.millis() - spriteTimer > 500) {
            cleanup();
            return bombStatus.DONE;
        } else {
            return checkPlayerCaught();
        }
    }

    public void blankTile() {
        app.image(app.tileImages.get(' '), x, y);
    }

    public ArrayList<Integer> coord2Index(int x, int y) {
        int x_ind = x / 32;
        // Sprites heads are 16 pixels above the grid
        int y_ind = (y - 64 + 16) / 32;

        ArrayList<Integer> indices = new ArrayList<Integer>();
        indices.add(x_ind);
        indices.add(y_ind);

        return indices;
    }

    public bombStatus checkPlayerCaught() {

        if (explosionTiles.contains(app.getBombGuy().getCoordsAsString())) {
            app.loseLife();
            return bombStatus.BOMBGUYCAUGHT;
        } else {
            app.playerMap.values().removeIf(coord -> explosionTiles.contains(coord));

            return bombStatus.TICKING;
        }
    }

    public void showExplosion() {
        // Clear the bomb and show centre explosion
        this.sprite = app.ExplosionSprites.get(0);
        // Store tiles that have explosions
        explosionTiles.add(String.format("%d%d", x, y));
        ;

        if (checkMaxDist(x + 32, y, 1)) {
            checkMaxDist(x + 64, y, 1);
        }

        if (checkMaxDist(x - 32, y, 1)) {
            checkMaxDist(x - 64, y, 1);

        }

        if (checkMaxDist(x, y - 32, 2)) {
            checkMaxDist(x, y - 64, 2);

        }

        if (checkMaxDist(x, y + 32, 2)) {
            checkMaxDist(x, y + 64, 2);
        }

    }

    public boolean checkMaxDist(int x_n, int y_n, int horzVert) {
        // Check out of bounds
        if (x_n < 0 || x_n > 480 || y_n > 480 || y_n < 64) {
            return false;
        }

        ArrayList<Integer> indices = coord2Index(x_n, y_n);
        char tile = app.tileMap.get(indices.get(1)).get(indices.get(0));
        // System.out.println(tile);

        switch (tile) {
        case 'W':
            return false;
        case 'B':
            app.tileMap.get(indices.get(1)).set(indices.get(0), ' ');
            app.image(app.ExplosionSprites.get(horzVert), x_n, y_n);
            // explosionTiles.add(String.format("%d%d", x_n, y_n));
            return false;
        default:
            app.image(app.ExplosionSprites.get(horzVert), x_n, y_n);
            explosionTiles.add(String.format("%d%d", x_n, y_n));
            return true;
        }
    }

    public void cleanup() {

        // Restore tiles in all four directions
        restoreTile(x, y);
        // Right
        restoreTile(x + 32, y);
        restoreTile(x + 64, y);
        restoreTile(x + 32, y - 32); // Cover up the enemy heads
        restoreTile(x + 64, y - 32); //
        // Left
        restoreTile(x - 32, y);
        restoreTile(x - 64, y);
        restoreTile(x - 32, y - 32); // Cover up the enemy heads
        restoreTile(x - 64, y - 32); //

        // Up and Down
        restoreTile(x, y - 32);
        restoreTile(x, y - 64);
        restoreTile(x, y + 32);
        restoreTile(x, y + 64);
    }

    public void restoreTile(int x, int y) {
        // Check out of bounds
        if (x < 0 || x >= 480 || y >= 480 || y < 64) {
            return;
        }

        // Get the original tile from tileMap
        ArrayList<Integer> indices = coord2Index(x, y);
        char tile = app.tileMap.get(indices.get(1)).get(indices.get(0));
        app.image(app.tileImages.get(tile), x, y);
    }

    public void draw() {
        app.image(sprite, x, y);
    }

    public static ArrayList<Bomb> bombsTick(ArrayList<Bomb> bombList) {
        if (bombList.isEmpty()) {
            return bombList;
        }

        ArrayList<Bomb> newList = new ArrayList<>();

        // Go through all Bombs
        for (Bomb b : bombList) {
            // tick
            bombStatus res = b.tick();

            // If true, the bomb is still active/exploding
            if (res == bombStatus.TICKING) {
                newList.add(b);
            }

            if (res == bombStatus.BOMBGUYCAUGHT) {
                return newList;
            }
        }

        return newList;

    }

    public static void bombsDraw(ArrayList<Bomb> bombList) {
        for (Bomb b : bombList) {
            b.draw();
        }
    }
}
