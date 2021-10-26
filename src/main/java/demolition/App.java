package demolition;

import demolition.Exceptions.MapException;
import demolition.Players.BombGuy;
import demolition.Players.Player;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.io.*;
import java.util.*;

public class App extends PApplet {

    public static final int WIDTH = 480;
    public static final int HEIGHT = 480;

    public static final int FPS = 60;

    ArrayList<? extends Player> player_list;
    private BombGuy bombGuy;
    private Map map;

    public ArrayList<ArrayList<Character>> tileMap;
    public Hashtable<Character, PImage> tileImages = new Hashtable<Character, PImage>();

    public App() {

    }

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void setup() {
        frameRate(FPS);

        JSONObject config = loadJSONObject(new File("config.json"));
        JSONArray level_array = config.getJSONArray("levels");
        int lives = config.getInt("lives");

        // Load images during setup
        this.map = new Map(level_array.getJSONObject(0).getString("path"), this);

        this.tileImages.put('W', loadImage("src/main/resources/wall/solid.png"));
        this.tileImages.put('B', loadImage("src/main/resources/broken/broken.png"));
        this.tileImages.put(' ', loadImage("src/main/resources/empty/empty.png"));
        this.tileImages.put('G', loadImage("src/main/resources/goal/goal.png"));
        this.tileImages.put('P', loadImage("src/main/resources/empty/empty.png"));
        this.tileImages.put('R', loadImage("src/main/resources/empty/empty.png"));
        this.tileImages.put('Y', loadImage("src/main/resources/empty/empty.png"));

        background(239, 129, 0);

        player_list = map.getPlayerList();
        tileMap = map.initMap(this);

        ArrayList<PImage> BombGuySprites = Player.load_in_sprites("src/main/resources/player", this);
        ArrayList<PImage> YellowSprites = Player.load_in_sprites("src/main/resources/yellow_enemy", this);
        ArrayList<PImage> RedSprites = Player.load_in_sprites("src/main/resources/red_enemy", this);

        Player.initPlayers(player_list, this);

    }

    public void draw() {
        // Main loop

        bombGuy = getBombGuy(player_list);
        bombGuy.draw();

    }

    public BombGuy getBombGuy(ArrayList<? extends Player> player_list) {
        for (Player p : player_list) {
            if (p instanceof BombGuy) {
                return (BombGuy) p;
            }
        }

        return null;
    }

    public ArrayList<ArrayList<Character>> getTileMap() {
        return this.tileMap;
    }

    public void keyPressed() {
        System.out.println(String.format("Key %d", keyCode));
        if (key == CODED) {
            bombGuy.handleKey(keyCode);
        }

    }

    public static void main(String[] args) {
        PApplet.main("demolition.App");
    }
}
