package pieces;

import game.*;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece { // A basic piece
    
    private final Color color;
    private boolean killed = false;
    private Square square;
    final Board board;
    private final List<Square> possibleMoves = new ArrayList<>();
    private String alias;
    
    public Piece(Color color, Board board, Square square) {
        this.color = color;
        this.board = board;
        this.square = square;
        square.capture(this);
    }
    
    public void move(int x, int y) {
        Square targetSquare = board.getSquareAt(x, y);
        if(getPossibleMoves().contains(targetSquare)) {
            this.getSquare().removePiece();
            targetSquare.capture(this);
            this.setSquare(targetSquare);
        }
    }
    
    public void move(Integer[] coordinates) {
        if(coordinates.length > 2) {
            System.out.println("Coordinates in incorrect format! Operation aborted. move(Integer[] coordinates)");
            return;
        }
        move(coordinates[0], coordinates[1]);
    }

    boolean validMove(int x, int y) {
        if(!board.inBounds(x, y)) return false;
        return board.getSquareAt(x, y).getPieceColor() != color;
    }
    
    public Square getSquare() {return square;}
    public void setSquare(Square square) {this.square = square;}
    
    public List<Square> getPossibleMoves() { return possibleMoves; }
    void addMove(Square square) { possibleMoves.add(square); }
    public void resetMoves() { possibleMoves.clear(); }
    
    public Color getColor() { return color; }
    
    public void kill() { 
        this.killed = true; 
        board.killPiece(this);
    }
    
    public boolean isKilled() { return killed; }
    
    public String getAlias() {return alias;}
    void setAlias(String alias) { this.alias = alias; }

    public abstract void updateAvailableMoves();
    
}

