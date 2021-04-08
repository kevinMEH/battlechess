package pieces;

import game.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece { // A basic piece
    
    private final PieceColor pieceColor;
    private Square square;
    final Board board;
    private final List<Square> possibleMoves = new ArrayList<>();
    private ImageView imageView;
    
    public Piece(PieceColor pieceColor, Board board, Square square) {
        this.pieceColor = pieceColor;
        this.board = board;
        this.square = square;
        square.capture(this);
    }
    
    public void move(int x, int y) {
        Square targetSquare = board.getSquareAt(x, y);
        this.getSquare().removePiece();
        targetSquare.capture(this);
        this.setSquare(targetSquare);
    }

    boolean validMove(int x, int y) {
        if(!board.inBounds(x, y)) return false;
        return board.getSquareAt(x, y).getPieceColor() != pieceColor;
    }
    
    public Square getSquare() {return square;}
    public void setSquare(Square square) {this.square = square;}
    
    public List<Square> getPossibleMoves() { return possibleMoves; }
    void addMove(Square square) { possibleMoves.add(square); }
    public void resetMoves() { possibleMoves.clear(); }
    
    public PieceColor getColor() { return pieceColor; }
    
    public void kill() {
        board.killPiece(this);
    }
    
    public ImageView getImageView() { return imageView; }
    void setImage(String path) {
        Image image = new Image(path);
        imageView = new ImageView();
        imageView.setImage(image);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
    }

    public abstract void updateAvailableMoves();
    
}

