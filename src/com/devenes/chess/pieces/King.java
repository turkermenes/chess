package com.devenes.chess.pieces;

import com.devenes.chess.Converter;
import com.devenes.chess.core.Core;
import com.devenes.chess.listeners.PieceActionListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class King extends JButton {

    //50 move rule
    public String color;
    public int x, y, row, column;
    boolean played = false;
    public Core core;

    public King(Core core, String color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.core = core;
        setText("");
        String iconfilePath;
        if (color.equals("white"))
            iconfilePath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("pieces/white_king.png")).getFile();
        else
            iconfilePath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("pieces/black_king.png")).getFile();

        ImageIcon originalIcon = new ImageIcon(iconfilePath);
        Image resizedImage = originalIcon.getImage().getScaledInstance(Core.size, Core.size, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        setIcon(resizedIcon);
        setLocation(x, y);
        setSize(Core.size, Core.size);
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        setFocusable(false);
        addActionListener(new PieceActionListener(this));
        setVisible(true);
    }

    public ArrayList<String> getPossibleMoves() {
        ArrayList<String> result = new ArrayList<>();
        //8 kareye hareket edebiliyor fakat bu karelere saldıran rakip taş olmaması gerekli.

        // White -> King: 1, Queen: 2, Rook: 3, Knight: 4, Bishop: 5, Pawn: 6
        // Black -> king: 7, Queen: 8, Rook: 9, Knight: 10, Bishop: 11, Pawn: 12
        int pawn = color.equals("white") ? 6 : 12;
        int bishop = color.equals("white") ? 5 : 11;
        int knight = color.equals("white") ? 4 : 10;
        int rook = color.equals("white") ? 3 : 9;
        int queen = color.equals("white") ? 2 : 8;
        int king = color.equals("white") ? 7 : 1;
        //top
        if (row - 1 >= 0) {
            int val = Core.board[row - 1][column];
            if (val != king && val != queen && val != rook && val != knight && val != bishop && val != pawn) {
                String s = (row - 1) + "," + column;
                result.add(s);
            }
        }

        //right
        if (column + 1 < 8) {
            int val = Core.board[row][column + 1];
            if (val != king && val != queen && val != rook && val != knight && val != bishop && val != pawn) {
                String s = row + "," + (column + 1);
                result.add(s);
            }
        }

        //left
        if (column - 1 >= 0) {
            int val = Core.board[row][column - 1];
            if (val != king && val != queen && val != rook && val != knight && val != bishop && val != pawn) {
                String s = row + "," + (column - 1);
                result.add(s);
            }
        }

        //bottom
        if (row + 1 < 8) {
            int val = Core.board[row + 1][column];
            if (val != king && val != queen && val != rook && val != knight && val != bishop && val != pawn) {
                String s = (row + 1) + "," + column;
                result.add(s);
            }
        }

        //top right
        if (row - 1 >= 0 && column + 1 < 8) {
            int val = Core.board[row - 1][column + 1];
            if (val != king && val != queen && val != rook && val != knight && val != bishop && val != pawn) {
                String s = (row - 1) + "," + (column + 1);
                result.add(s);
            }
        }

        //top left
        if (row - 1 >= 0 && column - 1 >= 0) {
            int val = Core.board[row - 1][column - 1];
            if (val != king && val != queen && val != rook && val != knight && val != bishop && val != pawn) {
                String s = (row - 1) + "," + (column - 1);
                result.add(s);
            }
        }

        //bottom right
        if (row + 1 < 8 && column + 1 < 8) {
            int val = Core.board[row + 1][column + 1];
            if (val != king && val != queen && val != rook && val != knight && val != bishop && val != pawn) {
                String s = (row + 1) + "," + (column + 1);
                result.add(s);
            }
        }

        //bottom left
        if (row + 1 < 8 && column - 1 >= 0) {
            int val = Core.board[row + 1][column - 1];
            if (val != king && val != queen && val != rook && val != knight && val != bishop && val != pawn) {
                String s = (row + 1) + "," + (column - 1);
                result.add(s);
            }
        }

        return result;
    }

    public String toString() {
        return getClass().getSimpleName() + "-" + color + "-" + Converter.rowColumnToSquare(row, column);
    }
}
