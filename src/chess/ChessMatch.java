package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.exception.ChessException;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

/**
 * Represents a chess match with its rules and game state.
 */
public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassant;
	private ChessPiece promoted;

	private List<Piece> piecesOntheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();

	/**
	 * Constructs a new chess match with an 8x8 board and initializes the game
	 * state.
	 */
	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		check = false;
		currentPlayer = Color.WHITE;
		initialSetup();
	}

	/**
	 * Retrieves the current turn number.
	 *
	 * @return The current turn number.
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * Retrieves the current player's color.
	 *
	 * @return The color of the current player.
	 */
	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Checks if the current player is in check.
	 *
	 * @return true if the current player is in check, false otherwise.
	 */
	public boolean getCheck() {
		return check;
	}

	/**
	 * Checks if the game is in checkmate.
	 *
	 * @return true if the game is in checkmate, false otherwise.
	 */
	public boolean getCheckMate() {
		return checkMate;
	}

	/**
	 * Retrieves the en passant pawn, if any.
	 *
	 * @return The en passant pawn, or null if there is none.
	 */
	public ChessPiece getEnPassant() {
		return enPassant;
	}

	/**
	 * Retrieves the promoted pawn, if any.
	 *
	 * @return The promoted pawn, or null if there is none.
	 */
	public ChessPiece getPromoted() {
		return promoted;
	}

	/**
	 * Retrieves a matrix representation of all chess pieces on the board.
	 *
	 * @return A 2D array of ChessPiece objects representing the board state.
	 */
	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}

	/**
	 * Retrieves possible moves for a piece at a given source position.
	 *
	 * @param sourcePosition The source position of the piece.
	 * @return A boolean matrix indicating possible moves for the piece.
	 */
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPositioin();
		validateSourcePosition(position);
		return board.piece(position).possibleMovies();
	}

	/**
	 * Performs a chess move from a source position to a target position.
	 *
	 * @param sourcePosition The source position of the move.
	 * @param targetPosition The target position of the move.
	 * @return The captured piece, if any.
	 */
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPositioin();
		Position target = targetPosition.toPositioin();
		validateSourcePosition(source);
		validateTargetposition(source, target);
		Piece capturePiece = makeMove(source, target);

		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturePiece);
			throw new ChessException("You can't put yourself in check");
		}

		ChessPiece movedPiece = (ChessPiece) board.piece(target);

		// Check for pawn promotion
		promoted = null;
		if (movedPiece instanceof Pawn) {
			if (movedPiece.getColor() == Color.WHITE && target.getRow() == 0
					|| movedPiece.getColor() == Color.BLACK && target.getRow() == 7) {
				promoted = (ChessPiece) board.piece(target);
				promoted = replacePromotedPiece("Q");
			}
		}

		check = testCheck(opponent(currentPlayer));

		if (testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		} else {
			nextTurn();
		}

		// Check for en passant
		if (movedPiece instanceof Pawn
				&& (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
			enPassant = movedPiece;
		} else {
			enPassant = null;
		}
		return (ChessPiece) capturePiece;
	}

	/**
	 * Replaces a promoted pawn with a new piece of the specified type.
	 *
	 * @param type The type of piece to promote to ("B", "N", "R", "Q").
	 * @return The new promoted piece.
	 */
	public ChessPiece replacePromotedPiece(String type) {
		if (promoted == null) {
			throw new IllegalStateException("There is no piece to be promoted");
		}
		if (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
			return promoted;
		}
		Position pos = promoted.getChessPosition().toPositioin();
		Piece p = board.removePiece(pos);
		piecesOntheBoard.remove(p);

		ChessPiece newPiece = newPiece(type, promoted.getColor());
		board.placePiece(newPiece, pos);
		piecesOntheBoard.add(newPiece);

		return newPiece;
	}

	private ChessPiece newPiece(String type, Color color) {
		if (type.equals("B"))
			return new Bishop(board, color);
		if (type.equals("N"))
			return new Knight(board, color);
		if (type.equals("Q"))
			return new Queen(board, color);
		if (type.equals("R"))
			return new Rook(board, color);
		return null;
	}

	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece) board.removePiece(source);
		p.increaseMoveCount();
		Piece capturePiece = board.removePiece(target);
		board.placePiece(p, target);

		if (capturePiece != null) {
			piecesOntheBoard.remove(capturePiece);
			capturedPieces.add(capturePiece);
		}

		// Check for castling (kingside)
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}

		// Check for castling (queenside)
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}

		// Check for en passant
		if (p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturePiece == null) {
				Position pawnPosition;
				if (p.getColor() == Color.WHITE) {
					pawnPosition = new Position(target.getRow() + 1, target.getColumn());
				} else {
					pawnPosition = new Position(target.getRow() - 1, target.getColumn());
				}
				capturePiece = board.removePiece(pawnPosition);
				capturedPieces.add(capturePiece);
				piecesOntheBoard.remove(capturePiece);
			}
		}

		return capturePiece;
	}

	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece) board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);

		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOntheBoard.add(capturedPiece);
		}

		// Undo castling (kingside)
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(target.getRow(), target.getColumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}

		// Undo castling (queenside)
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(target.getRow(), target.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}

		// Undo en passant
		if (p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == null) {
				ChessPiece pawn = (ChessPiece) board.removePiece(target);
				Position pawnPosition;
				if (p.getColor() == Color.WHITE) {
					pawnPosition = new Position(3, target.getColumn());
				} else {
					pawnPosition = new Position(4, target.getColumn());
				}
				board.placePiece(pawn, pawnPosition);
			}
		}
	}

	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on the source position");
		}
		boolean isYourPiece = (currentPlayer != ((ChessPiece) board.piece(position)).getColor());
		if (isYourPiece) {
			throw new ChessException("The chosen piece isn't yours");
		}
		if (!board.piece(position).isthereAnyPossibleMove()) {
			throw new ChessException("There are no possible moves for the chosen piece");
		}
	}

	private void validateTargetposition(Position source, Position target) {
		if (!board.piece(source).possibleMovie(target)) {
			throw new ChessException("The chosen piece can't move to the target position");
		}
	}

	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPositioin());
		piecesOntheBoard.add(piece);
	}

	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private ChessPiece king(Color color) {
		List<Piece> list = piecesOntheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());

		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece) p;
			}
		}
		throw new IllegalStateException("There is no " + color + " king on the board");
	}

	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPositioin();
		List<Piece> opponentPieces = piecesOntheBoard.stream()
				.filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMovies();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) {
			return false;
		}
		List<Piece> list = piecesOntheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece p : list) {
			boolean[][] mat = p.possibleMovies();
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {
					if (mat[i][j]) {
						Position source = ((ChessPiece) p).getChessPosition().toPositioin();
						Position target = new Position(i, j);
						Piece capturePiece = makeMove(source, target);
						boolean inCheck = testCheck(color);
						undoMove(source, target, capturePiece);
						if (!inCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	/**
	 * Sets up the initial chess board with pieces placed in their starting
	 * positions.
	 */
	private void initialSetup() {
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('d', 1, new Queen(board, Color.WHITE));
		placeNewPiece('e', 1, new King(board, Color.WHITE, this));
		placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('g', 1, new Knight(board, Color.WHITE));
		placeNewPiece('h', 1, new Rook(board, Color.WHITE));
		placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

		placeNewPiece('a', 8, new Rook(board, Color.BLACK));
		placeNewPiece('b', 8, new Knight(board, Color.BLACK));
		placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('d', 8, new Queen(board, Color.BLACK));
		placeNewPiece('e', 8, new King(board, Color.BLACK, this));
		placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('g', 8, new Knight(board, Color.BLACK));
		placeNewPiece('h', 8, new Rook(board, Color.BLACK));
		placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
	}
}
