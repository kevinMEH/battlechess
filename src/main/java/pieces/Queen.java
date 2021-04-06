package pieces;

import game.*;

public class Queen extends Piece {
    
    public Queen(Color color, Board board, Square square) {
        super(color, board, square);
        setAlias("QN");
    }

    @Override
    public void updateAvailableMoves() {
        int currentX = this.getSquare().getX();
        int currentY = this.getSquare().getY();
        // Bishop Pattern
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
        
        // Rook Pattern
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
