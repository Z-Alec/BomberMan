package demolition.Players;

import demolition.App;
import demolition.Map;

import processing.core.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public class BombGuy extends Player {

    private int index = 0;
    private int end = 3;

    public BombGuy(int x, int y, App app) {
        super(x, y);

        this.app = app;
        this.sprite = this.app.loadImage("src/main/resources/player/player1.png");
    }

    public void handleKey(int key) {

        switch (key) {
        case PConstants.UP:
            // y is coord, tilemap is index cord
            if (isTileFree(x, y - 32)) {
                this.y -= 32;
            }
            direction = PConstants.UP;
            break;
        case PConstants.DOWN:
            if (isTileFree(x, y + 32)) {
                this.y += 32;
            }
            direction = PConstants.DOWN;
            break;
        case PConstants.LEFT:
            if (isTileFree(x - 32, y)) {
                this.x -= 32;
            }
            direction = PConstants.LEFT;
            break;
        case PConstants.RIGHT:
            if (isTileFree(x + 32, y)) {
                this.x += 32;
            }
            direction = PConstants.RIGHT;
            break;
        default:
            break;

        }

        index = app.directionMap.get(key);
        end = index + 3;

    }

    public boolean isTileFree(int x_new, int y_new) {

        ArrayList<Integer> indices = coord2Index(x_new, y_new);
        ArrayList<ArrayList<Character>> tileMap = app.getTileMap();

        char next_tile = tileMap.get(indices.get(1)).get(indices.get(0));

        switch (next_tile) {
        case 'W':
        case 'B':
            // If next tile has wall, not free
            return false;
        default:
            blankTile();

            return true;
        }

    }

    public void tick() {

        if (index + 1 > end) {
            index -= 3;
        } else {
            index += 1;
        }
        this.sprite = app.BombGuySprites.get(index);

        blankTile();

    }

    public String getCoordsAsString() {
        return String.format("%d%d", x, y);
    }

}
