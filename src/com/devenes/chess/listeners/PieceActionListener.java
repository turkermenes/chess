package com.devenes.chess.listeners;

import com.devenes.chess.core.Core;
import com.devenes.chess.core.Game;
import com.devenes.chess.pieces.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PieceActionListener implements ActionListener {

    Object object;
    Core core;

    public PieceActionListener(Core core, Object object) {
        this.object = object;
        this.core = core;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (object instanceof Pawn pawn) {
            if (Core.selectedPiece == null) {
                if (Core.turn == 1 && pawn.color.equals("white") || Core.turn == 2 && pawn.color.equals("black")) {
                    Core.possibleMoves = pawn.getPossibleMoves();
                    Core.selectedPiece = Core.pieces.get(pawn.index);
                }
            } else {
                Core.targetPiece = Core.pieces.get(pawn.index);
                core.makeMove();
            }
        } else if (object instanceof Bishop bishop) {
            if (Core.selectedPiece == null) {
                if (Core.turn == 1 && bishop.color.equals("white") || Core.turn == 2 && bishop.color.equals("black")) {
                    Core.possibleMoves = bishop.getPossibleMoves();
                    Core.selectedPiece = Core.pieces.get(bishop.index);
                }
            } else {
                Core.targetPiece = Core.pieces.get(bishop.index);

                core.makeMove();
            }

        } else if (object instanceof Knight knight) {
            if (Core.selectedPiece == null) {
                if (Core.turn == 1 && knight.color.equals("white") || Core.turn == 2 && knight.color.equals("black")) {
                    Core.possibleMoves = knight.getPossibleMoves();
                    Core.selectedPiece = Core.pieces.get(knight.index);
                }
            } else {
                Core.targetPiece = Core.pieces.get(knight.index);
                core.makeMove();
            }

        } else if (object instanceof Rook rook) {
            if (Core.selectedPiece == null) {
                if (Core.turn == 1 && rook.color.equals("white") || Core.turn == 2 && rook.color.equals("black")) {
                    Core.possibleMoves = rook.getPossibleMoves();
                    Core.selectedPiece = Core.pieces.get(rook.index);
                }
            } else {
                Core.targetPiece = Core.pieces.get(rook.index);
                core.makeMove();
            }

        } else if (object instanceof Queen queen) {
            if (Core.selectedPiece == null) {
                if (Core.turn == 1 && queen.color.equals("white") || Core.turn == 2 && queen.color.equals("black")) {
                    Core.possibleMoves = queen.getPossibleMoves();
                    Core.selectedPiece = Core.pieces.get(queen.index);
                }
            } else {
                Core.targetPiece = Core.pieces.get(queen.index);
                core.makeMove();
            }

        } else if (object instanceof King king) {
            if (Core.selectedPiece == null) {
                if (Core.turn == 1 && king.color.equals("white") || Core.turn == 2 && king.color.equals("black")) {
                    Core.possibleMoves = king.getPossibleMoves();
                    Core.selectedPiece = Core.pieces.get(king.index);
                }
            } else {
                Core.targetPiece = Core.pieces.get(king.index);
                core.makeMove();
            }
        }
    }
}

