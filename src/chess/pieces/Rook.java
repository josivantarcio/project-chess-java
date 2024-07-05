package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

/**
 * Representa uma peça Torre no jogo de xadrez.
 */
public class Rook extends ChessPiece {

    /**
     * Constrói uma Torre com um tabuleiro e uma cor específica.
     * 
     * @param board O tabuleiro onde a peça será colocada.
     * @param color A cor da peça (Branco ou Preto).
     */
    public Rook(Board board, Color color) {
        super(board, color);
    }

    /**
     * Retorna uma representação em string da Torre.
     * 
     * @return Uma string contendo "R" para representar a Torre.
     */
    @Override
    public String toString() {
        return "R";
    }

    /**
     * Calcula os movimentos possíveis para a Torre e retorna uma matriz booleana indicando
     * onde os movimentos são permitidos no tabuleiro.
     * 
     * @return Uma matriz booleana onde cada posição true indica um movimento possível.
     */
    @Override
    public boolean[][] possibleMovies() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        // Movimentos verticais e horizontais (cima, baixo, esquerda, direita)
        p.setValues(position.getRow() - 1, position.getColumn());
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow() - 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(position.getRow(), position.getColumn() - 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setColumn(p.getColumn() - 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(position.getRow(), position.getColumn() + 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setColumn(p.getColumn() + 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(position.getRow() + 1, position.getColumn());
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow() + 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        return mat;
    }
}
