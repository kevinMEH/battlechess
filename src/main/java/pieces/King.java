package pieces;

import game.*;

public class King extends Piece {

    public King(Color color, Board board, Square square) {
        super(color, board, square);
        if(color == Color.WHITE) setAlias("♔");
        else setAlias("♚");
    }
    
    public void updateAvailableMoves() { 
        // TODO: Check for checkmates before moving. 
        // Or not actually to make game more intense. 
        // Add timer if not doing this.
        int x = this.getSquare().getX();
        int y = this.getSquare().getY();
        // Top Left
        if(validMove(x - 1, y + 1)) addMove(board.getSquareAt(x - 1, y + 1));
        // Top
        if(validMove(x, y + 1)) addMove(board.getSquareAt(x, y + 1));
        // Top Right
        if(validMove(x + 1, y + 1)) addMove(board.getSquareAt(x + 1, y + 1));
        // Left
        if(validMove(x - 1, y)) addMove(board.getSquareAt(x - 1, y));
        // Right
        if(validMove(x + 1, y)) addMove(board.getSquareAt(x + 1, y));
        // Bottom Left
        if(validMove(x - 1, y - 1)) addMove(board.getSquareAt(x - 1, y - 1));
        // Bottom
        if(validMove(x, y - 1)) addMove(board.getSquareAt(x, y - 1));
        // Bottom Right
        if(validMove(x + 1, y - 1)) addMove(board.getSquareAt(x + 1, y - 1));
    }
    
}
