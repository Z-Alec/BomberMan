package demolition.Players;

import processing.core.*;

import demolition.App;

public class RedEnemy extends Player {
    private int type;

    public RedEnemy(int x, int y, App app) {
        this.x = x;
        this.y = y;
        this.app = app;
        this.sprite = this.app.loadImage("src/main/resources/red_enemy/red_down1.png");

    }
}
