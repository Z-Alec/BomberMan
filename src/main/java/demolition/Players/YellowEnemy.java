package demolition.Players;

import processing.core.*;
import demolition.App;

public class YellowEnemy extends Player {

    public YellowEnemy(int x, int y, App app) {
        this.x = x;
        this.y = y;
        this.app = app;
        this.sprite = this.app.loadImage("src/main/resources/yellow_enemy/yellow_down1.png");
    }

}
