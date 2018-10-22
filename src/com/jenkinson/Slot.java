package com.jenkinson;

import javax.swing.*;
import java.awt.*;

class Slot {
    /**
     * Represents one of 22 x 10 blockGrid on the tetris canvas.
     */

    final int i;
    final int j;

    static Color voidColor = Color.LIGHT_GRAY;
    Color color = Color.LIGHT_GRAY;

    boolean solid = false;

    Slot(int i, int j){
        this.i = i;
        this.j = j;
    }


}
