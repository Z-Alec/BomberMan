package demolition.Players;

import processing.core.*;
import demolition.App;
import java.util.*;

public class YellowEnemy extends Enemy implements EnemyInterface {
    private int index = 0;
    private int end = 3;

    public YellowEnemy(int x, int y, App app) {
        super(x, y);
        this.app = app;
        this.sprite = this.app.loadImage("src/main/resources/yellow_enemy/yellow_down1.png");
    }

    public void move() {
        // Every second, check if next tile is free before moving in straight line
        int[] newCoords = moveMap.get(direction).move();
        if (isTileFree(newCoords[0], newCoords[1], app)) {
            this.x = newCoords[0];
            this.y = newCoords[1];
        } else {
            moveDecision();
        }
    }

    public void tick() {

        // Every second, check if next tile is free before moving in straight line
        if (index + 1 > end) {
            index -= 3;
        } else {
            index += 1;
        }
        this.sprite = app.YellowSprites.get(index);

        blankTile();

        app.playerMap.put(this, String.format("%d%d", x, y));

    }

    public void moveDecision() {

    }

}
