package com.artificial.pathfinding.algorithms;

import com.artificial.pathfinding.Heuristics;
import com.artificial.pathfinding.Node;

import java.util.LinkedList;
import java.util.List;

//http://waprogramming.com/papers/50af7709377c22.88356189.pdf
//Similar to A*, but uses less memory because only one path is stored at a time
public class IterativeDeepening implements Pathfinder {
    @Override
    public List<Node> findPath(Node start, Node end) {
        final Heuristics heuristics = Heuristics.EUCLIDEAN;
        double cost_limit = heuristics.distance(start, end);
        final List<Node> currentPath = new LinkedList<>();
        currentPath.add(start);
        //replace with graph.getSize() -> worst case scenario - it checks every node
        final int max_depth = 5000;
        for (int i = 0; i < max_depth; i++) {
            final Result r = depthLimitedSearch(heuristics, end, 0, new LinkedList<>(currentPath), cost_limit);
            if (r.currentPath != null) {
                return r.currentPath;
            }
            cost_limit = r.cost_limit;
        }
        return null;
    }

    private Result depthLimitedSearch(final Heuristics h, final Node end, final double start_cost, List<Node> currentPath, final double cost_limit) {
        final Node curr = currentPath.get(0);
        final double min_cost = start_cost + h.distance(curr, end);
        if (min_cost > cost_limit) {
            return new Result(null, min_cost);
        }
        if (curr.equals(end)) {
            return new Result(currentPath, cost_limit);
        }
        double next_cost_limit = Double.MAX_VALUE;
        for (final Node next : curr.getEdges()) {
            final double new_start_cost = start_cost + next.getCost();
            final List<Node> nextPath = new LinkedList<>(currentPath);
            nextPath.add(next);
            final Result result = depthLimitedSearch(h, end, new_start_cost, nextPath, cost_limit);
            if (result.currentPath != null) {
                return result;
            }
            next_cost_limit = Math.min(next_cost_limit, result.cost_limit);
        }
        return new Result(null, next_cost_limit);
    }

    private final class Result {
        private List<Node> currentPath;
        private double cost_limit;

        Result(List<Node> currentPath, double cost_limit) {
            this.currentPath = currentPath;
            this.cost_limit = cost_limit;
        }
    }

}
