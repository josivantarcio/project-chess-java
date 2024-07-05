package boardgame;

import boardgame.exception.BoardException;

/**
 * Represents a game board that holds pieces at specific positions.
 */
public class Board {

    private int rows;
    private int columns;
    private Piece[][] pieces;

    /**
     * Constructor that creates a board with a specified number of rows and columns.
     *
     * @param rows    The number of rows on the board.
     * @param columns The number of columns on the board.
     * @throws BoardException If the number of rows or columns is less than 1.
     */
    public Board(int rows, int columns) throws BoardException {
        if (rows < 1 || columns < 1) {
            throw new BoardException("Erro creating board: there must be at least 1 row and 1 column");
        }
        this.rows = rows;
        this.columns = columns;
        this.pieces = new Piece[rows][columns];
    }

    /**
     * Retrieves the number of rows on the board.
     *
     * @return The number of rows.
     */
    public int getRows() {
        return rows;
    }

    /**
     * Retrieves the number of columns on the board.
     *
     * @return The number of columns.
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Retrieves the piece at the specified row and column on the board.
     *
     * @param row    The row index of the piece.
     * @param column The column index of the piece.
     * @return The piece at the specified position.
     * @throws BoardException If the position does not exist on the board.
     */
    public Piece piece(int row, int column) throws BoardException {
        if (!positionExists(row, column)) {
            throw new BoardException("Position not on the board!");
        }
        return pieces[row][column];
    }

    /**
     * Retrieves the piece at the specified position on the board.
     *
     * @param position The position object containing the row and column indices.
     * @return The piece at the specified position.
     * @throws BoardException If the position does not exist on the board.
     */
    public Piece piece(Position position) throws BoardException {
        if (!positionExists(position)) {
            throw new BoardException("Position not on the board!");
        }
        return pieces[position.getRow()][position.getColumn()];
    }

    /**
     * Places a piece on the board at the specified position.
     *
     * @param piece    The piece to be placed on the board.
     * @param position The position where the piece should be placed.
     * @throws BoardException If there is already a piece at the specified position.
     */
    public void placePiece(Piece piece, Position position) throws BoardException {
        if (thereIsAPiece(position)) {
            throw new BoardException("There is already a piece on position " + position);
        }
        pieces[position.getRow()][position.getColumn()] = piece;
    }

    /**
     * Removes the piece from the board at the specified position.
     *
     * @param position The position from which the piece should be removed.
     * @return The piece that was removed, or null if no piece was present at the position.
     * @throws BoardException If the position does not exist on the board.
     */
    public Piece removePiece(Position position) throws BoardException {
        if (!positionExists(position)) {
            throw new BoardException("Position not on the board!");
        }
        if (piece(position) == null) {
            return null;
        }

        Piece removedPiece = piece(position);
        pieces[position.getRow()][position.getColumn()] = null;
        return removedPiece;
    }

    /**
     * Checks if a position exists on the board.
     *
     * @param row    The row index of the position.
     * @param column The column index of the position.
     * @return True if the position exists on the board, false otherwise.
     */
    private boolean positionExists(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    /**
     * Checks if a position exists on the board.
     *
     * @param position The position object containing the row and column indices.
     * @return True if the position exists on the board, false otherwise.
     */
    public boolean positionExists(Position position) {
        return positionExists(position.getRow(), position.getColumn());
    }

    /**
     * Checks if there is a piece at the specified position on the board.
     *
     * @param position The position object containing the row and column indices.
     * @return True if there is a piece at the specified position, false otherwise.
     * @throws BoardException If the position does not exist on the board.
     */
    public boolean thereIsAPiece(Position position) throws BoardException {
        if (!positionExists(position)) {
            throw new BoardException("Position not on the board!");
        }
        return piece(position) != null;
    }

}
