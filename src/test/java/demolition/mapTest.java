package demolition;

import demolition.Players.*;

import java.util.ArrayList;
import processing.core.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class mapTest {

    private Map map;

    @BeforeEach
    public void init() {
        App app = new App();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setConfig("src/test/resources/");
        app.loadConfig();
        map = new Map(app.levelPath, app);
    }

    @Test
    public void testInitMap() {

        ArrayList<ArrayList<Character>> tileMap = map.initMap();
        assertFalse(tileMap.isEmpty());
        assertEquals("416416", map.goal);
    }

    @Test
    public void testMakeEnemy() {
        Enemy Renemy = map.makeEnemy('R', 32, 96);
        Enemy Yenemy = map.makeEnemy('Y', 32, 128);
        Enemy nullEnemy = map.makeEnemy('T', 32, 160);

        assertEquals("3296", Renemy.getCoordsAsString());
        assertEquals("32128", Yenemy.getCoordsAsString());
        assertNull(nullEnemy);

    }

}
