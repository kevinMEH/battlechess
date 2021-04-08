package game;

import pieces.Piece;

import java.util.List;

public class Game {
    
    private Board board;
    
    Game(Board.BoardType boardType) {
        switch(boardType) {
            case REGULAR:
                board = new Board(8, 8, boardType);
                break;
            case MINI:
                board = new Board(5, 6, boardType);
                break;
        }
    }
    
    PieceColor winnerColor() {
        List<Piece> pieces = board.getPieces();
        if(!pieces.contains(board.getBlackKing())) // Does not contain black king
            return PieceColor.WHITE;
        if(!pieces.contains(board.getWhiteKing())) // Does not contain white king
            return PieceColor.BLACK;
        return null;
    }
    
    public boolean checkWin() { return winnerColor() != null; }
    
    public Board getBoard() { return board; }

}
