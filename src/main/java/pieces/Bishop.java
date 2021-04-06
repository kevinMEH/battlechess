package pieces;

import game.*;

public class Bishop extends Piece {
    
    public Bishop(Color color, Board board, Square square) {
        super(color, board, square);
        setAlias("BP");
    }
    
    public void updateAvailableMoves() {
        int currentX = this.getSquare().getX();
        int currentY = this.getSquare().getY();
        // Top Left
        for(int x = currentX - 1, y = currentY + 1;
            board.inBounds(x, y);
            x--, y++)
        {
            if(validMove(x, y)) addMove(board.getSquareAt(x, y));
            if(board.getSquareAt(x, y).hasPiece()) break; 
        }
        // Top Right
        for(int x = currentX + 1, y = currentY + 1;
            board.inBounds(x, y);
            x++, y++)
        {
            if(validMove(x, y)) addMove(board.getSquareAt(x, y));
            if(board.getSquareAt(x, y).hasPiece()) break;
        }
        // Bottom Left
        for(int x = currentX - 1, y = currentY - 1;
            board.inBounds(x, y);
            x--, y--)
        {
            if(validMove(x, y)) addMove(board.getSquareAt(x, y));
            if(board.getSquareAt(x, y).hasPiece()) break;
        }
        // Bottom Right
        for(int x = currentX + 1, y = currentY - 1;
            board.inBounds(x, y);
            x++, y--)
        {
            if(validMove(x, y)) addMove(board.getSquareAt(x, y));
            if(board.getSquareAt(x, y).hasPiece()) break;
        }
    }
}
