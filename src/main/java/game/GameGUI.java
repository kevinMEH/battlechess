package game;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import pieces.Piece;

import java.io.IOException;

public class GameGUI extends Application {
    
    public static void commence() {
        launch(GameGUI.class);
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
//        Parent root = FXMLLoader.load(getClass().getResource("/Gui.fxml"));
        
        VBox root = new VBox();

        Button regular = new Button("Regular Chess");
        regular.setOnAction(actionEvent -> {
            System.out.println("User Selected Regular Chess");
            game = new Game(Board.BoardType.REGULAR);
            playGame(primaryStage);
        });
        Button mini = new Button("Mini Chess");
        mini.setOnAction(actionEvent -> {
            System.out.println("User Selected Mini Chess");
            game = new Game(Board.BoardType.MINI);
            playGame(primaryStage);
        });

        root.getChildren().addAll(regular, mini);
        final Scene menuScene = new Scene(root, 1200, 800);
        primaryStage.setTitle("battlechess");
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }
    
    GridPane chessBoard;
    Game game;
    
    Piece currentSelectedPiece; // The current selected piece
    PieceColor currentPlayer = PieceColor.WHITE;
    
    void playGame(Stage primaryStage) { // Chess board and game
        chessBoard = new GridPane();
        chessBoard.setBackground(new Background(new BackgroundFill(Color.web("fdf6e3"), CornerRadii.EMPTY, Insets.EMPTY)));
        chessBoard.setAlignment(Pos.CENTER);
        chessBoard.setMinSize(400, 400);
        
        int xSize = game.getBoard().getXSize();
        int ySize = game.getBoard().getYSize();
        
        // NOTE: X and Y both start at the top left of the screen instead of the bottom left, therefore the Y has to be inverted.
        for(int y = ySize - 1; y >= 0; y--) { // Initial board setup, no pieces
            for(int x = 0; x < xSize; x++) {
                // StackPane is a variable to the square in the board for easy access
                Pane pane = new Pane();
                if((x + ySize - 1 - y) % 2 == 0)
                    pane.setBackground(new Background(new BackgroundFill(Color.web("#fbf1c7"), CornerRadii.EMPTY, Insets.EMPTY)));
                else
                    pane.setBackground(new Background(new BackgroundFill(Color.web("#8ec07c"), CornerRadii.EMPTY, Insets.EMPTY)));
                
                int currentX = x; // Or else the lambda will use the variable x and y of the for statement, which is dynamic.
                int currentY = y;
                pane.setOnMouseClicked(mouseEvent -> { //
                    
                    System.out.println("Clicked square " + currentX + ", " + currentY);
                    
                    Square square = game.getBoard().getSquareAt(currentX, currentY);
                    if(square.hasPiece()) {
                        System.out.println("Square has piece"); // DEBUGGING REMOVE THIS
                        Piece targetPiece = square.getPiece();
                        
                        if(currentSelectedPiece == null) { // If we have not selected a piece, select it
                            System.out.println("No selected piece"); // DEBUGGING REMOVE THIS
                            if(targetPiece.getColor() == currentPlayer) { 
                                // If the piece belongs to the player
                                System.out.println("Player " + currentPlayer.name() + " selected " + targetPiece.getClass() 
                                        + " at " + targetPiece.getSquare().getX() + ", " + targetPiece.getSquare().getY());
                                currentSelectedPiece = targetPiece;
                            }
                        } else { // We have already selected a piece
                            System.out.println("Already selected a piece"); // DEBUGGING REMOVE THIS
                            if(targetPiece.getColor() == currentPlayer) { // If the color is the same, reselect to the other piece
                                System.out.println("Same color"); // DEBUGGING REMOVE THIS
                                System.out.println("Player " + currentPlayer.name() + " selected " + targetPiece.getClass()
                                        + " at " + targetPiece.getSquare().getX() + ", " + targetPiece.getSquare().getY());
                                currentSelectedPiece = targetPiece;
                            } else { // If color is different
                                System.out.println("Different color"); // DEBUGGING REMOVE THIS
                                if(currentSelectedPiece.getPossibleMoves().contains(targetPiece.getSquare())) {
                                    // If move is possible
                                    currentSelectedPiece.move(currentX, currentY);
                                    System.out.println("Player moved piece " + currentSelectedPiece.getColor() 
                                            + " to " + currentX + ", " + currentY);
                                    nextTurn();
                                }
                            }
                        }
                    } else { // If square does not have a piece
                        System.out.println("Square does not have a piece"); // DEBUGGING REMOVE THIS
                        if(currentSelectedPiece != null 
                                && currentSelectedPiece.getPossibleMoves().contains(square)) { // If valid move, move to that square
                            currentSelectedPiece.move(currentX, currentY);
                            System.out.println("Player moved piece " + currentSelectedPiece.getColor() 
                                    + " to " + currentX + ", " + currentY);
                            nextTurn();
                        }
                    }
        
                });
                
                game.getBoard().getSquareAt(x, y).setPane(pane);
                chessBoard.add(pane, x, ySize - 1 - y);
            }
        }

        // Setting constraints for rows and columns
        for(int x = 0; x < xSize; x++)
            chessBoard.getColumnConstraints().add(new ColumnConstraints(20, Control.USE_COMPUTED_SIZE, 100, Priority.ALWAYS, HPos.CENTER, true));
        for(int y = 0; y < ySize; y++)
            chessBoard.getRowConstraints().add(new RowConstraints(20, Control.USE_COMPUTED_SIZE, 100, Priority.ALWAYS, VPos.CENTER, true));

        // Initial update of board
        System.out.println("Initial Update of Board");
        updateBoard();
        game.getBoard().updateMoves();

        final Scene game = new Scene(chessBoard, 1200, 800);
        primaryStage.setScene(game);
        primaryStage.show();
        
    }
    
    public void updateBoard() {
        Board board = game.getBoard();
        // NOTE: X and Y both start at the top left of the screen instead of the bottom left, therefore the Y has to be inverted.
        for(int y = 0; y < board.getYSize(); y++) {
            for(int x = 0; x < board.getXSize(); x++) {
                Square square = board.getSquareAt(x, y);
                Pane pane = square.getPane();
                pane.getChildren().clear(); // Clears children 
                if(square.hasPiece()) { // If it has a piece displays it
                    ImageView imageView = square.getPiece().getImageView();
                    imageView.resize(pane.getWidth(), pane.getHeight());
                    pane.getChildren().add(imageView);
                }
            }
        }
    }
    
    void nextTurn() {
        game.getBoard().updateMoves();
        updateBoard();
        switch(currentPlayer) {
            case WHITE: currentPlayer = PieceColor.BLACK;
                break;
            case BLACK: currentPlayer = PieceColor.WHITE;
                break;
        }
        currentSelectedPiece = null;
        if(game.checkWin()) endScreen();
    }
    
    void endScreen() { // End screen
        
    }
}
