package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

/**
 * Representa uma peça Peão no jogo de xadrez.
 */
public class Pawn extends ChessPiece {

    private ChessMatch chessMatch;

    /**
     * Constrói um Peão com um tabuleiro, uma cor específica e uma partida de xadrez associada.
     * 
     * @param board O tabuleiro onde a peça será colocada.
     * @param color A cor da peça (Branco ou Preto).
     * @param chessMatch A partida de xadrez à qual o Peão pertence.
     */
    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    /**
     * Calcula os movimentos possíveis para o Peão e retorna uma matriz booleana indicando
     * onde os movimentos são permitidos no tabuleiro.
     * 
     * @return Uma matriz booleana onde cada posição true indica um movimento possível.
     */
    @Override
    public boolean[][] possibleMovies() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        // Peça branca
        if (getColor() == Color.WHITE) {
            // Movimento para frente
            p.setValues(position.getRow() - 1, position.getColumn());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
            // Primeiro movimento especial de duas casas para frente
            p.setValues(position.getRow() - 2, position.getColumn());
            Position p2 = new Position(position.getRow() - 1, position.getColumn());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2)
                    && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
                mat[p.getRow()][p.getColumn()] = true;
            }
            // Captura na diagonal esquerda
            p.setValues(position.getRow() - 1, position.getColumn() - 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
            // Captura na diagonal direita
            p.setValues(position.getRow() - 1, position.getColumn() + 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            /*
             * Captura en passant para peças brancas
             */
            if (position.getRow() == 3) {
                Position left = new Position(position.getRow(), position.getColumn() - 1);
                if (getBoard().positionExists(left) && isThereOpponentPiece(left)
                        && getBoard().piece(left) == chessMatch.getEnPassant()) {
                    mat[left.getRow() - 1][left.getColumn()] = true;
                }
                Position right = new Position(position.getRow(), position.getColumn() + 1);
                if (getBoard().positionExists(right) && isThereOpponentPiece(right)
                        && getBoard().piece(right) == chessMatch.getEnPassant()) {
                    mat[right.getRow() - 1][right.getColumn()] = true;
                }
            }
        }
        // Peça preta
        if (getColor() == Color.BLACK) {
            // Movimento para frente
            p.setValues(position.getRow() + 1, position.getColumn());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
            // Primeiro movimento especial de duas casas para frente
            p.setValues(position.getRow() + 2, position.getColumn());
            Position p2 = new Position(position.getRow() + 1, position.getColumn());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2)
                    && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
                mat[p.getRow()][p.getColumn()] = true;
            }
            // Captura na diagonal direita
            p.setValues(position.getRow() + 1, position.getColumn() + 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
            // Captura na diagonal esquerda
            p.setValues(position.getRow() + 1, position.getColumn() - 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            /*
             * Captura en passant para peças pretas
             */
            if (position.getRow() == 4) {
                Position left = new Position(position.getRow(), position.getColumn() - 1);
                if (getBoard().positionExists(left) && isThereOpponentPiece(left)
                        && getBoard().piece(left) == chessMatch.getEnPassant()) {
                    mat[left.getRow() + 1][left.getColumn()] = true;
                }
                Position right = new Position(position.getRow(), position.getColumn() + 1);
                if (getBoard().positionExists(right) && isThereOpponentPiece(right)
                        && getBoard().piece(right) == chessMatch.getEnPassant()) {
                    mat[right.getRow() + 1][right.getColumn()] = true;
                }
            }
        }

        return mat;
    }

    /**
     * Retorna uma representação em string da peça Peão.
     * 
     * @return Uma string contendo "p" para representar o Peão.
     */
    @Override
    public String toString() {
        return "p";
    }
}
