package demolition.Players;

import java.util.ArrayList;

import demolition.App;

interface EnemyInterface {

    public default boolean isTileFree(int x_new, int y_new, App app) {

        int[] indices = coord2Index(x_new, y_new);
        ArrayList<ArrayList<Character>> tileMap = app.getTileMap();

        char next_tile = tileMap.get(indices[1]).get(indices[0]);

        switch (next_tile) {
        case 'W':
        case 'B':
            return false;
        default:
            // Else, we can move; refresh the tiles we are moving away from
            blankTile();
            return true;
        }

    }

    public void moveDecision();

    public int[] coord2Index(int x, int y);

    public void blankTile();

}
