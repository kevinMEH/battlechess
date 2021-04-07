package pieces;

import game.*;

public class Pawn extends Piece {
    
    private boolean firstMove = true;
    
    public Pawn(PieceColor pieceColor, Board board, Square square) {
        super(pieceColor, board, square);
        setAlias("PN");
        if(pieceColor == PieceColor.WHITE) setImage("/src/main/pictures/White Pawn.png");
        else setImage("/src/main/pictures/Black Pawn.png");
    }
    
    public void updateAvailableMoves() { // TODO: Add En Passant
        /*
        4
        3       1
        2   E   X   E
        1       P
        0   1   2   3
         */
        int x = getSquare().getX();
        int y = getSquare().getY();
        int multiplier;
        switch(getColor()) { // The multiplier determines the direction the piece moves
            case WHITE:
                multiplier = 1; // White pieces move up
                break;
            case BLACK:
                multiplier = -1; // Black pieces move down
                break;
            default:
                System.out.println("Color of piece not initialized! Method: updateAvailableMoves() " + getClass().getName());
                multiplier = 1;
        }
        if(firstMove && board.inBounds(x, y + multiplier * 2) 
                && board.getSquareAt(x, y + multiplier * 2).isEmpty())
            addMove(board.getSquareAt(x, y + multiplier * 2));
        if(board.inBounds(x, y + multiplier) && board.getSquareAt(x, y + multiplier).isEmpty())
            addMove(board.getSquareAt(x, y + multiplier));
        if(validMove(x - 1, y + multiplier) 
                && board.getSquareAt(x - 1, y + multiplier).hasPiece()) // Attack diagonally
            addMove(board.getSquareAt(x - 1, y + multiplier));
        if(validMove(x + 1, y + multiplier)
                && board.getSquareAt(x + 1, y + multiplier).hasPiece()) // Attack diagonally
            addMove(board.getSquareAt(x + 1, y + multiplier));
    }
    
    @Override
    public void move(int x, int y) {
        super.move(x, y);
        firstMove = false;
    }
}
