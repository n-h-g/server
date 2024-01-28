package com.nhg.game.room;

import com.nhg.game.utils.Position3;
import com.nhg.game.utils.Rotation;
import com.nhg.game.utils.pathfinder.Layout;
import com.nhg.game.utils.pathfinder.Tile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Transient;

import java.util.stream.Stream;

@Slf4j
@Getter
@Setter
@Embeddable
@NoArgsConstructor
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
    @Column(columnDefinition = "varchar(1000000) default '0'", nullable = false)
    private String layout;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer doorX;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer doorY;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "rotation default 'SOUTH'")
    private Rotation doorRotation;

    @Transient
    private Tile[][] tiles;

    @Transient
    private int mapSizeX;

    @Transient
    private int mapSizeY;

    public RoomLayout(String layout, int doorX, int doorY, int doorRotation) {
        this.layout = layout;
        this.doorX = doorX;
        this.doorY = doorY;
        this.doorRotation = Rotation.intToRotation(doorRotation);
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
    @PostLoad
    private void prepareTilesFromLayout() {
        String[] layoutRows = layout.split("/");

        if (layoutRows.length == 0) throw new IllegalArgumentException("Wrong layout syntax");

        mapSizeY = Stream.of(layoutRows).map(String::length).max(Integer::compareTo).get();
        mapSizeX = layoutRows.length;

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
     * @param x position
     * @param y position
     * @return the tile on the given x and y position.
     * @throws IndexOutOfBoundsException when x or y are greater than the map size
     */
    public Tile getTile(int x, int y) throws IndexOutOfBoundsException {
        return tiles[x][y];
    }

    /**
     * Get the coordinates of the door as Position3.
     *
     * @return Position3 representing X,Y and Z coordinates of the door.
     *
     * @see Position3
     */
    public Position3 getDoorPosition3() {
        return new Position3(
                doorX,
                doorY,
                getTile(doorX, doorY).getPosition().getZ()
        );
    }

}
