package boardgame;

/**
 * Represents a position on a board.
 */
public class Position {
    
    private int row;
    private int column;
    
    /**
     * Constructor that initializes a position with specified row and column values.
     *
     * @param row    The row index of the position.
     * @param column The column index of the position.
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Retrieves the row index of the position.
     *
     * @return The row index.
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets the row index of the position.
     *
     * @param row The new row index to set.
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Retrieves the column index of the position.
     *
     * @return The column index.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Sets the column index of the position.
     *
     * @param column The new column index to set.
     */
    public void setColumn(int column) {
        this.column = column;
    }
    
    /**
     * Sets both row and column indices of the position.
     *
     * @param row    The new row index to set.
     * @param column The new column index to set.
     */
    public void setValues(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Returns a string representation of the position in the format "row, column".
     *
     * @return A string representation of the position.
     */
    @Override
    public String toString() {
        return row + ", " + column;
    }   
}
