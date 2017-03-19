package com.artificial.pathfinding.algorithms;

import com.artificial.pathfinding.Graph;
import com.artificial.pathfinding.Heuristics;
import com.artificial.pathfinding.Node;

import java.util.*;

public class BestFirstSearch extends Algorithm {
    @Override
    public List<Node> findPath(final Graph graph, final Node start, final Node end) {
        final Heuristics heuristics = graph.getHeuristics();
        final Map<Node, Double> h_score = new HashMap<>();
        final Queue<Node> opened = new PriorityQueue<>(Comparator.comparing(o -> h_score.getOrDefault(o, Double.MAX_VALUE)));
        final Map<Node, Node> path = new HashMap<>();
        h_score.put(start, heuristics.distance(start, end));
        opened.add(start);
        while (!opened.isEmpty()) {
            //Node with lowest h_score
            final Node curr = opened.poll();
            if (curr.equals(end)) {
                return constructPath(path, end);
            }
            curr.setState(Node.State.CLOSED);
            for (final Node next : getEdges(graph, curr)) {
                if (next.getState() == Node.State.CLOSED || !next.valid()) {
                    continue;
                }
                final double total_g = h_score.get(curr) + next.getCost() + heuristics.distance(curr, next);
                if (!opened.contains(next)) {
                    opened.add(next);
                    next.setState(Node.State.OPENED);
                } else if (total_g >= h_score.getOrDefault(next, Double.MAX_VALUE)) {
                    continue;
                }
                path.put(next, curr);
                h_score.put(next, total_g);
                delay(10);
            }
        }
        return null;
    }

}
