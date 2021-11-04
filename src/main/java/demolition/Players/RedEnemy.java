package demolition.Players;

import processing.core.*;

import demolition.App;

public class RedEnemy extends Enemy implements EnemyInterface {
    private int index = 0;
    private int end = 3;

    public RedEnemy(int x, int y, App app) {
        super(x, y);
        this.app = app;
        this.sprite = this.app.loadImage("src/main/resources/red_enemy/red_down1.png");
    }

    public void move() {
        // Every second, check if next tile is free before moving in straight line
        int[] newCoords = moveMap.get(direction).move();

        if (isTileFree(newCoords[0], newCoords[1], app)) {
            this.x = newCoords[0];
            this.y = newCoords[1];

        } else {
            moveDecision();
            move();
        }

    }

    public void tick() {

        if (index + 1 > end) {
            index -= 3;
        } else {
            index += 1;
        }
        this.sprite = app.RedSprites.get(index);

        blankTile();

        app.playerMap.put(this, String.format("%d%d", x, y + 16));
        // System.out.println(String.format("RED: %d%d", x, y));

    }

    public void moveDecision() {
        int choice = (int) app.random(PConstants.LEFT, PConstants.DOWN + 1);
        this.direction = choice;

        this.index = app.directionMap.get(direction);
        this.end = this.index + 3;

    }
}
