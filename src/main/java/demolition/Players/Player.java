package demolition.Players;

import java.io.*;
import java.util.*;

import demolition.App;

import processing.core.*;
import processing.core.PApplet;

interface Move {
    int[] move();
}

public class Player {

    protected int x;
    protected int y;
    protected int direction = PConstants.DOWN;
    protected App app;

    protected PImage sprite;
    protected static ArrayList<PImage> BombGuySprites = new ArrayList<PImage>();
    protected static ArrayList<PImage> RedEnemySprites = new ArrayList<PImage>();
    protected static ArrayList<PImage> YellowEnemySprites = new ArrayList<PImage>();

    Hashtable<Integer, Move> moveMap = new Hashtable<>();

    public Player(int x, int y) {
        this.x = x;
        this.y = y;

        moveMap.put(PConstants.LEFT, () -> new int[] { this.x - 32, this.y });
        moveMap.put(PConstants.RIGHT, () -> new int[] { this.x + 32, this.y });
        moveMap.put(PConstants.DOWN, () -> new int[] { this.x, this.y + 32 });
        moveMap.put(PConstants.UP, () -> new int[] { this.x, this.y - 32 });

    }

    public static ArrayList<PImage> loadSprites(String folder, PApplet app) {

        // Get all files in the specified folder
        File f = new File(folder);
        File[] files = f.listFiles();

        ArrayList<String> pics = new ArrayList<String>();
        ArrayList<PImage> sprites = new ArrayList<PImage>();

        // Extract all png files
        for (File file : files) {
            String path = file.getPath();
            if (path.substring(path.length() - 3, path.length()).equals("png")) { // this line weeds out other
                                                                                  // directories/folders
                pics.add(path);
            }
        }

        // Sort alphabetically for consistency and return
        Collections.sort(pics);
        for (String p : pics) {
            sprites.add(app.loadImage(p));
        }

        return sprites;

    }

    public void tick() {
        // Handles logic

    }

    public static void initPlayers(Set<Enemy> player_list, PApplet app) {
        for (Enemy p : player_list) {
            p.draw();
        }

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

    public void blankTile() {
        app.image(app.tileImages.get(' '), x, y + 16);
        ArrayList<Integer> indices = coord2Index(x, y - 32 + 16);
        char prevTopTile = app.tileMap.get(indices.get(1)).get(indices.get(0));

        app.image(app.tileImages.get(prevTopTile), x, y - 32 + 16);
    }

    public String getCoordsAsString() {
        return String.format("%d%d", x, y + 16);
    }

    public void draw() {
        // handles graphics, no logic
        app.image(this.sprite, x, y);

    }

    // public void move() {
    // System.out.println("THIS SHOULDNT BE PRINTING");
    // }

    public static void playersTick(Set<Enemy> player_list, App app) {
        for (Player p : player_list) {
            p.tick();
        }
    }

    public static void enemiesMove(Set<Enemy> player_list) {
        for (Enemy e : player_list) {
            if (e instanceof RedEnemy) {
                RedEnemy r = (RedEnemy) e;
                r.move();
            } else {
                YellowEnemy y = (YellowEnemy) e;
                y.move();
            }
        }
    }

    public static void playersDraw(Set<Enemy> player_list) {
        for (Player p : player_list) {
            p.draw();
        }

    }

}
