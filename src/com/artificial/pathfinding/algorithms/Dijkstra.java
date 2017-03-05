package com.artificial.pathfinding.algorithms;

import com.artificial.pathfinding.Node;

import java.util.*;

//https://en.wikipedia.org/wiki/Dijkstra's_algorithm
public class Dijkstra implements Pathfinder {
    @Override
    public List<Node> findPath(final Node start, final Node end) {
        final Map<Node, Double> costMap = new HashMap<>();
        final Queue<Node> opened = new PriorityQueue<>(Comparator.comparing(o -> costMap.getOrDefault(o, Double.MAX_VALUE)));
        final Set<Node> closed = new HashSet<Node>();
        final Map<Node, Node> path = new HashMap<>();
        opened.add(start);
        costMap.put(start, 0d);
        while (!opened.isEmpty()) {
            final Node current = opened.poll();
            if (current.equals(end)) {
                return constructPath(path, end);
            }
            closed.add(current);
            for (final Node next : current.getEdges()) {
                if (closed.contains((next)) || !next.valid()) {
                    continue;
                }
                final double total_cost = costMap.getOrDefault(current, Double.MAX_VALUE) + next.getCost();
                if (total_cost < costMap.getOrDefault(next, Double.MAX_VALUE)) {
                    path.put(next, current);
                    costMap.put(next, total_cost);
                    opened.add(next);
                }
            }
        }
        return null;
    }
}
