package demolition.Players;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

import demolition.App;

import processing.core.*;
import processing.core.PApplet;

public class Player {

    protected int x;
    protected int y;
    protected App app;

    protected PImage sprite;
    protected static ArrayList<PImage> BombGuySprites = new ArrayList<PImage>();
    protected static ArrayList<PImage> RedEnemySprites = new ArrayList<PImage>();
    protected static ArrayList<PImage> YellowEnemySprites = new ArrayList<PImage>();

    public Player() {

        // this.player = app.loadImage("src/main/resources/player/player1.png");
        // this.player = app.loadImage("src/main/resources/player/player2.png");
        // this.player = app.loadImage("src/main/resources/player/player3.png");
        // this.player = app.loadImage("src/main/resources/player/player4.png");

        // this.player = app.loadImage("src/main/resources/player/player_up1.png");
        // this.player = app.loadImage("src/main/resources/player/player_up2.png");
        // this.player = app.loadImage("src/main/resources/player/player_up3.png");
        // this.player = app.loadImage("src/main/resources/player/player_up4.png");

        // this.player = app.loadImage("src/main/resources/player/player1.png");

        // this.red = app.loadImage("src/main/resources/red_enemy/red_down1.png");
        // this.yellow = app.loadImage("src/main/resources/red_enemy/red_down1.png");
        // app.loadImage("src/main/resources/yellow_enemy/yellow_down1.png");

    }

    public static ArrayList<PImage> load_in_sprites(String folder, PApplet app) {

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

    public static void initPlayers(ArrayList<? extends Player> player_list, PApplet app) {
        for (Player p : player_list) {
            p.draw();
        }

    }

    public void draw() {
        // handles graphics, no logic
        app.image(sprite, x, y);

    }

}
