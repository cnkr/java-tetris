package com.jenkinson;

import javax.swing.*;
import java.awt.*;

class Slot {
    /**
     * Represents one of 22 x 10 blockGrid on the tetris canvas.
     */

    final int i;
    final int j;

    private static Color voidColor = Color.LIGHT_GRAY;
    Color color = voidColor;

    boolean solid = false;

    Slot(int i, int j) {
        this.i = i;
        this.j = j;
    }

    void fill(Color color) {
        this.solid = true;
        this.color = color;
    }

    void purge() {
        this.solid = false;
        this.color = voidColor;
    }

}
