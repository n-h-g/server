package com.cubs3d.game.utils.pathfinder;

import com.cubs3d.game.utils.Int2;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class AStar {

    public interface Heuristic {
        double calculate(Tile start, Tile target, Tile current);
    }

    private static final int OrthogonalMovementCost = 10;
    private static final int DiagonalMovementCost = 14;

    private final boolean isDiagonalMovementAllowed;

    public AStar(boolean isDiagonalMovementAllowed) {
        this.isDiagonalMovementAllowed = isDiagonalMovementAllowed;
    }

    private Heuristic getHeuristic() {
        return (start, target, current) -> {

            int dx = target.getPosition().getX() - current.getPosition().getX();
            int dy = target.getPosition().getY() - current.getPosition().getY();

            if (isDiagonalMovementAllowed) {
                return Math.sqrt(dx * dx + dy * dy);
            }

            // Manhattan distance
            dx = Math.abs(dx);
            dy = Math.abs(dy);

            return dx + dy;
        };
    }

    private static Deque<Tile> retrievePath(Tile start, Tile target) {
        LinkedList<Tile> path = new LinkedList<>();
        if (start == null)
            return path;

        Tile curr = target;
        while (curr != null) {
            path.addFirst(curr);
            curr = curr.getPreviousTile();
            if ((curr != null) && (curr.equals(start))) {
                return path;
            }
        }
        return path;
    }

    public Queue<Tile> findPath(Tile start, Tile target, Layout layout) {

        Queue<Tile> openQueue = new PriorityQueue<>(Comparator.comparingDouble(Tile::getFCost));
        List<Tile> closedList = new LinkedList<>();

        Tile current;
        List<Int2> adjPositions;
        double tentative_gScore;

        try {

            if (layout == null || start == null || target == null
                    || start.equals(target) || target.getPosition().getZ() == 0)
                return openQueue;

            start.setGCost(0);

            //Add first tile to de queue
            openQueue.add(start);

            while (!openQueue.isEmpty()) {
                current = openQueue.poll();
                closedList.add(current);

                if (current.equals(target))
                    return retrievePath(start, target);

                adjPositions = getAdjacentPositions(current.getPosition().ToInt2XY());

                for (Int2 adjPosition : adjPositions) {

                    Tile adjTile;

                    try {
                        adjTile = layout.getTile(adjPosition.getX(), adjPosition.getY());
                    } catch (IndexOutOfBoundsException e) {
                        continue;
                    }

                    boolean isDiagonalMovement = isDiagonalMovement(current.getPosition().ToInt2XY(), adjPosition);

                    tentative_gScore = current.getGCost()
                            + (isDiagonalMovement ? DiagonalMovementCost : OrthogonalMovementCost);

                    if (closedList.contains(adjTile))
                        continue;


                    if (adjTile.getPosition().getZ() == 0) {
                        openQueue.remove(adjTile);
                        closedList.add(adjTile);
                        continue;
                    }

                    if (!openQueue.contains(adjTile) || tentative_gScore < adjTile.getGCost()) {
                        adjTile.setPreviousTile(current);
                        adjTile.setGCost(tentative_gScore);
                        adjTile.setHCost(getHeuristic().calculate(start, target, adjTile));

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



    private List<Int2> getAdjacentPositions(Int2 current) {
        List<Int2> adj = new ArrayList<>();

        adj.add(new Int2(current.getX() - 1, current.getY()));
        adj.add(new Int2(current.getX() + 1, current.getY()));
        adj.add(new Int2(current.getX(), current.getY() - 1));
        adj.add(new Int2(current.getX(), current.getY() + 1));

        if (!isDiagonalMovementAllowed) return adj;

        adj.add(new Int2(current.getX() + 1,current.getY() + 1));
        adj.add(new Int2(current.getX() + 1,current.getY() - 1));
        adj.add(new Int2(current.getX() - 1,current.getY() + 1));
        adj.add(new Int2(current.getX() - 1,current.getY() - 1));

        return adj;
    }

    private static boolean isDiagonalMovement(Int2 current, Int2 previous) {
        return Objects.equals(previous, new Int2(current.getX() + 1, current.getY() + 1))
                || Objects.equals(previous, new Int2(current.getX() + 1, current.getY() - 1))
                || Objects.equals(previous, new Int2(current.getX() - 1, current.getY() + 1))
                || Objects.equals(previous, new Int2(current.getX() - 1, current.getY() - 1));
    }
}
