package demolition;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import demolition.Exceptions.MapException;

import static org.junit.jupiter.api.Assertions.*;

public class SampleTest {

    @Test
    public void simpleTest() {
        assertEquals(480, App.HEIGHT);
    }

    @Disabled
    @Test
    public void testMapSize() {
        App app = new App();
        Map map = new Map("level1.txt", app);

        try {
            map.parseMapTxt();
        } catch (MapException e) {
            System.out.println(e);
        }
    }

}
