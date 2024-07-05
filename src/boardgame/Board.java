package boardgame;

import boardgame.exception.BoardException;

/**
 * A classe Board representa um tabuleiro genérico que pode conter peças.
 * 
 * @author Josevan Oliveira
 */
public class Board {

    private int rows;
    private int columns;
    private Piece[][] pieces;

    /**
     * Construtor que inicializa o tabuleiro com o número de linhas e colunas especificado.
     * 
     * @param rows número de linhas do tabuleiro.
     * @param columns número de colunas do tabuleiro.
     * @throws BoardException se o número de linhas ou colunas for menor que 1.
     */
    public Board(int rows, int columns) {
        if (rows < 1 || columns < 1) {
            throw new BoardException("Error creating board: there must be at least 1 row and 1 column");
        }
        this.rows = rows;
        this.columns = columns;
        this.pieces = new Piece[rows][columns];
    }

    /**
     * Retorna o número de linhas do tabuleiro.
     * 
     * @return número de linhas.
     */
    public int getRows() {
        return rows;
    }

    /**
     * Retorna o número de colunas do tabuleiro.
     * 
     * @return número de colunas.
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Retorna a peça na posição especificada.
     * 
     * @param row linha da posição desejada.
     * @param column coluna da posição desejada.
     * @return a peça na posição especificada.
     * @throws BoardException se a posição não existir no tabuleiro.
     */
    public Piece piece(int row, int column) {
        if (!positionExists(row, column)) {
            throw new BoardException("Position not on the board!");
        }
        return pieces[row][column];
    }

    /**
     * Retorna a peça na posição especificada.
     * 
     * @param position posição desejada.
     * @return a peça na posição especificada.
     * @throws BoardException se a posição não existir no tabuleiro.
     */
    public Piece piece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Position not on the board!");
        }
        return pieces[position.getRow()][position.getColumn()];
    }

    /**
     * Coloca uma peça na posição especificada do tabuleiro.
     * 
     * @param piece peça a ser colocada.
     * @param position posição onde a peça será colocada.
     * @throws BoardException se já houver uma peça na posição especificada.
     */
    public void placePiece(Piece piece, Position position) {
        if (thereIsAPiece(position)) {
            throw new BoardException("There is already a piece on position " + position);
        }
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }

    /**
     * Remove e retorna a peça na posição especificada do tabuleiro.
     * 
     * @param position posição da peça a ser removida.
     * @return a peça removida, ou null se não houver peça na posição especificada.
     * @throws BoardException se a posição não existir no tabuleiro.
     */
    public Piece removePiece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Position not on the board!");
        }
        if (piece(position) == null) {
            return null;
        }

        Piece aux = piece(position);
        aux.position = null;
        pieces[position.getRow()][position.getColumn()] = null;
        return aux;
    }

    /**
     * Verifica se uma posição está dentro dos limites do tabuleiro.
     * 
     * @param row linha da posição.
     * @param column coluna da posição.
     * @return true se a posição existe no tabuleiro, false caso contrário.
     */
    private boolean positionExists(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    /**
     * Verifica se uma posição está dentro dos limites do tabuleiro.
     * 
     * @param position posição a ser verificada.
     * @return true se a posição existe no tabuleiro, false caso contrário.
     */
    public boolean positionExists(Position position) {
        return positionExists(position.getRow(), position.getColumn());
    }

    /**
     * Verifica se há uma peça na posição especificada do tabuleiro.
     * 
     * @param position posição a ser verificada.
     * @return true se houver uma peça na posição especificada, false caso contrário.
     * @throws BoardException se a posição não existir no tabuleiro.
     */
    public boolean thereIsAPiece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Position not on the board!");
        }
        return piece(position) != null;
    }
}
