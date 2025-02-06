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
    Core core;

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
        addActionListener(new PieceActionListener(core,this));
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
    public void makeMove() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Map<Class<?>, Integer> pieceValues = Map.of(
                Pawn.class, 6,
                Bishop.class, 5,
                Knight.class, 4,
                Rook.class, 3,
                Queen.class, 2,
                King.class, 1
        );

        if (Core.selectedPiece != null && Core.targetSquare != null) {
            // Move to an empty square
            Object selectedP = Core.selectedPiece;
            int selectedPieceRow = (int) selectedP.getClass().getField("row").get(selectedP);
            int selectedPieceColumn = (int) selectedP.getClass().getField("column").get(selectedP);
            Core.board[selectedPieceRow][selectedPieceColumn] = 0;
            String[] split = Core.targetSquare.split(",");
            int x = Converter.columnToX(Integer.parseInt(split[1]));
            int y = Converter.rowToY(Integer.parseInt(split[0]));
            selectedP.getClass().getField("x").set(selectedP, x);
            selectedP.getClass().getField("y").set(selectedP, y);
            selectedP.getClass().getMethod("setLocation", int.class, int.class).invoke(selectedP, x, y);
            selectedP.getClass().getField("row").set(selectedP, Converter.yToRow(y));
            selectedP.getClass().getField("column").set(selectedP, Converter.xToColumn(x));
            int row = (int) selectedP.getClass().getField("row").get(selectedP);
            int column = (int) selectedP.getClass().getField("column").get(selectedP);

            int baseValue = pieceValues.getOrDefault(selectedP.getClass(), 0);
            String color = (String) selectedP.getClass().getField("color").get(selectedP);
            Core.board[row][column] = color.equals("white") ? baseValue : baseValue + 6;

        } else if (Core.selectedPiece != null && Core.targetPiece != null) {
            // Taking a piece
            Object targetP = Core.targetPiece;
            int targetX = (int) targetP.getClass().getField("x").get(targetP);
            int targetY = (int) targetP.getClass().getField("y").get(targetP);
            int targetRow = (int) targetP.getClass().getField("row").get(targetP);
            int targetColumn = (int) targetP.getClass().getField("column").get(targetP);
            Core.pieces.remove(targetP);

            Object selectedP = Core.selectedPiece;
            int selectedPieceRow = (int) selectedP.getClass().getField("row").get(selectedP);
            int selectedPieceColumn = (int) selectedP.getClass().getField("column").get(selectedP);
            Core.board[selectedPieceRow][selectedPieceColumn] = 0;
            selectedP.getClass().getField("x").set(selectedP, targetX);
            selectedP.getClass().getField("y").set(selectedP, targetY);
            selectedP.getClass().getField("row").set(selectedP, targetRow);
            selectedP.getClass().getField("column").set(selectedP, targetColumn);
            selectedP.getClass().getMethod("setLocation", int.class, int.class).invoke(selectedP, targetX, targetY);

            int baseValue = pieceValues.getOrDefault(selectedP.getClass(), 0);
            String color = (String) selectedP.getClass().getField("color").get(selectedP);
            Core.board[targetRow][targetColumn] = color.equals("white") ? baseValue : baseValue + 6;

        } else return;

        Core.selectedPiece = null;
        Core.targetSquare = null;
        Core.targetPiece = null;
        Core.turn = Core.turn == 1 ? 2 : 1;
        core.updateBoard();
    }

    public String toString() {
        return getClass().getSimpleName() + "-" + color + "-" + Converter.rowColumnToSquare(row, column);
    }
}
