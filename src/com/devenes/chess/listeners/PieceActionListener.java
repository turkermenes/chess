package com.devenes.chess.listeners;

import com.devenes.chess.Converter;
import com.devenes.chess.core.Core;
import com.devenes.chess.core.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static com.devenes.chess.core.Core.*;

public class PieceActionListener implements ActionListener {

    Object object;

    public PieceActionListener(Object object) {
        this.object = object;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String color;
        int index;
        try {
            color = (String) object.getClass().getField("color").get(object);
            index = Core.pieces.indexOf(object);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        if (Core.selectedPiece == null) {
            if (Core.turn == 1 && color.equals("white") || Core.turn == 2 && color.equals("black")) {
                try {
                    Core.possibleMoves = (ArrayList<String>) object.getClass().getMethod("getPossibleMoves").invoke(object);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                Core.selectedPiece = Core.pieces.get(index);
                try {
                    //fill background
                    int sRow = (int) selectedPiece.getClass().getField("row").get(selectedPiece);
                    int sColumn = (int) selectedPiece.getClass().getField("column").get(selectedPiece);
                    if ((sRow + 1) % 2 == 0 && (sColumn + 1) % 2 == 0 || (sRow + 1) % 2 == 1 && (sColumn + 1) % 2 == 1)
                        selectedPiece.getClass().getMethod("setBackground", Color.class).invoke(selectedPiece, Color.decode("#F6EB72"));
                    else
                        selectedPiece.getClass().getMethod("setBackground", Color.class).invoke(selectedPiece, Color.decode("#DCC34B"));
                    selectedPiece.getClass().getMethod("setContentAreaFilled", boolean.class).invoke(selectedPiece, true);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else {
            Core.targetPiece = Core.pieces.get(index);
            if (selectedPiece.equals(targetPiece)) {
                try {
                    selectedPiece.getClass().getMethod("setContentAreaFilled", boolean.class).invoke(selectedPiece, false);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                Core.selectedPiece = null;
                Core.targetSquare = null;
                Core.targetPiece = null;
                Core.possibleMoves = null;
                return;
            }
            int targetX, targetY;
            try {
                targetX = (int) Core.targetPiece.getClass().getField("x").get(Core.targetPiece);
                targetY = (int) Core.targetPiece.getClass().getField("y").get(Core.targetPiece);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

            // x ve y'nin yerleri niye ters?
            String targetSquare = Converter.xToColumn(targetY) + "," + Converter.yToRow(targetX);
            System.out.println(targetSquare);
            if (Core.possibleMoves.contains(targetSquare)) {
                System.out.println(possibleMoves);
                try {
                    new Game().makeMove();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

    }
}

