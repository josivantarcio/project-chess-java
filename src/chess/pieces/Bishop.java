package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

/**
 * Representa uma peça Bispo no jogo de xadrez.
 */
public class Bishop extends ChessPiece {

    /**
     * Constrói um Bispo com um tabuleiro e uma cor específica.
     * 
     * @param board O tabuleiro onde a peça será colocada.
     * @param color A cor da peça (Branco ou Preto).
     */
    public Bishop(Board board, Color color) {
        super(board, color);
    }

    /**
     * Retorna uma representação em string da peça Bispo.
     * 
     * @return Uma string contendo "B" para representar o Bispo.
     */
    @Override
    public String toString() {
        return "B";
    }

    /**
     * Calcula os movimentos possíveis para o Bispo e retorna uma matriz booleana indicando
     * onde os movimentos são permitidos no tabuleiro.
     * 
     * @return Uma matriz booleana onde cada posição true indica um movimento possível.
     */
    @Override
    public boolean[][] possibleMovies() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        // Movimento para cima e à esquerda (diagonal)
        p.setValues(position.getRow() - 1, position.getColumn() - 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() - 1, p.getColumn() - 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimento para cima e à direita (diagonal)
        p.setValues(position.getRow() - 1, position.getColumn() + 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() - 1, p.getColumn() + 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimento para baixo e à direita (diagonal)
        p.setValues(position.getRow() + 1, position.getColumn() + 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() + 1, p.getColumn() + 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimento para baixo e à esquerda (diagonal)
        p.setValues(position.getRow() + 1, position.getColumn() - 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() + 1, p.getColumn() - 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        return mat;
    }
}
