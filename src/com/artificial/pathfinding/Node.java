package com.artificial.pathfinding;

public class Node implements Locatable, Validatable {
    public static final int CLEAR = 0, BLOCKED = 1;
    private final int x, y;
    private int flag;
    private double cost = 1d;
    private State state = State.NONE;

    public Node(final int x, final int y) {
        this(x, y, Node.CLEAR);
    }

    public Node(final int x, final int y, final int flag) {
        this.x = x;
        this.y = y;
        this.flag = flag;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(final double cost) {
        this.cost = cost;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public boolean valid() {
        return flag != BLOCKED;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof Node && x == ((Node) obj).x && y == ((Node) obj).y && flag == ((Node) obj).flag;
    }

    @Override
    public String toString() {
        return x + ", " + y + "; " + flag;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public enum State {
        NONE, OPENED, CLOSED;
    }
}
