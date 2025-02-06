package com.devenes.chess.listeners;

import com.devenes.chess.Converter;

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
                Object selectedP = selectedPiece;
                try {
                    selectedP.getClass().getMethod("makeMove").invoke(selectedP);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
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
