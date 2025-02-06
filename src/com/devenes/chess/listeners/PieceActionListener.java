package com.devenes.chess.listeners;

import com.devenes.chess.core.Core;
import com.devenes.chess.core.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
            index = (int) object.getClass().getField("index").get(object);
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
            }
        } else {
            Core.targetPiece = Core.pieces.get(index);
            try {
                new Game().makeMove();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

    }
}

