package pieces;

import game.*;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(PieceColor pieceColor, Board board, Square square) {
        super(pieceColor, board, square);
        setAlias("KG");
        if(pieceColor == PieceColor.WHITE) setImage("https://raw.githubusercontent.com/kevinMEH/battlechess/gui/src/main/java/pieces/White%20King.png");
        else setImage("https://raw.githubusercontent.com/kevinMEH/battlechess/gui/src/main/java/pieces/Black%20King.png");
    }
    
    public void updateAvailableMoves() { 
        // TODO: Check for checkmates before moving. 
        // Or not actually to make game more intense. 
        // Add timer if not doing this.
        int x = this.getSquare().getX();
        int y = this.getSquare().getY();
        
        List<Square> enemyKingRawMoveSet 
                = getColor() == PieceColor.WHITE 
                ? ((King) board.getBlackKing()).getRawMoveSet() 
                : ((King) board.getWhiteKing()).getRawMoveSet();
        
        // Top Left
        if(validMove(x - 1, y + 1) && !enemyKingRawMoveSet.contains(board.getSquareAt(x - 1, y + 1)))
            addMove(board.getSquareAt(x - 1, y + 1));
        // Top
        if(validMove(x, y + 1) && !enemyKingRawMoveSet.contains(board.getSquareAt(x, y + 1)))
            addMove(board.getSquareAt(x, y + 1));
        // Top Right
        if(validMove(x + 1, y + 1) && !enemyKingRawMoveSet.contains(board.getSquareAt(x + 1, y + 1))) 
            addMove(board.getSquareAt(x + 1, y + 1));
        // Left
        if(validMove(x - 1, y) && !enemyKingRawMoveSet.contains(board.getSquareAt(x - 1, y))) 
            addMove(board.getSquareAt(x - 1, y));
        // Right
        if(validMove(x + 1, y) && !enemyKingRawMoveSet.contains(board.getSquareAt(x + 1, y))) 
            addMove(board.getSquareAt(x + 1, y));
        // Bottom Left
        if(validMove(x - 1, y - 1) && !enemyKingRawMoveSet.contains(board.getSquareAt(x - 1, y - 1))) 
            addMove(board.getSquareAt(x - 1, y - 1));
        // Bottom
        if(validMove(x, y - 1) && !enemyKingRawMoveSet.contains(board.getSquareAt(x, y - 1))) 
            addMove(board.getSquareAt(x, y - 1));
        // Bottom Right
        if(validMove(x + 1, y - 1) && !enemyKingRawMoveSet.contains(board.getSquareAt(x + 1, y - 1))) 
            addMove(board.getSquareAt(x + 1, y - 1));
    }
    
    public List<Square> getRawMoveSet() { // Raw move set, does not check for other king position
        List<Square> result = new ArrayList<>();
        int x = this.getSquare().getX();
        int y = this.getSquare().getY();
        
        // Only checks if in bounds
        // Top Left
        if(board.inBounds(x - 1, y + 1)) result.add(board.getSquareAt(x - 1, y + 1));
        // Top
        if(board.inBounds(x, y + 1)) result.add(board.getSquareAt(x, y + 1));
        // Top Right
        if(board.inBounds(x + 1, y + 1)) result.add(board.getSquareAt(x + 1, y + 1));
        // Left
        if(board.inBounds(x - 1, y)) result.add(board.getSquareAt(x - 1, y));
        // Right
        if(board.inBounds(x + 1, y)) result.add(board.getSquareAt(x + 1, y));
        // Bottom Left
        if(board.inBounds(x - 1, y - 1)) result.add(board.getSquareAt(x - 1, y - 1));
        // Bottom
        if(board.inBounds(x, y - 1)) result.add(board.getSquareAt(x, y - 1));
        // Bottom Right
        if(board.inBounds(x + 1, y - 1)) result.add(board.getSquareAt(x + 1, y - 1));
        
        return result;
    }
    
}
