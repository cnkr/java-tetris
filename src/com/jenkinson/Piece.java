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

    class Block {
        int i;
        int j;

        Block(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    PieceType kind;
    Block[] blocks = new Block[4];
    Color color;

    GridContext cx;

    Piece(GridContext cx) {

        this.cx = cx;
        Random r = new Random();
        this.kind = PieceType.values()[r.nextInt(PieceType.values().length)];
        //this.kind = PieceType.I;  //TODO Remove

        switch (this.kind) {
            case I:
                blocks[0] = new Block(0, 0);
                blocks[1] = new Block(1, 0);
                blocks[2] = new Block(2, 0);
                blocks[3] = new Block(3, 0);
                this.color = Color.BLUE;
                break;
            case J:
                blocks[0] = new Block(1, 1);
                blocks[1] = new Block(2, 1);
                blocks[2] = new Block(3, 1);
                blocks[3] = new Block(3, 0);
                this.color = Color.PINK;
                break;
            case L:
                blocks[0] = new Block(1, 0);
                blocks[1] = new Block(2, 0);
                blocks[2] = new Block(3, 0);
                blocks[3] = new Block(3, 1);
                this.color = Color.GREEN;
                break;
            case O:
                blocks[0] = new Block(2, 0);
                blocks[1] = new Block(2, 1);
                blocks[2] = new Block(3, 1);
                blocks[3] = new Block(3, 0);
                this.color = Color.YELLOW;
                break;
            case S:
                blocks[0] = new Block(1, 0);
                blocks[1] = new Block(2, 0);
                blocks[2] = new Block(2, 1);
                blocks[3] = new Block(3, 1);
                this.color = Color.CYAN;
                break;
            case T:
                blocks[0] = new Block(2, 0);
                blocks[1] = new Block(2, 1);
                blocks[2] = new Block(2, 2);
                blocks[3] = new Block(3, 1);
                this.color = Color.ORANGE;
                break;
            case Z:
                blocks[0] = new Block(1, 1);
                blocks[1] = new Block(2, 1);
                blocks[2] = new Block(2, 0);
                blocks[3] = new Block(3, 0);
                this.color = Color.RED;
                break;

        }

        for (Block block : blocks) {
            Slot slot = cx.grid[block.i][block.j];
            slot.fill(color);
        }
    }

    void translate(int iOffset, int jOffset) {

        // First, check if target slots are available
        boolean slotsAvailable = true;

        // Empty current slots
        for (Block b : blocks) {
            cx.grid[b.i][b.j].purge();
        }

        // Check new slots
        for (Block b : blocks) {
            try {
                if (cx.grid[b.i + iOffset][b.j + jOffset].solid) {
                    slotsAvailable = false;
                    break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                slotsAvailable = false;
            }
        }

        // If problem detected, undo. Else, proceed.
        if (!slotsAvailable) {
            for (Block b : blocks) {
                cx.grid[b.i][b.j].fill(color);
            }
        } else {
            for (Block b : blocks) {
                b.i += iOffset;
                b.j += jOffset;
            }
            for (Block b : blocks) {
                cx.grid[b.i][b.j].fill(color);
            }
        }
    }
}
