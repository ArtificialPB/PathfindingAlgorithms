package com.artificial.pathfinding.ui;

import com.artificial.pathfinding.Graph;
import com.artificial.pathfinding.Heuristics;
import com.artificial.pathfinding.Node;
import com.artificial.pathfinding.Tile;
import com.artificial.pathfinding.algorithms.Algorithm;

import javax.swing.*;
import java.awt.*;

public class UserInterface extends JFrame {
    public UserInterface() {
        initialize();
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
    }

    private void initialize() {
        final JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        this.setLayout(new BorderLayout());
        this.add(contentPane, BorderLayout.CENTER);

        final JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        final JPanel topSettingsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        settingsPanel.add(topSettingsPanel);

        final JComboBox<Algorithm> algorithmCombo = new JComboBox<>(new DefaultComboBoxModel<>(Algorithm.values()));
        final JComboBox<Heuristics> heuristicsCombo = new JComboBox<>(Heuristics.values());
        final JButton searchButton = new JButton("Find Path");
        final JButton clearWallsButton = new JButton("Clear walls");
        topSettingsPanel.add(algorithmCombo);
        topSettingsPanel.add(heuristicsCombo);
        topSettingsPanel.add(searchButton);
        topSettingsPanel.add(clearWallsButton);
        GridPanel gridPanel = new GridPanel(25, 25);
        contentPane.add(settingsPanel, BorderLayout.NORTH);
        contentPane.add(gridPanel, BorderLayout.CENTER);

        searchButton.addActionListener(e -> {
            final Graph g = gridPanel.getGraph();
            g.resetStates();
            final Tile start = gridPanel.getStart(), end = gridPanel.getEnd();
            final Algorithm algorithm = (Algorithm) algorithmCombo.getSelectedItem();
            g.setHeuristics((Heuristics) heuristicsCombo.getSelectedItem());
            final Thread thread = new Thread(() -> {
                final java.util.List<Node> path = algorithm.getAlgorithm().findPath(g, g.getNodeAt(start.getX(), start.getY()), g.getNodeAt(end.getX(), end.getY()));
                gridPanel.setPath(path);
            });
            thread.start();
        });
        clearWallsButton.addActionListener(e -> gridPanel.getGraph().resetFlags());
    }
}
