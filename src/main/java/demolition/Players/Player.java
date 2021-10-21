package demolition.Players;

import processing.core.*;
import processing.core.PApplet;

public class Player {

    protected int x;
    protected int y;

    protected PImage sprite;

    public Player() {
    }

    public void tick() {
        // Handles logic

    }

    public void draw(PApplet app) {
        // handles graphics, no logic
        app.image(sprite, 240, 240);

    }

}
