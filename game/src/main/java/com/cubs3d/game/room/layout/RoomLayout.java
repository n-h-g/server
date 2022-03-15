package com.cubs3d.game.room.layout;

import com.cubs3d.game.utils.Int3;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Stream;

@Slf4j
public class RoomLayout {

    private Tile[][] tiles;


    /**
     *
     * @param layout
     * @see #prepareTilesFromLayout
     */
    public RoomLayout(String layout) {
        try {
            prepareTilesFromLayout(layout);
        } catch (Exception e) {
            log.error("Error while preparing the room layout: "+ e);
        }

    }

    /**
     * Prepare the tiles matrix and create Tiles object (using Y as height)
     *
     * <!--       Y
     * <!--       |
     * <!--       |
     * <!--       |
     * <!--       o _________ X
     * <!--      /
     * <!--     /
     * <!--    /
     * <!--   Z
     * @param layout layout is a string that represent the matrix: the numbers and letters represent the Y position
     *               of the tile. (0-9 and letters A=10 to Z=36)
     *               The / is used as end of line character.
     *               <!-- Ex:
     *               <!-- 0 A 1 0 1 2 /
     *               <!-- 0 1 1 0 1 2 /
     *               <!-- 0 1 1 1 1 2 /
     *               <!-- 0 1 1 1 1 2 /
     */
    private void prepareTilesFromLayout(String layout) {
        String[] layoutRows = layout.split("/");

        if (layoutRows.length <= 0) throw new IllegalArgumentException("Wrong layout syntax");

        int mapSizeZ = Stream.of(layoutRows).map(String::length).max(Integer::compareTo).get();
        int mapSizeX = layoutRows.length;

        this.tiles = new Tile[mapSizeX][mapSizeZ];

        for (int x = 0; x < mapSizeX; x++) {
            for (int z = 0; z < mapSizeZ; z++) {
                char tile;
                try {
                    tile = layoutRows[x].substring(z, z + 1).trim().toUpperCase().charAt(0);
                } catch (IndexOutOfBoundsException ignored) {
                    tile = '0';
                }

                // height 0 represent empty tile
                int y = Character.isAlphabetic(tile)
                        ? 10 + tile - 'A'
                        : Integer.parseInt(Character.toString(tile));

                this.tiles[x][z] = new Tile(new Int3(x,y,z));
            }
        }
    }

    /**
     * Get the tile on the given position on this layout.
     *
     * @param x position
     * @param z position
     * @return the tile on the given x and z position.
     * @throws IndexOutOfBoundsException when x or z are greater than the map size
     */
    public Tile getTile(int x, int z) throws IndexOutOfBoundsException {
        return tiles[x][z];
    }


}
