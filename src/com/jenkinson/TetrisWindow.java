package com.jenkinson;

import javax.swing.*;

public class TetrisWindow extends JFrame {
    /**
     * Application Window
     */

    Screen activeScreen;

    public static void main(String[] args) {
        TetrisWindow game = new TetrisWindow();
    }

    TetrisWindow() {

        setTitle("T E T R I S");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        activeScreen = new GameScreen();
        add(activeScreen);
        pack();
        setLocationRelativeTo(null);

        // Shady part: Think how to handle Key listener.
        addKeyListener(new GameKeyListener((GameScreen)activeScreen));
        setVisible(true);
    }


}