package com.artificial.pathfinding.algorithms;

import com.artificial.pathfinding.Node;

import java.util.*;
//https://en.wikipedia.org/wiki/Breadth-first_search
public class BreadthFirstSearch implements Pathfinder {
    @Override
    public List<Node> findPath(Node start, Node end) {
        final Set<Node> closed = new HashSet<>();
        final Deque<Node> opened = new ArrayDeque<>();
        final Map<Node, Node> path = new HashMap<>();
        opened.add(start);
        while (!opened.isEmpty()) {
            final Node current = opened.poll();
            if (current.equals(end)) {
                return constructPath(path, end);
            }
            closed.add(current);
            for (final Node next : current.getEdges()) {
                if (closed.contains(next) || !next.valid()) {
                    continue;
                }
                path.put(next, current);
                opened.add(next);
            }
        }
        return null;
    }
}
