package com.devenes.chess.pieces;

import com.devenes.chess.Converter;
import com.devenes.chess.core.Core;
import com.devenes.chess.listeners.PieceActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class Knight extends JButton implements Piece {

    public String color;
    public int x;
    public int y;
    public int row, column, index;
    public Core core;

    public Knight(Core core, String color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.core = core;
        setText("");
        String iconfilePath;
        if (color.equals("white"))
            iconfilePath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("pieces/white_knight.png")).getFile();
        else
            iconfilePath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("pieces/black_knight.png")).getFile();

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
        // White -> King: 1, Queen: 2, Rook: 3, Knight: 4, Bishop: 5, Pawn: 6
        // Black -> king: 7, Queen: 8, Rook: 9, Knight: 10, Bishop: 11, Pawn: 12
        ArrayList<String> result = new ArrayList<>();
        int pawn = color.equals("white") ? 6 : 12;
        int bishop = color.equals("white") ? 5 : 11;
        int knight = color.equals("white") ? 4 : 10;
        int rook = color.equals("white") ? 3 : 9;
        int queen = color.equals("white") ? 2 : 8;
        // square objesi gibi bir şey ile board oluşturarak square objesinde o karedeki taşın rengi ve tipi olsun. Aynı renk mi diye kolayca kontrol edilir.
        if (row + 1 < 8 && column - 2 >= 0) {
            int val = Core.board[row + 1][column - 2];
            if (val != 1 && val != 7 && val != pawn && val != bishop && val != knight && val != rook && val != queen){
                String s = (row + 1) + "," + (column - 2);
                result.add(s);
            }
        }
        if (row + 1 < 8 && column + 2 < 8) {
            int val = Core.board[row + 1][column + 2];
            if (val != 1 && val != 7 && val != pawn && val != bishop && val != knight && val != rook && val != queen){
                String s = (row + 1) + "," + (column + 2);
                result.add(s);
            }
        }
        if (row + 2 < 8 && column + 1 < 8) {
            int val = Core.board[row + 2][column + 1];
            if (val != 1 && val != 7 && val != pawn && val != bishop && val != knight && val != rook && val != queen){
                String s = (row + 2) + "," + (column + 1);
                result.add(s);
            }
        }
        if (row + 2 < 8 && column - 1 >= 0) {
            int val = Core.board[row + 2][column - 1];
            if (val != 1 && val != 7 && val != pawn && val != bishop && val != knight && val != rook && val != queen){
                String s = (row + 2) + "," + (column - 1);
                result.add(s);
            }
        }
        if (row - 1 >= 0 && column - 2 >= 0) {
            int val = Core.board[row - 1][column - 2];
            if (val != 1 && val != 7 && val != pawn && val != bishop && val != knight && val != rook && val != queen){
                String s = (row - 1) + "," + (column - 2);
                result.add(s);
            }
        }
        if (row - 1 >= 0 && column + 2 < 8) {
            int val = Core.board[row - 1][column + 2];
            if (val != 1 && val != 7 && val != pawn && val != bishop && val != knight && val != rook && val != queen){
                String s = (row - 1) + "," + (column + 2);
                result.add(s);
            }
        }
        if (row - 2 >= 0 && column + 1 < 8) {
            int val = Core.board[row - 2][column + 1];
            if (val != 1 && val != 7 && val != pawn && val != bishop && val != knight && val != rook && val != queen) {
                String s = (row - 2) + "," + (column + 1);
                result.add(s);
            }
        }
        if (row - 2 >= 0 && column - 1 >= 0) {
            int val = Core.board[row - 2][column - 1];
            if (val != 1 && val != 7 && val != pawn && val != bishop && val != knight && val != rook && val != queen) {
                String s = (row - 2) + "," + (column - 1);
                result.add(s);
            }
        }
        return result;
}

    public String toString() {
        return getClass().getSimpleName() + "-" + color + "-" + Converter.rowColumnToSquare(row, column);
    }
}
