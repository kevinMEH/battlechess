package game;

import pieces.*;

import java.util.ArrayList;
import java.util.List;

public class Board {
    
    enum BoardType {
        REGULAR,
        MINI
    }
    
    private final Square[][] board;
    private final BoardType boardType;
    
    public Board(int x, int y, BoardType boardType) {
        this.boardType = boardType;
        board = new Square[x][y];
        fillBoard();
        spawnPieces();
    }
    
    private void fillBoard() { // Filling board with squares
        for(int x = 0; x < board.length; x++) {
            for(int y = 0; y < board[0].length; y++)
                board[x][y] = new Square(x, y);
        }
    }

    /*
    7
    6
    5
    4
    3
    2
    1
x   0   1   2   3   4   5   6   7
    y
     */

    /*
    5
    4
    3
    2
    1
x   0   1   2   3   4
    y
 */
    private final List<Piece> pieces = new ArrayList<>();
    private Piece blackKing;
    private Piece whiteKing;
    
    private void spawnPieces() {
        King whiteKing;
        King blackKing;
        switch(boardType) {
            case REGULAR:
                whiteKing = new King(PieceColor.WHITE, this, getSquareAt(4, 0));
                this.whiteKing = whiteKing;
                blackKing = new King(PieceColor.BLACK, this, getSquareAt(4, 7));
                this.blackKing = blackKing;
                
                pieces.add(new Rook(PieceColor.WHITE, this, getSquareAt(0, 0)));
                pieces.add(new Knight(PieceColor.WHITE, this, getSquareAt(1, 0)));
                pieces.add(new Bishop(PieceColor.WHITE, this, getSquareAt(2, 0)));
                pieces.add(new Queen(PieceColor.WHITE, this, getSquareAt(3, 0)));
                pieces.add(whiteKing);
                pieces.add(new Bishop(PieceColor.WHITE, this, getSquareAt(5, 0)));
                pieces.add(new Knight(PieceColor.WHITE, this, getSquareAt(6, 0)));
                pieces.add(new Rook(PieceColor.WHITE, this, getSquareAt(7, 0)));
                
                pieces.add(new Pawn(PieceColor.WHITE, this, getSquareAt(0, 1)));
                pieces.add(new Pawn(PieceColor.WHITE, this, getSquareAt(1, 1)));
                pieces.add(new Pawn(PieceColor.WHITE, this, getSquareAt(2, 1)));
                pieces.add(new Pawn(PieceColor.WHITE, this, getSquareAt(3, 1)));
                pieces.add(new Pawn(PieceColor.WHITE, this, getSquareAt(4, 1)));
                pieces.add(new Pawn(PieceColor.WHITE, this, getSquareAt(5, 1)));
                pieces.add(new Pawn(PieceColor.WHITE, this, getSquareAt(6, 1)));
                pieces.add(new Pawn(PieceColor.WHITE, this, getSquareAt(7, 1)));

                pieces.add(new Rook(PieceColor.BLACK, this, getSquareAt(0, 7)));
                pieces.add(new Knight(PieceColor.BLACK, this, getSquareAt(1, 7)));
                pieces.add(new Bishop(PieceColor.BLACK, this, getSquareAt(2, 7)));
                pieces.add(new Queen(PieceColor.BLACK, this, getSquareAt(3, 7)));
                pieces.add(blackKing);
                pieces.add(new Bishop(PieceColor.BLACK, this, getSquareAt(5, 7)));
                pieces.add(new Knight(PieceColor.BLACK, this, getSquareAt(6, 7)));
                pieces.add(new Rook(PieceColor.BLACK, this, getSquareAt(7, 7)));

                pieces.add(new Pawn(PieceColor.BLACK, this, getSquareAt(0, 6)));
                pieces.add(new Pawn(PieceColor.BLACK, this, getSquareAt(1, 6)));
                pieces.add(new Pawn(PieceColor.BLACK, this, getSquareAt(2, 6)));
                pieces.add(new Pawn(PieceColor.BLACK, this, getSquareAt(3, 6)));
                pieces.add(new Pawn(PieceColor.BLACK, this, getSquareAt(4, 6)));
                pieces.add(new Pawn(PieceColor.BLACK, this, getSquareAt(5, 6)));
                pieces.add(new Pawn(PieceColor.BLACK, this, getSquareAt(6, 6)));
                pieces.add(new Pawn(PieceColor.BLACK, this, getSquareAt(7, 6)));
                break;
                
            case MINI:
                whiteKing = new King(PieceColor.WHITE, this, getSquareAt(2, 0));
                this.whiteKing = whiteKing;
                blackKing = new King(PieceColor.BLACK, this, getSquareAt(2, 5));
                this.blackKing = blackKing;
                
                pieces.add(new Rook(PieceColor.WHITE, this, getSquareAt(0, 0)));
                pieces.add(new Queen(PieceColor.WHITE, this, getSquareAt(1, 0)));
                pieces.add(whiteKing);
                pieces.add(new Knight(PieceColor.WHITE, this, getSquareAt(3, 0)));
                pieces.add(new Rook(PieceColor.WHITE, this, getSquareAt(4, 0)));
                
                pieces.add(new Pawn(PieceColor.WHITE, this, getSquareAt(0, 1)));
                pieces.add(new Pawn(PieceColor.WHITE, this, getSquareAt(1, 1)));
                pieces.add(new Pawn(PieceColor.WHITE, this, getSquareAt(2, 1)));
                pieces.add(new Pawn(PieceColor.WHITE, this, getSquareAt(3, 1)));
                pieces.add(new Pawn(PieceColor.WHITE, this, getSquareAt(4, 1)));

                pieces.add(new Rook(PieceColor.BLACK, this, getSquareAt(0, 5)));
                pieces.add(new Queen(PieceColor.BLACK, this, getSquareAt(1, 5)));
                pieces.add(blackKing);
                pieces.add(new Knight(PieceColor.BLACK, this, getSquareAt(3, 5)));
                pieces.add(new Rook(PieceColor.BLACK, this, getSquareAt(4, 5)));

                pieces.add(new Pawn(PieceColor.BLACK, this, getSquareAt(0, 4)));
                pieces.add(new Pawn(PieceColor.BLACK, this, getSquareAt(1, 4)));
                pieces.add(new Pawn(PieceColor.BLACK, this, getSquareAt(2, 4)));
                pieces.add(new Pawn(PieceColor.BLACK, this, getSquareAt(3, 4)));
                pieces.add(new Pawn(PieceColor.BLACK, this, getSquareAt(4, 4)));
                break;
        }
    }
    
    public Square getSquareAt(int x, int y) {
        try {
            return board[x][y];
        } catch (Exception e) {
            return null;
        }
    }
    
    public void killPiece(Piece piece) {
        pieces.remove(piece);
    }
    
    void updateMoves() {
        for(Piece piece : pieces) {
            piece.resetMoves();
            piece.updateAvailableMoves();
        }
    }
    
    public List<Piece> getPieces() { return pieces; }
    public Piece getBlackKing() { return blackKing; }
    public Piece getWhiteKing() { return whiteKing; }
    
    public int getXSize() { return board.length; }
    public int getYSize() { return board[0].length; }

    public boolean inBounds(int x, int y) {
        if(x > getXSize() - 1 || x < 0) return false;
        return !(y > getYSize() - 1 || y < 0);
    }
    
    
}
