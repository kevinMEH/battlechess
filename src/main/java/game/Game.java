package game;

import pieces.Piece;

import java.util.List;
import java.util.Scanner;

public class Game {
    
    private final Board board;
    
    Game() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Real Chess or Mini Chess?");
            System.out.println("Regular | Mini");
            String response = scanner.nextLine();
            if(response.equalsIgnoreCase("regular")) {
                board = new Board(8, 8, Board.BoardType.REGULAR);
                break;
            } else if(response.equalsIgnoreCase("mini")) {
                board = new Board(5, 6, Board.BoardType.MINI);
                break;
            } else {
                System.out.println("Error: Input not recognized! Try again.");
            }
        }
        startGame();
    }
    
    public void startGame() {
        while(true) {
            board.calculateMoves();
            playerMove(Color.WHITE);
            if(checkWin()) break;
            board.calculateMoves();
            playerMove(Color.BLACK);
            if(checkWin()) break;
        }
        endGame();
    }
    
    void playerMove(Color color) {
        while(true) {
            printBoard();
            System.out.println("It's " + color.name() + "'s turn to move!");
            System.out.println("What piece would you like to move? Please give the coordinates.");
            System.out.println("Example coordinates: 0:4 (x:y)");
            Scanner scanner = new Scanner(System.in);
            String response = scanner.nextLine();
            
            Piece targetPiece = checkCoordinates(response, color);
            if(targetPiece == null) continue;
            
            System.out.println("Where would you like to go? Please give the coordinates.");
            response = scanner.nextLine();
            
            if(!checkMove(response, targetPiece)) continue;
            targetPiece.move(convertToCoordinates(response));
            break;
        }
    }
    
    void printBoard() {
        final String RESET = "\u001B[0m";
        final String GRAY = "\u001B[47m";
        final String BLUE_TEXT = "\u001B[30m";
        
        for(int y = board.getYSize() - 1; y >= 0; y--) {
            System.out.print(y + " ");
            for(int x = 0; x < board.getXSize(); x++) {
                Square square = board.getSquareAt(x, y);
                String print;
                if((x + y) % 2 == 0) print = GRAY + " ";
                else print = " ";
                if(square.hasPiece()) {
                    if(square.getPieceColor() == Color.BLACK) print = print + BLUE_TEXT;
                    print = print + square.getPiece().getAlias() + " " + RESET;
                }
                else print = print + "   " + RESET;
                System.out.print(print);
            }
            System.out.println();
        }
        System.out.println("   0   1   2   3   4   5   6   7");
    }
    
    Piece checkCoordinates(String response, Color color) {
        if(!correctFormat(response)) {
            System.out.println("Incorrect format! Try again.");
            return null;
        }
        
        Integer[] coordinates = convertToCoordinates(response);
        
        if(!coordinatesInBounds(coordinates)) {
            System.out.println("Coordinates not in bounds! Try again.");
            return null;
        }
        Piece targetPiece = board.getSquareAt(coordinates[0], coordinates[1]).getPiece();
        if(targetPiece == null) {
            System.out.println("There is no piece at the specified location! Try again.");
            return null;
        }
        if(targetPiece.getColor() != color) {
            System.out.println("This piece does not belong to you! Try again.");
            return null;
        }
        return targetPiece;
    }
    
    boolean checkMove(String response, Piece attacker) {
        if(!correctFormat(response)) {
            System.out.println("Incorrect format! Try again.");
            return false;
        }
        
        Integer[] coordinates = convertToCoordinates(response);
        
        if(!coordinatesInBounds(coordinates)) {
            System.out.println("Coordinates not in bounds! Try again.");
            return false;
        }
        if(!attacker.getPossibleMoves().contains(board.getSquareAt(coordinates[0], coordinates[1]))) {
            System.out.println("Your piece cannot move here!");
            return false;
        }
        return true;
    }
    
    boolean correctFormat(String response) {
        String[] split = response.split(":");
        if(split.length != 2) return false;
        Integer[] coordinates = new Integer[2];
        for (int i = 0; i < 2; i++) {// Converting String array to Integer array
            try {
                coordinates[i] = Integer.parseInt(split[i]);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
    Integer[] convertToCoordinates(String response) {
        String[] split = response.split(":");
        Integer[] coordinates = new Integer[2];
        for (int i = 0; i < 2; i++)// Converting String array to Integer array
            coordinates[i] = Integer.parseInt(split[i]);
        return coordinates;
    }
    boolean coordinatesInBounds(Integer[] coordinates) {
        return board.inBounds(coordinates[0], coordinates[1]);
    }
    
    Color winnerColor() {
        List<Piece> pieces = board.getPieces();
        if(!pieces.contains(board.getBlackKing())) // Does not contain black king
            return Color.WHITE;
        if(!pieces.contains(board.getWhiteKing())) // Does not contain white king
            return Color.BLACK;
        return null;
    }
    
    boolean checkWin() { return winnerColor() != null; }
    
    private void endGame() {
        switch (winnerColor()) {
            case WHITE:
                System.out.println("Congratulations! White won the game.");
                break;
            case BLACK:
                System.out.println("Congratulations! Black won the game.");
                break;
        }
    }

}
