package demolition;

import demolition.Players.BombGuy;
import demolition.Players.Player;

import java.util.ArrayList;
import processing.core.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

public class bombGuyTest {

    private Player player;
    private App app;

    // Make a player for each test
    @BeforeEach
    public void init() {
        app = new App();
        app.noLoop();
        player = new Player(32, 96);

    }

    // Test converting coords into indices
    @Test
    public void testC2I() {
        int[] indices = player.coord2Index(32, 96);
        assertArrayEquals(new int[] { 1, 1 }, indices);
    }

    @Disabled
    @Test
    public void blankTile() {
        app.loadConfig();
        player.blankTile();
    }

}
