package boardgame;

/**
 * Represents an abstract piece in a board game.
 */
public abstract class Piece {
    
    protected Position position;
    private Board board;
    
    /**
     * Constructor that initializes a piece with a board.
     *
     * @param board The board where the piece will be placed.
     */
    public Piece(Board board) {
        this.board = board;
    }

    /**
     * Retrieves the board where the piece is located.
     *
     * @return The board object.
     */
    public Board getBoard() {
        return board;
    }
    
    /**
     * Abstract method to be implemented by subclasses to determine possible moves of the piece.
     *
     * @return A boolean matrix indicating possible moves of the piece.
     */
    public abstract boolean[][] possibleMovies();
    
    /**
     * Checks if a specific position is a possible move for the piece.
     *
     * @param position The position to check.
     * @return True if the position is a possible move, false otherwise.
     */
    public boolean possibleMovie(Position position) {
        return possibleMovies()[position.getRow()][position.getColumn()];
    }
    
    /**
     * Checks if there is any possible move for the piece.
     *
     * @return True if there is at least one possible move, false otherwise.
     */
    public boolean isthereAnyPossibleMove() {
        boolean[][] mat = possibleMovies();
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                if(mat[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }
}
