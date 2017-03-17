package com.artificial.pathfinding;

import com.artificial.pathfinding.ui.UserInterface;

import javax.swing.*;

public class ApplicationMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(UserInterface::new);
    }

}
