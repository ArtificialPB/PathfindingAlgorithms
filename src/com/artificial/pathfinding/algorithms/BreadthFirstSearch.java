package com.artificial.pathfinding.algorithms;

import com.artificial.pathfinding.Graph;
import com.artificial.pathfinding.Node;

import java.util.*;

//https://en.wikipedia.org/wiki/Breadth-first_search
public class BreadthFirstSearch extends Pathfinder {
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
            curr.setState(Node.State.CLOSED);
            for (final Node next : getEdges(graph, curr)) {
                if (next.getState() == Node.State.CLOSED || opened.contains(next) || !next.valid()) {
                    continue;
                }
                opened.add(next);
                next.setState(Node.State.OPENED);
                path.put(next, curr);
                delay(10);
            }
        }
        return null;
    }
}
