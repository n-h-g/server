package com.nhg.game.room;

import com.nhg.game.utils.Int3;
import com.nhg.game.utils.PostgreSQLEnumType;
import com.nhg.game.utils.Rotation;
import com.nhg.game.utils.pathfinder.Layout;
import com.nhg.game.utils.pathfinder.Tile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import java.util.stream.Stream;

@Slf4j
@Getter
@Setter
@Embeddable
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
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
    @Type(type = "pgsql_enum")
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

                this.tiles[x][y] = new Tile(new Int3(x,y,z));
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

    public Int3 getInt3AtDoor() {
        return new Int3(
                doorX,
                doorY,
                getTile(doorX, doorY).getPosition().getZ()
        );
    }

}