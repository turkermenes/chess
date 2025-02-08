package com.devenes.chess.gui;

import com.devenes.chess.Converter;
import com.devenes.chess.core.Core;
import com.devenes.chess.listeners.PromoteListener;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class Components {

    public JPanel promotePanel() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // butonlar dışında bir yere tıklanırsa hamle iptal olmalı

        String color = (String) Core.selectedPiece.getClass().getField("color").get(Core.selectedPiece);
        int pieceX = Core.targetPiece == null ? (Integer) Core.selectedPiece.getClass().getField("x").get(Core.selectedPiece) :
                (Integer) Core.targetPiece.getClass().getField("x").get(Core.targetPiece);
        int pieceRow = (Integer) Core.selectedPiece.getClass().getField("row").get(Core.selectedPiece);
        int panelY = color.equals("white") ? Converter.rowToY(pieceRow - 1) : Converter.rowToY(pieceRow - 2);
        int x = 0;
        int y = color.equals("white") ? 0 : 3 * Core.size;
        if (Core.targetPiece != null) {
            int targetRow = (Integer) Core.targetPiece.getClass().getField("row").get(Core.targetPiece);
            int targetColumn = (Integer) Core.targetPiece.getClass().getField("column").get(Core.targetPiece);

            for (Object p : Core.pieces) {
                int column = (Integer) p.getClass().getField("column").get(p);
                int row = (Integer) p.getClass().getField("row").get(p);
                if (targetColumn == column && Math.abs(targetRow - row) <= 3) {
                    p.getClass().getMethod("setVisible", boolean.class).invoke(p, false);
                }
            }
        }

        JButton queenButton = createPieceButton("queen", color, x, y);
        y = color.equals("white") ? y + Core.size : y - Core.size;
        JButton knightButton = createPieceButton("knight", color, x, y);
        y = color.equals("white") ? y + Core.size : y - Core.size;
        JButton rookButton = createPieceButton("rook", color, x, y);
        y = color.equals("white") ? y + Core.size : y - Core.size;
        JButton bishopButton = createPieceButton("bishop", color, x, y);
        JPanel panel = new JPanel();
        panel.setSize(Core.size, Core.size * 4);
        panel.setLocation(pieceX, panelY);
        panel.setLayout(null);
        panel.add(queenButton);
        panel.add(knightButton);
        panel.add(rookButton);
        panel.add(bishopButton);
        panel.setBackground(Color.white);
        panel.setVisible(true);

        return panel;
    }

    private JButton createPieceButton(String piece, String color, int x, int y) {
        JButton button = new JButton();
        button.setText("");
        String iconfilePath;
        if (color.equals("white"))
            iconfilePath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("pieces/white_" + piece + ".png")).getFile();
        else
            iconfilePath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("pieces/black_" + piece + ".png")).getFile();

        ImageIcon originalIcon = new ImageIcon(iconfilePath);
        Image resizedImage = originalIcon.getImage().getScaledInstance(Core.size, Core.size, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        button.setIcon(resizedIcon);
        button.setLocation(x, y);
        button.setSize(Core.size, Core.size);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        button.setFocusable(false);
        button.addActionListener(new PromoteListener(piece));
        button.setVisible(true);

        return button;
    }
}
