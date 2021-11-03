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

    private ArrayList<ArrayList<Character>> tileMap = new ArrayList<ArrayList<Character>>();

    private ArrayList<Player> player_list = new ArrayList<Player>();
    Hashtable<Enemy, String> EnemyMap = new Hashtable<>();
    private BombGuy bombGuy;

    private App app;

    public Map(String filepath, App app) {
        this.filepath = filepath;

        this.app = app;

    }

    public void tick() {

    }

    public Enemy makeEnemy(char color, int x, int y) {
        switch (color) {
        // Sprites are 48 x 32 pixels, make feet touch the bottom
        // of grid
        case 'R':
            return new RedEnemy(x, y - 16, app);
        case 'Y':
            return new YellowEnemy(x, y - 16, app);
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

        ArrayList<Character> x_list = new ArrayList<Character>();

        for (int i = 0; i < line.length(); i++) {
            char tile = line.charAt(i);
            int x = 32 * i;
            int y = 64 + row * 32;

            // Load the image corresponding with the char at this position
            app.image(app.tileImages.get(tile), x, y);
            // Save the tile char to our map
            x_list.add(tile);

            // If a player char, create the Player object at this position
            if (tile == 'R' || tile == 'Y') {
                EnemyMap.put(makeEnemy(tile, x, y), String.format("%d%d", x, y));
            } else if (tile == 'P') {
                bombGuy = new BombGuy(x, y - 16, app);
            }

        }

        tileMap.add(x_list);
    }

    public ArrayList<ArrayList<Character>> initMap(PApplet app) {
        try {
            parseMapTxt();
        } catch (MapException e) {
            System.out.println(e);
        }

        return tileMap;
    }

    public ArrayList<Player> getPlayerList() {
        return player_list;
    }

    public Hashtable<Enemy, String> getPlayerMap() {
        return EnemyMap;
    }

    public BombGuy getBombGuy() {
        return bombGuy;
    }

    public void draw(PApplet app) {

    }

}
