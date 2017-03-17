package com.artificial.pathfinding.algorithms;

import com.artificial.pathfinding.Graph;
import com.artificial.pathfinding.Heuristics;
import com.artificial.pathfinding.Node;

import java.util.*;

//https://en.wikipedia.org/wiki/A*_search_algorithm
public class AStar extends Pathfinder {
    @Override
    public List<Node> findPath(final Graph graph, final Node start, final Node end) {
        final Heuristics heuristics = graph.getHeuristics();
        final Map<Node, Double> g_score = new HashMap<>(), f_score = new HashMap<>();
        final Queue<Node> opened = new PriorityQueue<>(Comparator.comparing(o -> f_score.getOrDefault(o, Double.MAX_VALUE)));
        final Map<Node, Node> path = new HashMap<>();
        g_score.put(start, 0d);
        f_score.put(start, heuristics.distance(start, end));
        opened.add(start);
        while (!opened.isEmpty()) {
            //Node with lowest f_score
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
                if (!opened.contains(next) || total_g < g_score.getOrDefault(next, Double.MAX_VALUE)) {
                    path.put(next, curr);
                    g_score.put(next, total_g);
                    f_score.put(next, total_g + heuristics.distance(next, end));
                    if (!opened.contains(next)) {
                        opened.add(next);
                        next.setState(Node.State.OPENED);
                    }
                }
                delay(10);
            }
        }
        return null;
    }

}
