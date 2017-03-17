package com.artificial.pathfinding;

public class Graph {
    private final Node[][] nodes;
    private Heuristics heuristics = Heuristics.EUCLIDEAN;

    public Graph(final int rows, final int cols) {
        this.nodes = new Node[rows][cols];
    }

    public void addNode(final Node node) {
        this.nodes[node.getX()][node.getY()] = node;
    }

    public Node getNodeAt(final int x, final int y) {
        if (x < 0 || x >= nodes.length || y < 0 || y >= nodes[0].length) {
            return null;
        }
        return nodes[x][y];
    }

    public int getSize() {
        return nodes.length * nodes[0].length;
    }

    public void resetStates() {
        for (final Node[] arr : nodes) {
            for (final Node n : arr) {
                n.setState(Node.State.NONE);
            }
        }
    }

    public void resetFlags(){
        for (final Node[] arr : nodes) {
            for (final Node n : arr) {
                n.setFlag(Node.CLEAR);
            }
        }
    }

    public Heuristics getHeuristics() {
        return heuristics;
    }

    public void setHeuristics(Heuristics heuristics) {
        this.heuristics = heuristics;
    }
}
