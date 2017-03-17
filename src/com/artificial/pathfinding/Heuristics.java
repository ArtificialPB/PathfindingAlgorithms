package com.artificial.pathfinding;

public enum Heuristics {
    MANHATTAN((start, end) -> Math.abs(start.getX() - end.getX()) + Math.abs(start.getY() - end.getY())),
    EUCLIDEAN((start, end) -> Math.sqrt((start.getX() - end.getX()) * (start.getX() - end.getX()) + (start.getY() - end.getY()) * (start.getY() - end.getY()))),
    OCTILE((start, end) -> {
        final int dx = Math.abs(start.getX() - end.getX());
        final int dy = Math.abs(start.getY() - end.getY());
        final double V = Math.sqrt(2) - 1;
        return (dx < dy) ? V * dx + dy : V * dy + dx;
    }),
    CHEBYSHEV(((start, end) -> {
        final int dx = Math.abs(start.getX() - end.getX());
        final int dy = Math.abs(start.getY() - end.getY());
        return Math.max(dx, dy);
    }));
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
