package com.artificial.pathfinding;

public enum Heuristics {
    MANHATTAN((start, end) -> {
        final Tile a = start.getTile();
        final Tile b = end.getTile();
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }),
    EUCLIDEAN((start, end) -> {
        final Tile a = start.getTile();
        final Tile b = end.getTile();
        return Math.sqrt((a.getX() - b.getX()) * (a.getX() - b.getX()) + (a.getY() - b.getY()) * (a.getY() - b.getY()));
    }),
    DIAGONAL((start, end) -> {
        final Tile a = start.getTile();
        final Tile b = end.getTile();
        return Math.max(Math.abs(a.getX() - b.getX()), Math.abs(a.getY() - b.getY()));
    });
    private final Calculation calculation;

    Heuristics(Calculation calculation) {
        this.calculation = calculation;
    }

    public double distance(Locatable start, Locatable end) {
        return calculation.distance(start, end);
    }

    private interface Calculation {
        double distance(Locatable start, Locatable end);
    }

}
