package boardgame;

/**
 * A classe abstrata Piece representa uma peça genérica de um jogo de tabuleiro.
 * Define métodos para obter o tabuleiro em que a peça está posicionada, verificar movimentos possíveis
 * e verificar se há algum movimento possível para a peça.
 * 
 * @author Josevan Oliveira
 */
public abstract class Piece {
    protected Position position;
    private Board board;

    /**
     * Construtor que associa a peça a um tabuleiro.
     * 
     * @param board o tabuleiro onde a peça será colocada.
     */
    public Piece(Board board) {
        this.board = board;
    }

    /**
     * Retorna o tabuleiro onde a peça está posicionada.
     * 
     * @return o tabuleiro onde a peça está posicionada.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Método abstrato que retorna uma matriz booleana indicando os movimentos possíveis da peça.
     * 
     * @return matriz booleana representando os movimentos possíveis da peça.
     */
    public abstract boolean[][] possibleMoves();

    /**
     * Verifica se um determinado movimento é possível para a peça.
     * 
     * @param position posição para verificar o movimento.
     * @return true se o movimento é possível, false caso contrário.
     */
    public boolean possibleMove(Position position) {
        return possibleMoves()[position.getRow()][position.getColumn()];
    }

    /**
     * Verifica se há algum movimento possível para a peça.
     * 
     * @return true se há pelo menos um movimento possível, false caso contrário.
     */
    public boolean isThereAnyPossibleMove() {
        boolean[][] mat = possibleMoves();
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                if (mat[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }
}
