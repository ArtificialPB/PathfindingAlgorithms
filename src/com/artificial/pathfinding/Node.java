package com.artificial.pathfinding;

public interface Node extends Locatable, Validatable {
    double getCost();

    Iterable<Node> getEdges();
}
