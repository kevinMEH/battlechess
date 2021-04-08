package game;

import javafx.scene.layout.Pane;
import pieces.Piece;

public class Square {
    
    private Piece piece; // Current piece on square
    private Pane pane;
    private final int x;
    private final int y;
    
    public Square(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public boolean isEmpty() {
        return piece == null;
    }
    
    public boolean hasPiece() {
        return piece != null;
    }
    
    public void capture(Piece attacker) { // The attacker captures the current piece
        if(this.piece != null) this.piece.kill();
        this.piece = attacker;
    }
    
    public int getX() {return x;}
    public int getY() {return y;}
    public Piece getPiece() {return piece;}
    public void removePiece() {piece = null;}
    public PieceColor getPieceColor() {
        if(piece == null) return null;
        else return piece.getColor();
    }
    public Pane getPane() { return pane; }
    public void setPane(Pane pane) { this.pane = pane; }
    
    
    
}
