package demolition;

import demolition.Exceptions.MapException;
import demolition.Players.BombGuy;
import demolition.Players.Player;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.io.*;

public class App extends PApplet {

    public static final int WIDTH = 480;
    public static final int HEIGHT = 480;

    public static final int FPS = 60;

    private Player player;
    private Map map;

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

    }

    public void draw() {
        // Main loop
        background(239, 129, 0);

        try {
            map.parseMapTxt();
        } catch (MapException e) {
            System.out.println(e);
        }

        // this.player.tick();
        this.map.tick();

        this.map.draw(this);
        // this.player.draw(this);

    }

    public static void main(String[] args) {
        PApplet.main("demolition.App");
    }
}
