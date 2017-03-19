package com.artificial.pathfinding.algorithms;

import com.artificial.pathfinding.Graph;
import com.artificial.pathfinding.Node;

import java.util.*;

//https://en.wikipedia.org/wiki/Depth-first_search
public class DepthFirstSearch extends Algorithm {
    @Override
    public List<Node> findPath(final Graph graph, final Node start, final Node end) {
        final Deque<Node> opened = new ArrayDeque<>();
        final Map<Node, Node> path = new HashMap<>();
        opened.add(start);
        while (!opened.isEmpty()) {
            final Node curr = opened.poll();
            if (curr.equals(end)) {
                return constructPath(path, end);
            }
            if (curr.getState() != Node.State.CLOSED) {
                curr.setState(Node.State.CLOSED);
                for (final Node next : getEdges(graph, curr)) {
                    if (opened.contains(next) || !next.valid()) {
                        continue;
                    }
                    opened.add(next);
                    next.setState(Node.State.OPENED);
                    path.put(next, curr);
                    delay(10);
                }
            }
        }
        return null;
    }
}
