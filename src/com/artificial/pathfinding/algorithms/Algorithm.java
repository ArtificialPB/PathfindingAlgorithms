package com.artificial.pathfinding.algorithms;

import com.artificial.pathfinding.Graph;
import com.artificial.pathfinding.Node;
import com.artificial.pathfinding.Tile;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class Algorithm {
    public abstract List<Node> findPath(final Graph graph, final Node start, final Node end);

    List<Node> constructPath(final Map<Node, Node> path, final Node end) {
        final LinkedList<Node> ret = new LinkedList<>();
        ret.add(end);
        Node current = path.get(end);
        Node next;
        while ((next = path.get(current)) != null) {
            ret.addFirst(current);
            current = next;
        }
        ret.addFirst(current); //adds first step
        return ret;
    }

    private List<Node> getEdges(final Graph graph, final Node base, final boolean diagonal) {
        final Tile[] tiles = new Tile[diagonal ? 8 : 4];
        tiles[0] = new Tile(base.getX() + 1, base.getY());
        tiles[1] = new Tile(base.getX(), base.getY() - 1);
        tiles[2] = new Tile(base.getX() - 1, base.getY());
        tiles[3] = new Tile(base.getX(), base.getY() + 1);
        if (diagonal) {
            tiles[4] = new Tile(base.getX() + 1, base.getY() - 1);
            tiles[5] = new Tile(base.getX() - 1, base.getY() - 1);
            tiles[6] = new Tile(base.getX() - 1, base.getY() + 1);
            tiles[7] = new Tile(base.getX() + 1, base.getY() + 1);
        }
        final List<Node> ret = new ArrayList<>();
        for (final Tile t : tiles) {
            final Node n;
            if ((n = graph.getNodeAt(t.getX(),t.getY())) != null) {
                ret.add(n);
            }
        }
        return ret;
    }

    List<Node> getEdges(final Graph graph, final Node node) {
        return getEdges(graph, node, true);
    }

    void delay(final long milis){
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
