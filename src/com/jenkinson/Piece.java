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

    boolean moveAllowed = true;

    Piece(GridContext cx) {

        this.cx = cx;
        Random r = new Random();
        this.kind = PieceType.values()[r.nextInt(PieceType.values().length)];

        switch (this.kind) {
            case I:
                blocks[0] = new Block(0, 0);
                blocks[1] = new Block(1, 0); // Pivot
                blocks[2] = new Block(2, 0);
                blocks[3] = new Block(3, 0);
                this.color = new Color(74, 120, 192);
                break;
            case J:
                blocks[0] = new Block(1, 1);
                blocks[1] = new Block(2, 1); // Pivot
                blocks[2] = new Block(3, 1);
                blocks[3] = new Block(3, 0);
                this.color = new Color(75, 182, 114);
                break;
            case L:
                blocks[0] = new Block(1, 0);
                blocks[1] = new Block(2, 0); // Pivot
                blocks[2] = new Block(3, 0);
                blocks[3] = new Block(3, 1);
                this.color = new Color(183, 102, 175);
                break;
            case O:
                blocks[0] = new Block(2, 0);
                blocks[1] = new Block(2, 1); // Pivot
                blocks[2] = new Block(3, 1);
                blocks[3] = new Block(3, 0);
                this.color = new Color(230, 215, 48);
                break;
            case S:
                blocks[0] = new Block(1, 0);
                blocks[1] = new Block(2, 0); // Pivot
                blocks[2] = new Block(2, 1);
                blocks[3] = new Block(3, 1);
                this.color = new Color(90, 200, 203);
                break;
            case T:
                blocks[0] = new Block(1, 0);
                blocks[1] = new Block(2, 0); // Pivot
                blocks[2] = new Block(3, 0);
                blocks[3] = new Block(2, 1);
                this.color = new Color(230, 151, 74);
                break;
            case Z:
                blocks[0] = new Block(1, 1);
                blocks[1] = new Block(2, 1); // Pivot
                blocks[2] = new Block(2, 0);
                blocks[3] = new Block(3, 0);
                this.color = new Color(0xAA, 0x39, 0x39);
                break;

        }

        for (Block block : blocks) {
            Slot slot = cx.grid[block.i][block.j];
            slot.fill(color);
        }

    }

    private void purgeSelfBlocks() {
        for (Block b : blocks) {
            cx.grid[b.i][b.j].purge();
        }
    }

    boolean translate(int iOffset, int jOffset) {
        if (!moveAllowed)
            return false;

        // First, check if target slots are available
        boolean badSlotDetected = false;

        // Empty current slots
        purgeSelfBlocks();

        // Check new slots
        for (Block b : blocks) {
            try {
                if (cx.grid[b.i + iOffset][b.j + jOffset].solid) {
                    badSlotDetected = true;
                    break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                badSlotDetected = true;
            }
        }

        // If problem detected, undo. Else, proceed.
        if (badSlotDetected) {
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

        boolean success = !badSlotDetected;
        return success;
    }

    private void rotateHelper(boolean isRightRotate) {

        // Create 5x5 zero matrix
        Block[][] mat = new Block[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                mat[i][j] = null;
            }
        }

        // Place block idx 1 to mat[2][2] and others relative to it
        for (Block b : blocks) {
            int iOffset = b.i - blocks[1].i;
            int jOffset = b.j - blocks[1].j;
            mat[2 + iOffset][2 + jOffset] = b;
        }

        // Rotating right is effectively Transpose + Vertical mirroring
        // Rotating left is effectively Vertical mirroring + Transpose

        if (isRightRotate) {
            transpose(mat);
            verticalFlip(mat);
        } else {
            verticalFlip(mat);
            transpose(mat);
        }

        // Place back
        Block b;
        Block c = mat[2][2];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (mat[i][j] != null) {
                    b = mat[i][j];
                    b.i = c.i + (i - 2);
                    b.j = c.j + (j - 2);
                }
            }
        }
    }

    boolean rotate(boolean isRightRotate) {
        if (!moveAllowed)
            return false;

        // Check square piece exception
        if (kind == PieceType.O)
            return false;

        // First, check if target slots are available
        boolean badSlotDetected = false;

        purgeSelfBlocks();

        Block[] backup = new Block[4];
        for (int k = 0; k < blocks.length; k++) {
            backup[k] = new Block(blocks[k].i, blocks[k].j);
        }

        rotateHelper(isRightRotate);

        // Check new slots
        for (Block b : blocks) {
            try {
                if (cx.grid[b.i][b.j].solid) {
                    badSlotDetected = true;
                    break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                badSlotDetected = true;
            }
        }

        // If problem detected, fall back to backup.
        if (badSlotDetected) {
            blocks = backup;
        }

        // Fill new (or backed up) blocks
        for (Block b : blocks) {
            cx.grid[b.i][b.j].fill(color);
        }

        return !badSlotDetected;
    }

    private void transpose(Block[][] mat) {
        Block temp;

        // Transpose matrix
        for (int i = 0; i < 5; i++) {
            for (int j = i; j < 5; j++) {
                temp = mat[i][j];
                mat[i][j] = mat[j][i];
                mat[j][i] = temp;
            }
        }
    }

    private void verticalFlip(Block[][] mat) {
        Block temp;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4 - j; j++) {
                temp = mat[i][j];
                mat[i][j] = mat[i][4 - j];
                mat[i][4 - j] = temp;
            }
        }
    }
}
