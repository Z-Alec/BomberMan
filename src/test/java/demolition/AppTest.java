package demolition;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import demolition.Exceptions.MapException;
import processing.core.PApplet;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    private App app = new App();

    // Set Test Resource Directory
    @BeforeEach
    public void init() {
        app.setConfig("src/test/resources/");
    }

    // Intro Test
    @Test
    public void simpleTest() {
        assertEquals(480, App.HEIGHT);
    }

    // Test configDir has been changed
    @Test
    public void testConfigDir() {
        assertEquals("src/test/resources/", app.configDir);
        ;
    }

    // Test the setup method
    @Test
    public void testSetup() {
        app.noLoop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
    }

}
