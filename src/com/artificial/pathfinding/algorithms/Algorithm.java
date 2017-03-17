package com.artificial.pathfinding.algorithms;

public enum Algorithm {
    ASTAR("A*", new AStar()),
    BEST_FIRST_SEARCH("Best-First-Search", new BestFirstSearch()),
    BREADTH_FIRST_SEARCH("Breadth-First-Search", new BreadthFirstSearch()),
    DIJKSTRA("Dijkstra", new Dijkstra()),
   // ITERATIVE_DEEPENING("IDA*", new IterativeDeepening()),
    ;
    private final String displayName;
    private final Pathfinder algorithm;

    Algorithm(final String displayName, final Pathfinder algorithm) {
        this.displayName = displayName;
        this.algorithm = algorithm;
    }

    public Pathfinder getAlgorithm() {
        return algorithm;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
