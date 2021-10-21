package demolition;

import processing.core.PApplet;
import processing.core.PImage;
import demolition.Exceptions.*;

import java.io.*;
import java.util.*;

import demolition.Exceptions.MapException;
import demolition.Players.*;

public class Map {
    private String filepath;
    private int rows = 13;
    private int cols = 15;

    private PImage player;
    private PImage red;
    private PImage yellow;

    private Hashtable<Character, PImage> tileMap = new Hashtable<Character, PImage>();
    private ArrayList<Player> player_list = new ArrayList<Player>();

    private PApplet app;

    public Map(String filepath, PApplet app) {
        this.filepath = filepath;

        this.player = app.loadImage("src/main/resources/player/player1.png");
        this.red = app.loadImage("src/main/resources/red_enemy/red_down1.png");
        this.yellow = app.loadImage("src/main/resources/yellow_enemy/yellow_down1.png");

        this.tileMap.put('W', app.loadImage("src/main/resources/wall/solid.png"));
        this.tileMap.put('B', app.loadImage("src/main/resources/broken/broken.png"));
        this.tileMap.put(' ', app.loadImage("src/main/resources/empty/empty.png"));
        this.tileMap.put('G', app.loadImage("src/main/resources/goal/goal.png"));
        this.tileMap.put('P', app.loadImage("src/main/resources/empty/empty.png"));
        this.tileMap.put('R', app.loadImage("src/main/resources/empty/empty.png"));
        this.tileMap.put('Y', app.loadImage("src/main/resources/empty/empty.png"));

        this.app = app;

    }

    public void tick() {

    }

    public Player makePlayer(char color, int x, int y) {
        switch (color) {
            case 'P':
                return new BombGuy(x, y);
            case 'R':
                return new RedEnemy(x, y);
            case 'Y':
                return new YellowEnemy(x, y);
            default:
                return null;
        }
    }

    public void parseMapTxt() throws MapException {
        File f = new File(filepath);
        try {

            Scanner scan = new Scanner(f);
            // Draw map one row at a time
            for (int i = 0; i < rows; i++) {
                try {
                    String line = scan.nextLine();

                    if (line.length() != cols) {
                        scan.close();
                        throw new MapException("Not enough cols in map");
                    }

                    drawLine(line, i);

                } catch (NoSuchElementException e) {
                    scan.close();
                    System.out.println("Not enough rows in map");
                }
            }
            scan.close();

        } catch (FileNotFoundException e) {
            System.err.println("Map File not found!");
        }

    }

    public void drawLine(String line, int row) {

        for (int i = 0; i < line.length(); i++) {
            char tile = line.charAt(i);
            int x = 32 * i;
            int y = 64 + row * 32;

            app.image(tileMap.get(tile), x, y);

            if (tile == 'P' || tile == 'R' || tile == 'Y') {
                player_list.add(makePlayer(tile, x, y));
            }

        }
    }

    public void draw(PApplet app) {
        try {
            parseMapTxt();
        } catch (MapException e) {
            System.out.println(e);
        }
    }

}
