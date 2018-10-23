package com.jenkinson;

import java.awt.event.*;

public class GameKeyListener implements KeyListener {
    /**
     * Key listener for the Game Screen
     */

    GameScreen screen;

    GameKeyListener(GameScreen screen) {
        this.screen = screen;
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {

        Piece activePiece = screen.gameGridContext.piece;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                activePiece.translate(0, -1);
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                activePiece.translate(0, 1);
                break;
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                //activePiece.translate(-1, 0);
                activePiece.rotate(true);
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                activePiece.translate(1, 0);
                break;
            case KeyEvent.VK_ENTER:
                activePiece = screen.updatePieces();
                break;
            case KeyEvent.VK_SPACE:
                activePiece.rotate(true);
                break;
            case KeyEvent.VK_ESCAPE:
                screen.quitRequested = true;
                break;
        }

        screen.repaint();
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
