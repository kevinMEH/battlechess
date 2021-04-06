package pieces;

import game.*;

public class Rook extends Piece {
    
    public Rook(Color color, Board board, Square square) {
        super(color, board, square);
        setAlias("RK");
    }
    
    public void updateAvailableMoves() {
        int currentX = this.getSquare().getX();
        int currentY = this.getSquare().getY();
        // Up
        for(int y = currentY + 1; board.inBounds(currentX, y); y++) {
            if(validMove(currentX, y)) 
                addMove(board.getSquareAt(currentX, y));
            if(board.getSquareAt(currentX, y).hasPiece()) break;
        }
        // Down
        for(int y = currentY - 1; board.inBounds(currentX, y); y--) {
            if(validMove(currentX, y))
                addMove(board.getSquareAt(currentX, y));
            if(board.getSquareAt(currentX, y).hasPiece()) break;
        }
        // Left
        for(int x = currentX - 1; board.inBounds(x, currentY); x--) {
            if(validMove(x, currentY))
                addMove(board.getSquareAt(x, currentY));
            if(board.getSquareAt(x, currentY).hasPiece()) break;
        }
        // Right
        for(int x = currentX + 1; board.inBounds(x, currentY); x++) {
            if(validMove(x, currentY))
                addMove(board.getSquareAt(x, currentY));
            if(board.getSquareAt(x, currentY).hasPiece()) break;
        }
    }
    
}
