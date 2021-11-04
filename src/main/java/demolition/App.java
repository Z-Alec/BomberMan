package demolition;

import demolition.Exceptions.MapException;
import demolition.Players.*;
import processing.core.*;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.io.*;
import java.util.*;

public class App extends PApplet {

    public static final int WIDTH = 480;
    public static final int HEIGHT = 480;

    public static final int FPS = 60;

    // Player list and Enemy positions
    public ArrayList<? extends Player> player_list;
    public Hashtable<Enemy, String> playerMap;
    public ArrayList<Bomb> bombList = new ArrayList<>();

    // Main Objects
    private BombGuy bombGuy;
    private Map map;

    public ArrayList<ArrayList<Character>> tileMap;
    public Hashtable<Character, PImage> tileImages = new Hashtable<Character, PImage>();

    // Sprite Storage
    public ArrayList<PImage> BombGuySprites;
    public ArrayList<PImage> YellowSprites;
    public ArrayList<PImage> RedSprites;
    public ArrayList<PImage> BombSprites;
    public ArrayList<PImage> ExplosionSprites = new ArrayList<>();

    // Player Lives and Timing
    public int currentLevel = 0;
    public int totalLevels;
    public int totalLives;
    public int livesRemaining;
    public int levelTime;
    public int time;
    public static int playerTimer = 0;
    public static int gameTimer = 0;
    public static int secondTimer = 0;
    public PFont font;

    // Player Direction Mappings
    public Hashtable<Integer, Integer> directionMap = new Hashtable<Integer, Integer>();

    public App() {

    }

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void loadNextLevel() {
        currentLevel += 1;

        if (currentLevel == totalLevels) {
            youWin();
            return;
        }

        // Load Map info from config.json
        JSONObject config = loadJSONObject(new File("config.json"));
        JSONArray level_array = config.getJSONArray("levels");
        totalLevels = level_array.size();
        levelTime = level_array.getJSONObject(currentLevel).getInt("time");
        time = levelTime;

        this.map = new Map(level_array.getJSONObject(currentLevel).getString("path"), this);

        background(239, 129, 0);
        this.image(loadImage("src/main/resources/icons/player.png"), 128, 16);
        this.font = createFont("src/main/resources/PressStart2P-Regular.ttf", 18);
        this.textFont(font);
        fill(0);
        this.text(livesRemaining, 170, 42);

        this.image(loadImage("src/main/resources/icons/clock.png"), 288, 16);
        this.text(time, 328, 42);

        // Load Map and Players in
        // player_list = map.getPlayerList();
        playerMap = map.getPlayerMap();
        tileMap = map.initMap(this);

        // Player.initPlayers(player_list, this);
        Player.initPlayers(playerMap.keySet(), this);

        // bombGuy = getBombGuy(player_list);
        bombGuy = map.getBombGuy();
        bombGuy.draw();
    }

    public void setup() {
        frameRate(FPS);

        // Load Map info from config.json
        JSONObject config = loadJSONObject(new File("config.json"));
        JSONArray level_array = config.getJSONArray("levels");
        totalLives = config.getInt("lives");
        livesRemaining = totalLives;
        levelTime = level_array.getJSONObject(0).getInt("time");
        time = levelTime;

        // Load images during setup
        this.map = new Map(level_array.getJSONObject(0).getString("path"), this);

        this.tileImages.put('W', loadImage("src/main/resources/wall/solid.png"));
        this.tileImages.put('B', loadImage("src/main/resources/broken/broken.png"));
        this.tileImages.put(' ', loadImage("src/main/resources/empty/empty.png"));
        this.tileImages.put('G', loadImage("src/main/resources/goal/goal.png"));
        this.tileImages.put('P', loadImage("src/main/resources/empty/empty.png"));
        this.tileImages.put('R', loadImage("src/main/resources/empty/empty.png"));
        this.tileImages.put('Y', loadImage("src/main/resources/empty/empty.png"));
        // this.tileImages.put('P', loadImage("src/main/resources/empty/empty.png"));
        // this.tileImages.put('R', loadImage("src/main/resources/empty/empty.png"));
        // this.tileImages.put('Y', loadImage("src/main/resources/empty/empty.png"));

        // Set Background and UI
        background(239, 129, 0);
        this.image(loadImage("src/main/resources/icons/player.png"), 128, 16);
        this.font = createFont("src/main/resources/PressStart2P-Regular.ttf", 18);
        this.textFont(font);
        fill(0);
        this.text(totalLives, 170, 42);

        this.image(loadImage("src/main/resources/icons/clock.png"), 288, 16);
        this.text(time, 328, 42);

        // Load Map and Players in
        // player_list = map.getPlayerList();
        playerMap = map.getPlayerMap();
        tileMap = map.initMap(this);

        BombGuySprites = Player.loadSprites("src/main/resources/player", this);
        YellowSprites = Player.loadSprites("src/main/resources/yellow_enemy", this);
        RedSprites = Player.loadSprites("src/main/resources/red_enemy", this);
        BombSprites = Player.loadSprites("src/main/resources/bomb", this);
        ExplosionSprites.add(loadImage("src/main/resources/explosion/centre.png"));
        ExplosionSprites.add(loadImage("src/main/resources/explosion/horizontal.png"));
        ExplosionSprites.add(loadImage("src/main/resources/explosion/vertical.png"));

        directionMap.put(PConstants.DOWN, 0);
        directionMap.put(PConstants.LEFT, 4);
        directionMap.put(PConstants.RIGHT, 8);
        directionMap.put(PConstants.UP, 12);

        // Player.initPlayers(player_list, this);
        Player.initPlayers(playerMap.keySet(), this);

        // bombGuy = getBombGuy(player_list);
        bombGuy = map.getBombGuy();
        bombGuy.draw();

    }

    public void resetMap() {
        // Load Map and Players in
        // player_list.clear();
        playerMap.clear();
        bombList.clear();
        tileMap = map.initMap(this);

        Player.initPlayers(playerMap.keySet(), this);
        bombGuy = map.getBombGuy();

        time = levelTime;
    }

    public void tick() {
        if (millis() - App.gameTimer > 1000) {
            // Clear previous time
            fill(239, 129, 0);
            stroke(239, 129, 0);
            rect(320, 0, 96, 64);

            // Decrease time and update
            this.time--;
            App.gameTimer = millis();
            fill(0);
            stroke(0);

        }

        if (time == 0 || livesRemaining == 0) {
            gameOver();
        }

        if (bombGuy.getCoordsAsString().equals(map.goal)) {
            loadNextLevel();
        }
    }

    public void checkCollision() {
        if (playerMap.containsValue(bombGuy.getCoordsAsString())) {
            loseLife();
        }
    }

    public void loseLife() {
        resetMap();
        livesRemaining--;
        // Clear previous lives
        fill(239, 129, 0);
        stroke(239, 129, 0);
        rect(160, 0, 64, 63);

        // Update lives
        fill(0);
        stroke(0);
        text(livesRemaining, 170, 42);

    }

    public void draw() {
        // Main loop

        this.text(time, 328, 42);

        // Animation Ticking
        if (millis() - App.playerTimer > 200) {
            Player.playersTick(playerMap.keySet(), this);
            bombGuy.tick();
            App.playerTimer = millis();
        }

        // Enemy Movement Ticking
        if (millis() - App.secondTimer > 1000) {
            Player.enemiesMove(playerMap.keySet());
            App.secondTimer = millis();
        }

        bombList = Bomb.bombsTick(bombList);
        Bomb.bombsDraw(bombList);

        checkCollision();

        // Draw all characters
        Player.playersDraw(playerMap.keySet());
        bombGuy.draw();

        this.tick();

        // for (String s : playerMap.values()) {
        // System.out.print("VAL: " + s + " ");
        // }
        // System.out.print("\n");

    }

    public ArrayList<ArrayList<Character>> getTileMap() {
        return this.tileMap;
    }

    public void keyPressed() {
        if (key == CODED) {
            bombGuy.handleKey(keyCode);
        } else if (key == ' ') {
            bombGuy.placeBomb(bombList);
        }

    }

    public void gameOver() {
        fill(239, 129, 0);
        rect(0, 0, 481, 481);
        textAlign(CENTER, CENTER);
        fill(0);
        text("GAME OVER", 240, 240);
        noLoop();
    }

    public void youWin() {
        fill(239, 129, 0);
        rect(0, 0, 481, 481);
        textAlign(CENTER, CENTER);
        fill(0);
        text("YOU WIN", 240, 240);
        noLoop();

    }

    public BombGuy getBombGuy() {
        return bombGuy;
    }

    public static void main(String[] args) {
        PApplet.main("demolition.App");
    }
}
