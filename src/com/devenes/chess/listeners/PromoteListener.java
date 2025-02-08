package com.devenes.chess.listeners;

import com.devenes.chess.core.Core;
import com.devenes.chess.pieces.Pawn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PromoteListener implements ActionListener {
    String piece;
    public PromoteListener(String piece) {
        this.piece = piece;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Core.selectedPiece instanceof Pawn pawn) {
            pawn.promoteTo = piece;
            try {
                pawn.promote();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
