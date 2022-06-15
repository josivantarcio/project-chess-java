package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.exception.ChessException;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;

	private List<Piece> piecesOntheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();

	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		check = false;
		currentPlayer = Color.WHITE;
		initialSetup();
	}

	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}

	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}

	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPositioin();
		validateSourcePosition(position);
		return board.piece(position).possibleMovies();
	}

	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPositioin();
		Position target = targetPosition.toPositioin();
		validateSourcePosition(source);
		validateTargetposition(source, target);
		Piece capturePiece = makeMove(source, target);
		
		if(testCheck(currentPlayer)) {
			undoMove(source, target, capturePiece);
			throw new ChessException("You can't put yourself check");
		}
		
		check = testCheck(opponent(currentPlayer)) ? true : false;
		
		nextTurn();
		return (ChessPiece) capturePiece;
	}

	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturePiece = board.removePiece(target);
		board.placePiece(p, target);

		if (capturePiece != null) {
			piecesOntheBoard.remove(capturePiece);
			capturedPieces.add(capturePiece);
		}
		return capturePiece;
	}

	private void undoMove(Position source, Position target, Piece capturedPiece) {
		Piece p = board.removePiece(target);
		board.placePiece(p, source);

		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOntheBoard.add(capturedPiece);
		}
	}

	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		boolean yourPiece = currentPlayer != ((ChessPiece) board.piece(position)).getColor();
		if (yourPiece) {
			throw new ChessException("The chosen piece isn't your");
		}
		if (!board.piece(position).isthereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves");
		}
	}

	private void validateTargetposition(Position source, Position target) {
		if (!board.piece(source).possibleMovie(target)) {
			throw new ChessException("The chosen piece don't move");
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
		List<Piece> list = piecesOntheBoard.stream()
				.filter(x -> ((ChessPiece) x).getColor() == color).collect(Collectors.toList());

		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece) p;
			}
		}
		throw new IllegalStateException("There isn't " + color + " king on the board");
	}

	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPositioin();
		List<Piece> opponentPieces = piecesOntheBoard.stream()
				.filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());
		for(Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMovies();
			if(mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}

	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private void initialSetup() {
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
//		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
//		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
//		placeNewPiece('d', 1, new Queen(board, Color.WHITE));
//		placeNewPiece('e', 1, new King(board, Color.WHITE, this));
//		placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
//		placeNewPiece('g', 1, new Knight(board, Color.WHITE));
		placeNewPiece('h', 1, new Rook(board, Color.WHITE));
//		placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
//		placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
//		placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
//		placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
//		placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
//		placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
//		placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
//		placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

		placeNewPiece('a', 8, new Rook(board, Color.BLACK));
//		placeNewPiece('b', 8, new Knight(board, Color.BLACK));
//		placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
//		placeNewPiece('d', 8, new Queen(board, Color.BLACK));
//		placeNewPiece('e', 8, new King(board, Color.BLACK, this));
//		placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
//		placeNewPiece('g', 8, new Knight(board, Color.BLACK));
		placeNewPiece('h', 8, new Rook(board, Color.BLACK));
//		placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
//		placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
//		placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
//		placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
//		placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
//		placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
//		placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
//		placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
	}
}
