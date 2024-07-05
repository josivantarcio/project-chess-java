package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

/**
 * Representa uma peça Cavalo no jogo de xadrez.
 */
public class Knight extends ChessPiece {

    /**
     * Constrói um Cavalo com um tabuleiro e uma cor específica.
     * 
     * @param board O tabuleiro onde a peça será colocada.
     * @param color A cor da peça (Branco ou Preto).
     */
    public Knight(Board board, Color color) {
        super(board, color);
    }

    /**
     * Verifica se é possível mover para a posição especificada.
     * 
     * @param position A posição para verificar o movimento.
     * @return true se for possível mover para a posição, false caso contrário.
     */
    private boolean canMove(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piece(position);
        return p == null || p.getColor() != getColor();
    }

    /**
     * Retorna uma representação em string da peça Cavalo.
     * 
     * @return Uma string contendo "N" para representar o Cavalo.
     */
    @Override
    public String toString() {
        return "N";
    }

    /**
     * Calcula os movimentos possíveis para o Cavalo e retorna uma matriz booleana indicando
     * onde os movimentos são permitidos no tabuleiro.
     * 
     * @return Uma matriz booleana onde cada posição true indica um movimento possível.
     */
    @Override
    public boolean[][] possibleMovies() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        // Movimento em L para cima e para a esquerda
        p.setValues(position.getRow() - 1, position.getColumn() - 2);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimento em L para cima e para a direita
        p.setValues(position.getRow() - 2, position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimento em L para baixo e para a direita
        p.setValues(position.getRow() + 1, position.getColumn() + 2);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimento em L para baixo e para a esquerda
        p.setValues(position.getRow() + 2, position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimento em L para cima e para a direita
        p.setValues(position.getRow() - 1, position.getColumn() + 2);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimento em L para baixo e para a esquerda
        p.setValues(position.getRow() + 2, position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimento em L para baixo e para a direita
        p.setValues(position.getRow() + 1, position.getColumn() - 2);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimento em L para cima e para a esquerda
        p.setValues(position.getRow() - 2, position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        return mat;
    }

}
