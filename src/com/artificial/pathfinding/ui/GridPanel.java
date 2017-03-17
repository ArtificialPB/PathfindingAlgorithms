package com.artificial.pathfinding.ui;

import com.artificial.pathfinding.Graph;
import com.artificial.pathfinding.Node;
import com.artificial.pathfinding.Tile;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

class GridPanel extends JPanel implements MouseInputListener {
    private static final int CELL_SIZE = 30, MIN_GRID_SIZE = 5;
    private static final int START_NODE = -2, END_NODE = -3;
    private int startX = 0, startY = 0, endX = 0, endY = 0;
    private final int rows, cols;
    private final Graph graph;
    private static final Color PATH_COLOR = new Color(105, 154, 178), OPENED_COLOR = new Color(207, 171, 44), CLOSED_COLOR = new Color(145, 154, 85);
    private java.util.List<Node> path = new ArrayList<>();

    GridPanel(int rows, int cols) {
        this.rows = Math.max(MIN_GRID_SIZE, rows);
        this.cols = Math.max(MIN_GRID_SIZE, cols);
        this.graph = new Graph(rows, cols);
        for (int x = 0; x < this.rows; x++) {
            for (int y = 0; y < this.cols; y++) {
                this.graph.addNode(new Node(x, y, Node.CLEAR));
            }
        }
        initialize();
    }

    private void initialize() {
        final Dimension d = new Dimension(2 + (rows) * CELL_SIZE, 2 + (cols) * CELL_SIZE);
        this.setPreferredSize(d);
        this.startX = (int) Math.floor(rows / 2d);
        this.endX = (int) Math.ceil(rows / 2d);
        final int startEndY = cols / 2;
        this.startY = startEndY;
        this.endY = startEndY;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        final ActionListener repainter = e -> GridPanel.this.repaint();
        new Timer(50, repainter).start();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        final Graphics2D g = (Graphics2D) graphics;
        for (int y = 0; y < cols; ++y) {
            int sy = 1 + (cols - y - 1) * (CELL_SIZE);
            for (int x = 0; x < rows; x++) {
                int sx = 1 + x * (CELL_SIZE);
                final Node n = graph.getNodeAt(x, y);
                g.setColor(getTileColor(n));
                g.fillRect(sx, sy, CELL_SIZE, CELL_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(sx, sy, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    private boolean isPath(final Node node) {
        for (Node n : path) {
            if (n.getX() == node.getX() && n.getY() == node.getY()) {
                return true;
            }
        }
        return false;
    }

    private Color getTileColor(final Node n) {
        if (isStart(n)) {
            return Color.GREEN;
        } else if (isEnd(n)) {
            return Color.RED;
        } else if (n.getFlag() == Node.BLOCKED) {
            return Color.GRAY;
        } else if (isPath(n)) {
            return PATH_COLOR;
        } else switch (n.getState()) {
            case OPENED:
                return OPENED_COLOR;
            case CLOSED:
                return CLOSED_COLOR;
        }
        return Color.WHITE;
    }

    private boolean isStart(final Node n) {
        return n.getX() == startX && n.getY() == startY;
    }

    private boolean isEnd(final Node n) {
        return n.getX() == endX && n.getY() == endY;
    }

    private Tile lastTile = new Tile(-1, -1);
    private boolean movingStart = false, movingEnd = false;

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        final Tile t = getTileFromPoint(e.getPoint());
        if (checkBounds(t)) {
            final Node n = graph.getNodeAt(t.getX(), t.getY());
            final int flag = n.getFlag();
            if (isStart(n)) {
                movingStart = true;
            } else if (isEnd(n)) {
                movingEnd = true;
            } else {
                switch (flag) {
                    case Node.CLEAR:
                        n.setFlag(Node.BLOCKED);
                        break;
                    case Node.BLOCKED:
                        n.setFlag(Node.CLEAR);
                        break;
                }
            }
            lastTile = t;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        movingStart = false;
        movingEnd = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        final Tile t = getTileFromPoint(e.getPoint());
        if (checkBounds(t) && (lastTile.getX() != t.getX() || lastTile.getY() != t.getY())) {
            final Node n = this.graph.getNodeAt(t.getX(), t.getY());
            final int flag = n.getFlag();
            if (movingStart || movingEnd) {
                if (flag != Node.CLEAR) return;
                if (movingStart) {
                    this.startX = t.getX();
                    this.startY = t.getY();
                } else {
                    this.endX = t.getX();
                    this.endY = t.getY();
                }
            } else {
                if (flag == Node.CLEAR) {
                    n.setFlag(Node.BLOCKED);
                } else if (flag == Node.BLOCKED) {
                    n.setFlag(Node.CLEAR);
                }
            }
            lastTile = t;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    private boolean checkBounds(final Tile t) {
        return t.getX() >= 0 && t.getX() < rows && t.getY() >= 0 && t.getY() < cols;
    }

    private Tile getTileFromPoint(final Point p) {
        return new Tile((int) Math.floor(p.x / CELL_SIZE), cols - (int) Math.floor(p.y / CELL_SIZE) - 1);
    }

    public Tile getStart() {
        return new Tile(startX, startY);
    }

    public Tile getEnd() {
        return new Tile(endX, endY);
    }

    public Graph getGraph() {
        return graph;
    }

    public void setPath(final java.util.List<Node> path) {
        this.path = path;
    }

}
