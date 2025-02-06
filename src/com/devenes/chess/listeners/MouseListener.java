package com.devenes.chess.listeners;

import com.devenes.chess.Converter;
import com.devenes.chess.core.Core;
import com.devenes.chess.core.Game;
import com.devenes.chess.pieces.*;

import java.awt.event.MouseEvent;

import static com.devenes.chess.core.Core.*;
import static com.devenes.chess.core.Core.targetSquare;

public class MouseListener implements java.awt.event.MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        //buradaydı mousePressed'e taşıdım xd
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (selectedPiece != null) {
            targetSquare = Converter.xToColumn(e.getY()) + "," + Converter.yToRow(e.getX());
            if (possibleMoves.contains(targetSquare)) {
                if (Core.selectedPiece instanceof Pawn pawn) {
                    pawn.makeMove();
                } else if (Core.selectedPiece instanceof Bishop bishop) {
                    bishop.makeMove();
                } else if (Core.selectedPiece instanceof Knight knight) {
                    knight.makeMove();
                } else if (Core.selectedPiece instanceof Rook rook) {
                    rook.makeMove();
                } else if (Core.selectedPiece instanceof Queen queen) {
                    queen.makeMove();
                } else if (Core.selectedPiece instanceof King king) {
                    king.makeMove();
                }
            } else {
                selectedPiece = null;
                targetSquare = null;
                targetPiece = null;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
