package chess;

import boardgame.Position;
import chess.exception.ChessException;

/**
 * Representa uma posição no jogo de xadrez com colunas ('a' a 'h') e linhas (1 a 8).
 */
public class ChessPosition {

    private char columns;
    private int rows;
    
    /**
     * Constrói uma posição de xadrez com coluna e linha especificadas.
     * 
     * @param columns A coluna ('a' a 'h').
     * @param rows A linha (1 a 8).
     * @throws ChessException Se os valores de coluna ou linha estiverem fora do intervalo válido.
     */
    public ChessPosition(char columns, int rows) {
        if(columns < 'a' || columns > 'h' || rows < 1 || rows > 8) {
            throw new ChessException("Erro ao instanciar ChessPosition. Valores válidos são de 'a1' a 'h8'");
        }
        this.columns = columns;
        this.rows = rows;
    }

    /**
     * Obtém a coluna da posição de xadrez.
     * 
     * @return A coluna ('a' a 'h').
     */
    public char getColumns() {
        return columns;
    }

    /**
     * Obtém a linha da posição de xadrez.
     * 
     * @return A linha (1 a 8).
     */
    public int getRows() {
        return rows;
    }
    
    /**
     * Converte a posição de xadrez para uma posição padrão de tabuleiro.
     * 
     * @return O objeto Position correspondente em um tabuleiro padrão.
     */
    protected Position toPositioin() {
        return new Position(8 - rows, columns - 'a');
    }
    
    /**
     * Converte um objeto Position para um ChessPosition.
     * 
     * @param position O objeto Position a ser convertido.
     * @return O objeto ChessPosition correspondente.
     */
    protected static ChessPosition fromPosition(Position position) {
        char col = (char)('a' + position.getColumn());
        char row = (char)(8 - position.getRow());
        return new ChessPosition(col, row);
    }

    /**
     * Retorna uma representação em string da posição de xadrez.
     * 
     * @return Uma representação em string no formato "coluna + linha" (por exemplo, "a1").
     */
    @Override
    public String toString() {
        return "" + columns + rows;
    }
    
    
}
