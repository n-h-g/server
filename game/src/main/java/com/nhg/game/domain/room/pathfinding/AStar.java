package com.nhg.game.domain.room.pathfinding;

import com.nhg.game.domain.room.layout.Layout;
import com.nhg.game.domain.room.layout.Tile;
import com.nhg.game.domain.shared.position.Position2;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

@Slf4j
public class AStar implements Pathfinder {
    public interface Heuristic {
        double calculate(Tile start, Tile target, Tile current);
    }

    private static final int OrthogonalMovementCost = 10;
    private static final int DiagonalMovementCost = 14;

    private final boolean isDiagonalMovementAllowed;

    private final Map<String, Tile> cameFrom = new HashMap<>();
    private final Map<String, Double> gCost = new HashMap<>();
    private final Map<String, Double> hCost = new HashMap<>();

    public AStar(boolean isDiagonalMovementAllowed) {
        this.isDiagonalMovementAllowed = isDiagonalMovementAllowed;
    }

    private Heuristic getHeuristic() {
        return (start, target, current) -> {

            int dx = Math.abs(target.getPosition().getX() - current.getPosition().getX());
            int dy = Math.abs(target.getPosition().getY() - current.getPosition().getY());

            if (isDiagonalMovementAllowed) {
                // Diagonal distance
                return OrthogonalMovementCost * (dx + dy)
                        + (DiagonalMovementCost - 2 * OrthogonalMovementCost) * Math.min(dx, dy);
            }

            // Manhattan distance
            return OrthogonalMovementCost * (dx + dy);
        };
    }

    private Deque<Tile> retrievePath(Tile start, Tile target) {
        LinkedList<Tile> path = new LinkedList<>();
        if (start == null)
            return path;

        Tile curr = target;
        while (curr != null) {
            path.addFirst(curr);
            curr = cameFrom.get(curr.toString());
            if ((curr != null) && (curr.equals(start))) {
                return path;
            }
        }
        return path;
    }

    private double getTileFCost(Tile tile) {
        return gCost.getOrDefault(tile.toString(), 0d) + hCost.getOrDefault(tile.toString(), 0d);
    }

    @Override
    public Queue<Tile> findPath(Tile start, Tile target, Layout layout) {

        Queue<Tile> openQueue = new PriorityQueue<>((o1, o2) -> {
            double dif = getTileFCost(o1) - getTileFCost(o2);
            return dif == 0 ? 0 : dif > 0 ? 1 : -1;
        });

        List<Tile> closedList = new LinkedList<>();

        Tile current;
        List<Position2> adjPositions;
        double tentative_gScore;

        try {

            if (layout == null || start == null || target == null
                    || start.equals(target) || target.getPosition().getZ() == 0f)
                return openQueue;

            gCost.put(start.toString(), 0d);

            //Add first tile to de queue
            openQueue.add(start);

            while (!openQueue.isEmpty()) {
                current = openQueue.poll();
                closedList.add(current);

                if (current.equals(target))
                    return retrievePath(start, target);

                adjPositions = getAdjacentPositions(current.getPosition().toPosition2());

                for (Position2 adjPosition : adjPositions) {

                    Tile adjTile;

                    try {
                        adjTile = layout.getTile(adjPosition.getX(), adjPosition.getY());
                    } catch (IndexOutOfBoundsException e) {
                        continue;
                    }

                    boolean isDiagonalMovement = isDiagonalMovement(current.getPosition().toPosition2(), adjPosition);

                    tentative_gScore = gCost.getOrDefault(current.toString(), 0d)
                            + (isDiagonalMovement ? DiagonalMovementCost : OrthogonalMovementCost);

                    if (closedList.contains(adjTile))
                        continue;


                    if (adjTile.getPosition().getZ() == 0 || adjTile.getState() == Tile.State.CLOSE) {
                        openQueue.remove(adjTile);
                        closedList.add(adjTile);
                        continue;
                    }

                    if (!openQueue.contains(adjTile) || tentative_gScore < gCost.get(adjTile.toString())) {

                        cameFrom.put(adjTile.toString(), current);
                        gCost.put(adjTile.toString(), tentative_gScore);
                        hCost.put(adjTile.toString(), getHeuristic().calculate(start, target, adjTile));

                        if(!openQueue.contains(adjTile))
                            openQueue.add(adjTile);
                    }
                }
            }

        } catch (Exception e) {
            log.error("Error "+ e);
        }

        return new LinkedList<>();
    }



    private List<Position2> getAdjacentPositions(Position2 current) {
        List<Position2> adj = new ArrayList<>();

        adj.add(new Position2(current.getX() - 1, current.getY()));
        adj.add(new Position2(current.getX() + 1, current.getY()));
        adj.add(new Position2(current.getX(), current.getY() - 1));
        adj.add(new Position2(current.getX(), current.getY() + 1));

        if (!isDiagonalMovementAllowed) return adj;

        adj.add(new Position2(current.getX() + 1,current.getY() + 1));
        adj.add(new Position2(current.getX() + 1,current.getY() - 1));
        adj.add(new Position2(current.getX() - 1,current.getY() + 1));
        adj.add(new Position2(current.getX() - 1,current.getY() - 1));

        return adj;
    }

    private static boolean isDiagonalMovement(Position2 current, Position2 previous) {
        return Objects.equals(previous, new Position2(current.getX() + 1, current.getY() + 1))
                || Objects.equals(previous, new Position2(current.getX() + 1, current.getY() - 1))
                || Objects.equals(previous, new Position2(current.getX() - 1, current.getY() + 1))
                || Objects.equals(previous, new Position2(current.getX() - 1, current.getY() - 1));
    }
}
