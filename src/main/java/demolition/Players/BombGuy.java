package demolition.Players;

import demolition.App;
import demolition.Map;

import processing.core.*;
import java.util.ArrayList;

public class BombGuy extends Player {

    public BombGuy(int x, int y, App app) {
        this.x = x;
        this.y = y;
        this.app = app;
        this.sprite = this.app.loadImage("src/main/resources/player/player1.png");
    }

    public void handleKey(int key) {
        app.image(app.tileImages.get(' '), x, y + 16);
        ArrayList<Integer> indices = coord2Index(x, y - 32 + 16);
        char prevTopTile = app.tileMap.get(indices.get(1)).get(indices.get(0));

        app.image(app.tileImages.get(prevTopTile), x, y - 32 + 16);

        switch (key) {
        case PConstants.UP:
            // y is coord, tilemap is index cord
            if (isTileFree(x, y - 32)) {
                this.y -= 32;
            }
            break;
        case PConstants.DOWN:
            if (isTileFree(x, y + 32)) {
                this.y += 32;
            }
            break;
        case PConstants.LEFT:
            if (isTileFree(x - 32, y)) {
                this.x -= 32;
            }
            break;
        case PConstants.RIGHT:
            if (isTileFree(x + 32, y)) {
                this.x += 32;
            }
            break;
        default:
            break;

        }

        System.out.println(String.format("x: %d, y: %d", x, y));

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

    public boolean isTileFree(int x_new, int y_new) {

        ArrayList<Integer> indices = coord2Index(x_new, y_new);
        ArrayList<ArrayList<Character>> tileMap = app.getTileMap();

        char next_tile = tileMap.get(indices.get(1)).get(indices.get(0));

        switch (next_tile) {
        case 'W':
        case 'B':
            return false;
        default:
            return true;
        }
    }

}
