package com.cubs3d.game.utils;

import java.util.*;

public class AStar {
    private static final int OrthogonalMovementCost = 10;
    private static final int DiagonalMovementCost = 14;

    private final List<Int3> _openTiles;
    private final List<Int3> _closedTiles;
    private final Map<Int3, Integer> _gScores;
    private final Map<Int3, Integer> _hScores;
    private final Map<Int3, Int3> _cameFrom;

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

    public List<Int3> findPath(Int3 start, Int3 goal) {
        _gScores.put(start, 0);
        _hScores.put(start, CalculateHeuristicScore(start, goal));

        _openTiles.add(start);

        while (_openTiles.size() != 0) {
            Int3 current = findLowestFScore();

            _openTiles.remove(current);
            _closedTiles.add(current);

            if (current == goal) {
                return ReconstructPath(current);
            }

            List<Int3> adjTiles = GetAdjacentNodes(current);

            for (Int3 adj : adjTiles) {
                if (_closedTiles.contains(adj)) continue;

                int gScoreTentatives = CalculateGScore(adj, current);

                int gScoreValue = _gScores.get(adj);

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

    private Int3 findLowestFScore() {
        int lowestScore = Integer.MAX_VALUE;
        Int3 lowestScoreVector = _openTiles.get(0);

        for (Int3 pos : _openTiles) {
            int gScore = _gScores.get(pos);
            int hScore = _hScores.get(pos);

            int fScore = gScore + hScore;

            if (fScore >= lowestScore) continue;

            lowestScore = fScore;
            lowestScoreVector = pos;
        }

        return lowestScoreVector;
    }

    private int CalculateGScore(Int3 current, Int3 previous) {
        int prevScore = _gScores.get(previous);

        return prevScore + (IsDiagonalMovement(current, previous) ? DiagonalMovementCost : OrthogonalMovementCost);
    }

    private static boolean IsDiagonalMovement(Int3 current, Int3 previous) {
        return Objects.equals(previous, new Int3(current.getX() + 1, current.getY(), current.getZ() + 1))
                || Objects.equals(previous, new Int3(current.getX() + 1, current.getY(), current.getZ() - 1))
                || Objects.equals(previous, new Int3(current.getX() - 1, current.getY(), current.getZ() + 1))
                || Objects.equals(previous, new Int3(current.getX() - 1, current.getY(), current.getZ() - 1));
    }


    private static int CalculateHeuristicScore(Int3 current, Int3 goal) {
        int dx = Math.abs((current.getX() - goal.getX()));
        int dy = Math.abs((current.getZ() - goal.getZ()));

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


    private List<Int3> ReconstructPath(Int3 current) {
        if (!_cameFrom.containsKey(current)) return new ArrayList<>();

        Int3 prev = _cameFrom.get(current);

        List<Int3> path = ReconstructPath(prev);
        path.add(current);

        return path;
    }

    private List<Int3> GetAdjacentNodes(Int3 current) {
        List<Int3> adj = new ArrayList<>();

        adj.add(new Int3(current.getX() - 1, current.getY(), current.getZ()));
        adj.add(new Int3(current.getX() + 1, current.getY(), current.getZ()));
        adj.add(new Int3(current.getX(), current.getY(),current.getZ() - 1));
        adj.add(new Int3(current.getX(), current.getY(),current.getZ() + 1));


        if (!_isDiagonalMovementAllowed) return adj;

        adj.add(new Int3(current.getX() + 1, current.getY(),current.getZ() + 1));
        adj.add(new Int3(current.getX() + 1, current.getY(),current.getZ() - 1));
        adj.add(new Int3(current.getX() - 1, current.getY(),current.getZ() + 1));
        adj.add(new Int3(current.getX() - 1, current.getY(),current.getZ() - 1));

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