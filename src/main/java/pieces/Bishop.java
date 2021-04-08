package pieces;

import game.*;

public class Bishop extends Piece {
    
    public Bishop(PieceColor pieceColor, Board board, Square square) {
        super(pieceColor, board, square);
        if(pieceColor == PieceColor.WHITE) setImage("https://raw.githubusercontent.com/kevinMEH/battlechess/main/src/main/java/pieces/White%20Bishop.png");
        else setImage("https://raw.githubusercontent.com/kevinMEH/battlechess/main/src/main/java/pieces/Black%20Bishop.png");
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
