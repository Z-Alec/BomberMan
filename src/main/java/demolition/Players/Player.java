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

    public static void initPlayers(Set<Enemy> playerList, PApplet app) {
        for (Enemy p : playerList) {
            p.draw();
        }

    }

    public int[] coord2Index(int x, int y) {

        int x_ind = x / 32;
        // Sprites heads are 16 pixels above the grid
        int y_ind = (y - 64 + 16) / 32;

        int[] indices = new int[2];
        indices[0] = (x_ind);
        indices[1] = (y_ind);

        return indices;
    }

    public void blankTile() {
        app.image(app.tileImages.get(' '), x, y + 16);
        int[] indices = coord2Index(x, y - 32 + 16);
        char prevTopTile = app.tileMap.get(indices[1]).get(indices[0]);

        app.image(app.tileImages.get(prevTopTile), x, y - 32 + 16);
    }

    public String getCoordsAsString() {
        return String.format("%d%d", x, y + 16);
    }

    public void draw() {
        // handles graphics, no logic
        app.image(this.sprite, x, y);

    }

    public void tick() {

    }

    public static void playersTick(Set<Enemy> playerList, App app) {
        for (Player p : playerList) {
            p.tick();
        }
    }

    public static void enemiesMove(Set<Enemy> playerList) {
        for (Enemy e : playerList) {
            if (e instanceof RedEnemy) {
                RedEnemy r = (RedEnemy) e;
                r.move();
            } else {
                YellowEnemy y = (YellowEnemy) e;
                y.move();
            }
        }
    }

    public static void playersDraw(Set<Enemy> playerList) {
        for (Player p : playerList) {
            p.draw();
        }

    }

}
