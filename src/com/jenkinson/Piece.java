package com.jenkinson;

import java.util.Random;
import java.awt.*;

class Piece {
    /**
     * Represents one of 7 tetris pieces.
     */

    enum PieceType {
        I, J, L, O, S, T, Z
    }

    PieceType kind;
    Block[] blocks = new Block[4];
    Color color;

    GameScreen screen;
    Slot[][] blockGrid;

    Piece(GameScreen screen, boolean activePiece) {
        this.screen = screen;
        Random r = new Random();
        this.kind = PieceType.values()[r.nextInt(PieceType.values().length)];
        this.kind = PieceType.I;  //TODO Remove

        if (activePiece) {
            blockGrid = screen.grid;
        } else {
            //TODO
        }

        switch (this.kind) {
            case I:
                blocks[0] = new Block(0, 0);
                blocks[1] = new Block(1, 0);
                blocks[2] = new Block(2, 0);
                blocks[3] = new Block(3, 0);
                this.color = Color.BLUE;
                break;
        }

        for (Block block : blocks) {
            Slot slot = blockGrid[block.i][block.j];
            slot.solid = true;
            slot.color = color;
        }
    }

    void translate(int iOffset, int jOffset) {
        for (Block b : blocks) {
            blockGrid[b.i][b.j].solid = false;
            blockGrid[b.i][b.j].color = Slot.voidColor;
        }
        for (Block b : blocks) {
            b.i += iOffset;
            b.j += jOffset;
        }
        for (Block b : blocks) {
            blockGrid[b.i][b.j].solid = true;
            blockGrid[b.i][b.j].color = color;
        }
    }

    class Block {

        int i;
        int j;

        Block(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }
}
