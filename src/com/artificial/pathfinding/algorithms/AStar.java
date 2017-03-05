package com.artificial.pathfinding.algorithms;

import com.artificial.pathfinding.Heuristics;
import com.artificial.pathfinding.Node;

import java.util.*;
//https://en.wikipedia.org/wiki/A*_search_algorithm
public class AStar implements Pathfinder {
    @Override
    public List<Node> findPath(Node start, Node end) {
        final Heuristics heuristics = Heuristics.EUCLIDEAN;
        final Map<Node, Double> g_score = new HashMap<>(), f_score = new HashMap<>();
        final Set<Node> closed = new HashSet<>();
        final Queue<Node> opened = new PriorityQueue<>(Comparator.comparing(o -> f_score.getOrDefault(o, Double.MAX_VALUE)));
        final Map<Node, Node> path = new HashMap<>();
        g_score.put(start, 0d);
        f_score.put(start, heuristics.distance(start, end));
        while (!opened.isEmpty()) {
            //Node with lowest f_score
            final Node curr = opened.poll();
            if (curr.equals(end)) {
                return constructPath(path, end);
            }
            closed.add(curr);
            for (final Node next : curr.getEdges()) {
                if (closed.contains(next) || !next.valid()) {
                    continue;
                }
                final double total_g = g_score.get(curr) + next.getCost();
                if (!opened.contains(next)) {
                    opened.add(next);
                } else if (total_g >= g_score.getOrDefault(next, Double.MAX_VALUE)) {
                    continue;
                }
                path.put(next, start);
                g_score.put(next, total_g);
                f_score.put(next, total_g + heuristics.distance(next, end));
            }
        }
        return null;
    }

}
