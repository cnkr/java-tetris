package com.jenkinson;

import java.util.Random;
import java.awt.*;
import java.util.Vector;

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
                blocks[1] = new Block(1, 0); //
                blocks[2] = new Block(2, 0);
                blocks[3] = new Block(3, 0);
                this.color = Color.BLUE;
                break;
            case J:
                blocks[0] = new Block(1, 1);
                blocks[1] = new Block(2, 1); //
                blocks[2] = new Block(3, 1);
                blocks[3] = new Block(3, 0);
                this.color = Color.PINK;
                break;
            case L:
                blocks[0] = new Block(1, 0);
                blocks[1] = new Block(2, 0); //
                blocks[2] = new Block(3, 0);
                blocks[3] = new Block(3, 1);
                this.color = Color.GREEN;
                break;
            case O:
                blocks[0] = new Block(2, 0);
                blocks[1] = new Block(2, 1); //
                blocks[2] = new Block(3, 1);
                blocks[3] = new Block(3, 0);
                this.color = Color.YELLOW;
                break;
            case S:
                blocks[0] = new Block(1, 0);
                blocks[1] = new Block(2, 0); //
                blocks[2] = new Block(2, 1);
                blocks[3] = new Block(3, 1);
                this.color = Color.CYAN;
                break;
            case T:
                blocks[0] = new Block(2, 0);
                blocks[1] = new Block(2, 1); //
                blocks[2] = new Block(2, 2);
                blocks[3] = new Block(3, 1);
                this.color = Color.ORANGE;
                break;
            case Z:
                blocks[0] = new Block(1, 1);
                blocks[1] = new Block(2, 1); //
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

    void rotate() {

        // Create 5x5 zero matrix
        Block[][] mat = new Block[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                mat[i][j] = null;
            }
        }

        // Place block idx 1 to mat[2][2]
        for (Block b : blocks) {
            int iOffset = b.i - blocks[1].i;
            int jOffset = b.j - blocks[1].j;
            mat[2 + iOffset][2 + jOffset] = b;
        }

        // Rotating right is effectively Transpose + Vertical mirroring

        Block temp;
        // Transpose matrix
        for (int i = 0; i < 5; i++) {
            for (int j = i; j < 5; j++) {
                temp = mat[i][j];
                mat[i][j] = mat[j][i];
                mat[j][i] = temp;
            }
        }

        // Flip matrix vertically
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4 - j; j++) {
                temp = mat[i][j];
                mat[i][j] = mat[i][4 - j];
                mat[i][4 - j] = temp;
            }
        }

        // Place back
        Block b;
        Block c = mat[2][2];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (mat[i][j] != null){
                    b = mat[i][j];
                    System.out.printf("B before (%d, %d)", b.i, b.j);
                    b.i = c.i + (i - 2);
                    b.j = c.j + (j - 2);
                    System.out.printf("B after (%d, %d)", b.i, b.j);
                }
            }
        }


    }
}
