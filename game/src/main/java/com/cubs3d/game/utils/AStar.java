package com.cubs3d.game.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class AStar {
    private static final int OrthogonalMovementCost = 10;
    private static final int DiagonalMovementCost = 14;

    private final List<Int2> _openTiles;
    private final List<Int2> _closedTiles;
    private final Map<Int2, Integer> _gScores;
    private final Map<Int2, Integer> _hScores;
    private final Map<Int2, Int2> _cameFrom;

    private final boolean _isDiagonalMovementAllowed;

    public AStar(boolean isDiagonalMovementAllowed) {
        _openTiles = new ArrayList<>();
        _closedTiles = new ArrayList<>();
        _gScores = new HashMap<>();
        _hScores = new HashMap<>();
        _cameFrom = new HashMap<>();
        _isDiagonalMovementAllowed = isDiagonalMovementAllowed;
    }

    public AStar() {
        this(false);
    }

    public List<Int2> findPath(Int2 start, Int2 goal) {
        _gScores.put(start, 0);
        _hScores.put(start, CalculateHeuristicScore(start, goal));

        _openTiles.add(start);

        while (_openTiles.size() != 0) {
            Int2 current = findLowestFScore();

            if (current == null) return null;

            _openTiles.remove(current);
            _closedTiles.add(current);

            if (current == goal) {
                return ReconstructPath(current);
            }

            List<Int2> adjTiles = GetAdjacentNodes(current);

            for (Int2 adj : adjTiles) {
                if (_closedTiles.contains(adj)) continue;

                int gScoreTentatives = CalculateGScore(adj, current);

                int gScoreValue = _gScores.getOrDefault(adj, 0);

                if (_openTiles.contains(adj) && gScoreTentatives >= gScoreValue) continue;

                _cameFrom.put(adj, current);
                _gScores.put(adj, gScoreTentatives);
                _hScores.put(adj, CalculateHeuristicScore(adj, goal));

                if (!_openTiles.contains(adj)) {
                    _openTiles.add(adj);
                }
            }
        }

        return null;
    }

    private Int2 findLowestFScore() {
        int lowestScore = Integer.MAX_VALUE;
        Int2 lowestScoreVector = _openTiles.get(0);

        for (Int2 pos : _openTiles) {
            int gScore = _gScores.getOrDefault(pos, Integer.MAX_VALUE / 2);
            int hScore = _hScores.getOrDefault(pos, Integer.MAX_VALUE / 2);

            int fScore = gScore + hScore;

            if (fScore >= lowestScore) continue;

            lowestScore = fScore;
            lowestScoreVector = pos;
        }

        return lowestScoreVector;
    }

    public void addClosedTile(Int2 tile) {
        this._closedTiles.add(tile);
    }

    private int CalculateGScore(Int2 current, Int2 previous) {
        int prevScore = _gScores.getOrDefault(previous, Integer.MAX_VALUE / 2);

        return prevScore + (IsDiagonalMovement(current, previous) ? DiagonalMovementCost : OrthogonalMovementCost);
    }

    private static boolean IsDiagonalMovement(Int2 current, Int2 previous) {
        return Objects.equals(previous, new Int2(current.getX() + 1, current.getY() + 1))
                || Objects.equals(previous, new Int2(current.getX() + 1, current.getY() - 1))
                || Objects.equals(previous, new Int2(current.getX() - 1, current.getY() + 1))
                || Objects.equals(previous, new Int2(current.getX() - 1, current.getY() - 1));
    }


    private static int CalculateHeuristicScore(Int2 current, Int2 goal) {
        int dx = Math.abs((current.getX() - goal.getX()));
        int dy = Math.abs((current.getY() - goal.getY()));

        int diagonal, orthogonal;

        if (dx > dy) {
            diagonal = dy;
            orthogonal = dx - diagonal;
        } else {
            diagonal = dx;
            orthogonal = dy - diagonal;
        }

        return diagonal * DiagonalMovementCost + orthogonal * OrthogonalMovementCost;
    }


    private List<Int2> ReconstructPath(Int2 current) {
        if (!_cameFrom.containsKey(current)) return new ArrayList<>();

        Int2 prev = _cameFrom.get(current);

        List<Int2> path = ReconstructPath(prev);
        path.add(current);

        return path;
    }

    private List<Int2> GetAdjacentNodes(Int2 current) {
        List<Int2> adj = new ArrayList<>();

        adj.add(new Int2(current.getX() - 1, current.getY()));
        adj.add(new Int2(current.getX() + 1, current.getY()));
        adj.add(new Int2(current.getX(), current.getY() - 1));
        adj.add(new Int2(current.getX(), current.getY() + 1));


        if (!_isDiagonalMovementAllowed) return adj;

        adj.add(new Int2(current.getX() + 1,current.getY() + 1));
        adj.add(new Int2(current.getX() + 1,current.getY() - 1));
        adj.add(new Int2(current.getX() - 1,current.getY() + 1));
        adj.add(new Int2(current.getX() - 1,current.getY() - 1));

        return adj;
    }

    public void Clear() {
        _openTiles.clear();
        _closedTiles.clear();
        _gScores.clear();
        _hScores.clear();
        _cameFrom.clear();
    }
}