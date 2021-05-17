package pieces;

import game.*;

public class Knight extends Piece {
    
    public Knight(PieceColor pieceColor, Board board, Square square) {
        super(pieceColor, board, square);
        if(pieceColor == PieceColor.WHITE) setImage("file:assets/White Knight.png");
        else setImage("file:assets/Black Knight.png");
    }
    
    public void updateAvailableMoves() {
        int x = this.getSquare().getX();
        int y = this.getSquare().getY();
        /*
        2       X       X
        1   X               X
        0           K
       -1   X               X
       -2       X       X
    x      -2  -1   0   1   2
        y
         */
        // Top Left Top
        if(validMove(x - 1, y + 2))
            addMove(board.getSquareAt(x - 1, y + 2));
        // Top Left Left
        if(validMove(x - 2, y + 1))
            addMove(board.getSquareAt(x - 2, y + 1));
        // Top Right Top
        if(validMove(x + 1, y + 2))
            addMove(board.getSquareAt(x + 1, y + 2));
        // Top Right Right
        if(validMove(x + 2, y + 1)) 
            addMove(board.getSquareAt(x + 2, y + 1));
        // Bottom Left Left
        if(validMove(x - 2, y - 1)) 
            addMove(board.getSquareAt(x - 2, y - 1));
        // Bottom Left Bottom
        if(validMove(x - 1, y - 2)) 
            addMove(board.getSquareAt(x - 1, y - 2));
        // Bottom Right Right
        if(validMove(x + 2, y - 1)) 
            addMove(board.getSquareAt(x + 2, y - 1));
        // Bottom Right Bottom
        if(validMove(x + 1, y - 2)) 
            addMove(board.getSquareAt(x + 1, y - 2));
    }
}
