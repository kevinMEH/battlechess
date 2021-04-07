package pieces;

import game.*;

public class Rook extends Piece {
    
    public Rook(PieceColor pieceColor, Board board, Square square) {
        super(pieceColor, board, square);
        setAlias("RK");
        if(pieceColor == PieceColor.WHITE) setImage("/src/main/pictures/White Rook.png");
        else setImage("/src/main/pictures/Black Rook.png");
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
