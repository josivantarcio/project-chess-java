package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

/**
 * Representa uma peça Rei no jogo de xadrez.
 */
public class King extends ChessPiece {

    private ChessMatch chessMatch;

    /**
     * Constrói um Rei com um tabuleiro, uma cor específica e uma partida de xadrez associada.
     * 
     * @param board O tabuleiro onde a peça será colocada.
     * @param color A cor da peça (Branco ou Preto).
     * @param chessMatch A partida de xadrez associada.
     */
    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
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
     * Verifica se é possível fazer o movimento de roque com a torre na posição especificada.
     * 
     * @param position A posição da torre para verificar o movimento de roque.
     * @return true se o movimento de roque é possível, false caso contrário.
     */
    private boolean testRookCastling(Position position) {
        ChessPiece cp = (ChessPiece) getBoard().piece(position);
        return cp != null && cp instanceof Rook && cp.getColor() == getColor() && cp.getMoveCount() == 0;
    }

    /**
     * Retorna uma representação em string da peça Rei.
     * 
     * @return Uma string contendo "K" para representar o Rei.
     */
    @Override
    public String toString() {
        return "K";
    }

    /**
     * Calcula os movimentos possíveis para o Rei e retorna uma matriz booleana indicando
     * onde os movimentos são permitidos no tabuleiro.
     * 
     * @return Uma matriz booleana onde cada posição true indica um movimento possível.
     */
    @Override
    public boolean[][] possibleMovies() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        // Movimento para cima
        p.setValues(position.getRow() - 1, position.getColumn());
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimento para baixo
        p.setValues(position.getRow() + 1, position.getColumn());
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimento para esquerda
        p.setValues(position.getRow(), position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimento para direita
        p.setValues(position.getRow(), position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimento para diagonal superior esquerda
        p.setValues(position.getRow() - 1, position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimento para diagonal superior direita
        p.setValues(position.getRow() - 1, position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimento para diagonal inferior esquerda
        p.setValues(position.getRow() + 1, position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimento para diagonal inferior direita
        p.setValues(position.getRow() + 1, position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        /**
         * Movimento especial: Roque
         */
        if (getMoveCount() == 0 && !chessMatch.getCheck()) {
            // Roque do lado do rei (roque curto)
            Position posTK = new Position(position.getRow(), position.getColumn() + 3);
            if (testRookCastling(posTK)) {
                Position p1 = new Position(position.getRow(), position.getColumn() + 1);
                Position p2 = new Position(position.getRow(), position.getColumn() + 2);
                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
                    mat[position.getRow()][position.getColumn() + 2] = true;
                }
            }
            // Roque do lado da rainha (roque longo)
            Position posTQ = new Position(position.getRow(), position.getColumn() - 4);
            if (testRookCastling(posTQ)) {
                Position p1 = new Position(position.getRow(), position.getColumn() - 1);
                Position p2 = new Position(position.getRow(), position.getColumn() - 2);
                Position p3 = new Position(position.getRow(), position.getColumn() - 3);
                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
                    mat[position.getRow()][position.getColumn() - 2] = true;
                }
            }
        }

        return mat;
    }

}
