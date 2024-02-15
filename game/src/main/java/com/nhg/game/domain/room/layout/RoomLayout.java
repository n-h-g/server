package com.nhg.game.domain.room.layout;

import com.nhg.game.domain.shared.position.Position2;
import com.nhg.game.domain.shared.position.Position3;
import com.nhg.game.domain.shared.position.Rotation;
import lombok.Getter;

import java.util.stream.Stream;

public class RoomLayout implements Layout {

    /**
     * layout is a string that represent the matrix: the numbers and letters represent the Z position
     * of the tile. (0-9 and letters A=10 to Z=36)
     * The / is used as end of line character.
     * <!-- Ex:
     * <!-- 0 A 1 0 1 2 /
     * <!-- 0 1 1 0 1 2 /
     * <!-- 0 1 1 1 1 2 /
     * <!-- 0 1 1 1 1 2 /
     */
    @Getter
    private final String layout;

    @Getter
    private final Position3 doorPosition;

    @Getter
    private final Rotation doorRotation;

    private Tile[][] tiles;

    public RoomLayout(String layout, Position2 doorPosition, Rotation doorRotation) {
        this.layout = layout;

        this.prepareTilesFromLayout();

        float doorZ = getTile(doorPosition).getPosition().getZ();
        this.doorPosition = new Position3(doorPosition, doorZ);

        this.doorRotation = doorRotation;
    }


    /**
     * Prepare the tiles matrix and create Tiles object (using Z as height)
     * <!--       Z
     * <!--       |
     * <!--       |
     * <!--       |
     * <!--       o _________ X
     * <!--      /
     * <!--     /
     * <!--    /
     * <!--   Y
     */
    private void prepareTilesFromLayout() {
        String[] layoutRows = layout.split("/");

        if (layoutRows.length == 0) throw new IllegalArgumentException("Wrong layout syntax");

        int mapSizeY = Stream.of(layoutRows).map(String::length).max(Integer::compareTo).get();
        int mapSizeX = layoutRows.length;

        this.tiles = new Tile[mapSizeX][mapSizeY];

        for (int x = 0; x < mapSizeX; x++) {
            for (int y = 0; y < mapSizeY; y++) {
                char tile;
                try {
                    tile = layoutRows[x].substring(y, y + 1).trim().toUpperCase().charAt(0);
                } catch (IndexOutOfBoundsException ignored) {
                    tile = '0';
                }

                // height 0 represent empty tile
                int z = Character.isAlphabetic(tile)
                        ? 10 + tile - 'A'
                        : Integer.parseInt(Character.toString(tile));

                this.tiles[x][y] = new Tile(new Position3(x,y,z));
            }
        }
    }

    /**
     * Get the tile on the given position on this layout.
     *
     * @param position the tile position.
     * @return the tile on the given x and y position.
     * @throws IndexOutOfBoundsException when x or y are greater than the map size
     */
    public Tile getTile(Position2 position) throws IndexOutOfBoundsException {
        return tiles[position.getX()][position.getY()];
    }

}
