package com.artificial.pathfinding.algorithms;

import com.artificial.pathfinding.Node;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface Pathfinder {
    List<Node> findPath(final Node start, final Node end);

    default List<Node> constructPath(final Map<Node, Node> path, final Node end) {
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
}
