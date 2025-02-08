package com.devenes.chess.pieces;

import com.devenes.chess.Converter;
import com.devenes.chess.core.Core;
import com.devenes.chess.gui.Components;
import com.devenes.chess.listeners.PieceActionListener;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Objects;

import static com.devenes.chess.core.Core.lastPlayedPiece;
import static com.devenes.chess.core.Core.selectedPiece;

public class Pawn extends JButton {
    //en passant, promote

    public String color;
    public int x, y, row, column;
    public boolean firstMove = true;
    public boolean playedTwoSquare = false;
    public Core core;
    public String promoteTo = null;

    public Pawn(Core core, String color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.core = core;
        setText("");
        String iconfilePath;
        if (color.equals("white"))
            iconfilePath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("pieces/white_pawn.png")).getFile();
        else
            iconfilePath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("pieces/black_pawn.png")).getFile();

        ImageIcon originalIcon = new ImageIcon(iconfilePath);
        Image resizedImage = originalIcon.getImage().getScaledInstance(Core.size, Core.size, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        setIcon(resizedIcon);
        setLocation(x, y);
        setSize(Core.size, Core.size);
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        setFocusable(false);
        addActionListener(new PieceActionListener( this));
        setVisible(true);
    }

    public ArrayList<String> getPossibleMoves() {
        ArrayList<String> result = new ArrayList<>();
        if (color.equals("white")) {
            // two step move
            if (firstMove) {
                if (Core.board[row - 2][column] == 0 && Core.board[row - 1][column] == 0) {
                    String s = (row - 2) + "," + column;
                    result.add(s);
                }
            }
            // one step move
            if (row - 1 >= 0 && Core.board[row - 1][column] == 0) {
                String s = (row - 1) + "," + column;
                result.add(s);
            }
            // White -> King: 1, Queen: 2, Rook: 3, Knight: 4, Bishop: 5, Pawn: 6
            // Black -> king: 7, Queen: 8, Rook: 9, Knight: 10, Bishop: 11, Pawn: 12

            // take left piece
            if (row - 1 >= 0 && column - 1 >= 0) {
                int val = Core.board[row - 1][column - 1];
                if (val == 8 || val == 9 || val == 10 || val == 11 || val == 12) {
                    String s = (row - 1) + "," + (column - 1);
                    result.add(s);
                }
            }
            //take right piece
            if (row - 1 >= 0 && column + 1 < 8) {
                int val = Core.board[row - 1][column + 1];
                if (val == 8 || val == 9 || val == 10 || val == 11 || val == 12) {
                    String s = (row - 1) + "," + (column + 1);
                    result.add(s);
                }
            }

        } else {
            // two step move
            if (firstMove) {
                if (Core.board[row + 2][column] == 0 && Core.board[row + 1][column] == 0) {
                    String s = (row + 2) + "," + column;
                    result.add(s);
                }
            }
            // one step move
            if (row + 1 < 8 && Core.board[row + 1][column] == 0) {
                String s = (row + 1) + "," + column;
                result.add(s);
            }
            // take left piece
            if (row + 1 < 8 && column - 1 >= 0) {
                int val = Core.board[row + 1][column - 1];
                if (val == 2 || val == 3 || val == 4 || val == 5 || val == 6) {
                    String s = (row + 1) + "," + (column - 1);
                    result.add(s);
                }
            }
            // take right piece
            if (row + 1 < 8 && column + 1 < 8) {
                int val = Core.board[row + 1][column + 1];
                if (val == 2 || val == 3 || val == 4 || val == 5 || val == 6) {
                    String s = (row + 1) + "," + (column + 1);
                    result.add(s);
                }
            }
        }
        //en passant
        if (Core.lastPlayedPiece instanceof Pawn pawn) {
            if (pawn.playedTwoSquare) {
                if (pawn.row == row && Math.abs(column - pawn.column) == 1 && Core.board[pawn.row - 1][pawn.column] == 0) {
                    int r = color.equals("white") ? row - 1 : row + 1;
                    String s = (r) + "," + (pawn.column);
                    result.add(s);
                }
            }
        }
        return result;
    }


    public void enPassant() {
        //x,y,setLocation,row, column
        for (Object o : Core.pieces) {
            if (o instanceof Pawn pawn && !pawn.color.equals(color) && pawn.playedTwoSquare && Core.lastPlayedPiece == pawn) {
                Core.board[pawn.row][pawn.column] = 0;
                Core.pieces.remove(pawn);
                int lastRow = color.equals("white") ? row - 1 : row + 1;
                x = Converter.columnToX(pawn.column);
                y = Converter.rowToY(lastRow);
                setLocation(x, y);
                row = lastRow;
                column = pawn.column;
                Core.board[row][column] = color.equals("white") ? 6 : 12;
                setContentAreaFilled(false);
                firstMove = false;
                Core.lastPlayedPiece = selectedPiece;
                Core.selectedPiece = null;
                Core.targetSquare = null;
                Core.targetPiece = null;
                Core.possibleMoves = null;
                Core.turn = Core.turn == 1 ? 2 : 1;

                if (core != null)
                    core.updateBoard();
                break;
            }
        }

    }

    public void promote() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (promoteTo != null) {
            Core.pieces.remove(this);
            if (Core.targetPiece != null) Core.pieces.remove(Core.targetPiece);
            Core.board[row][column] = 0;
            int r = color.equals("white") ? row - 1 : row + 1;
            int col = Core.targetPiece == null ? column : (Integer) Core.targetPiece.getClass().getField("column").get(Core.targetPiece);

            switch (promoteTo) {
                //lastplayedpiece pawn mı olmalı yoksa terfi edilen taş mı??
                case "queen":
                    Queen queen = new Queen(core, color, Converter.columnToX(col), Converter.rowToY(r));
                    lastPlayedPiece = queen;
                    queen.column = col;
                    queen.row = r;
                    Core.pieces.add(queen);
                    Core.board[queen.row][queen.column] = color.equals("white") ? 2 : 8;
                    break;
                case "knight":
                    Knight knight = new Knight(core, color, Converter.columnToX(col), Converter.rowToY(r));
                    knight.column = col;
                    knight.row = r;
                    lastPlayedPiece = knight;
                    Core.pieces.add(knight);
                    Core.board[knight.row][knight.column] = color.equals("white") ? 4 : 10;
                    break;
                case "rook":
                    Rook rook = new Rook(core, color, Converter.columnToX(col), Converter.rowToY(r));
                    rook.column = col;
                    rook.row = r;
                    lastPlayedPiece = rook;
                    Core.pieces.add(rook);
                    Core.board[rook.row][rook.column] = color.equals("white") ? 3 : 9;
                    break;
                case "bishop":
                    Bishop bishop = new Bishop(core, color, Converter.columnToX(col), Converter.rowToY(r));
                    bishop.column = col;
                    bishop.row = r;
                    lastPlayedPiece = bishop;
                    Core.pieces.add(bishop);
                    Core.board[bishop.row][bishop.column] = color.equals("white") ? 5 : 11;

            }
            for (Object p : Core.pieces) {
                int targetRow = (Integer) p.getClass().getField("row").get(p);
                int targetColumn = (Integer) p.getClass().getField("column").get(p);
                if (targetColumn == col && Math.abs(targetRow - r) <= 3) {
                    p.getClass().getMethod("setVisible", boolean.class).invoke(p, true);
                }
            }

        }
        Core.selectedPiece = null;
        Core.targetSquare = null;
        Core.targetPiece = null;
        Core.possibleMoves = null;
        Core.turn = Core.turn == 1 ? 2 : 1;
        if (core != null)
            core.updateBoard();
    }

    public String toString() {
        return getClass().getSimpleName() + "-" + color + "-" + Converter.rowColumnToSquare(row, column);
    }
}
