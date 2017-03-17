package com.artificial.pathfinding.algorithms;

import com.artificial.pathfinding.Graph;
import com.artificial.pathfinding.Node;

import java.util.*;

//https://en.wikipedia.org/wiki/Dijkstra's_algorithm
public class Dijkstra extends Pathfinder {
    @Override
    public List<Node> findPath(final Graph graph, final Node start, final Node end) {
        final Map<Node, Double> g_score = new HashMap<>();
        final Queue<Node> opened = new PriorityQueue<>(Comparator.comparing(o -> g_score.getOrDefault(o, Double.MAX_VALUE)));
        final Map<Node, Node> path = new HashMap<>();
        opened.add(start);
        g_score.put(start, 0d);
        while (!opened.isEmpty()) {
            final Node curr = opened.poll();
            if (curr.equals(end)) {
                return constructPath(path, end);
            }
            curr.setState(Node.State.CLOSED);
            for (final Node next : getEdges(graph, curr)) {
                if (next.getState() == Node.State.CLOSED || !next.valid()) {
                    continue;
                }
                final double total_g = g_score.get(curr) + next.getCost();
                if (!opened.contains(next)) {
                    opened.add(next);
                    next.setState(Node.State.OPENED);
                } else if (total_g >= g_score.getOrDefault(next, Double.MAX_VALUE)) {
                    continue;
                }
                path.put(next, curr);
                g_score.put(next, total_g);
                delay(10);
            }
        }
        return null;
    }
}
