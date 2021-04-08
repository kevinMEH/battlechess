package game;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import pieces.Piece;
import pieces.King;

import java.util.List;

public class GameGUI extends Application {
    
    public static void commence() {
        launch(GameGUI.class);
    }
    
    @Override
    public void start(Stage primaryStage) {
        
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
                                selectPiece(targetPiece);
                            }
                            
                        } else { // We have already selected a piece
                            System.out.println("Already selected a piece"); // DEBUGGING REMOVE THIS
                            if(targetPiece.getColor() == currentPlayer) { // If the color is the same, reselect to the other piece
                                System.out.println("Same color"); // DEBUGGING REMOVE THIS
                                System.out.println("Player " + currentPlayer.name() + " selected " + targetPiece.getClass()
                                        + " at " + targetPiece.getSquare().getX() + ", " + targetPiece.getSquare().getY());
                                selectPiece(targetPiece);
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
                        } else if(currentSelectedPiece != null) {
                            selectPiece(null);
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
    
    Image blackDot = new Image("https://raw.githubusercontent.com/kevinMEH/battlechess/main/src/main/java/game/black%20dot.png");
    String blackDotId = "BlackDot";
    private void addPropertiesToImageView(ImageView imageView) {
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
    }
    
    String greenPaneID = "GreenPane";
    private void addPropertiesToPane(Pane pane, String hex) {
        pane.setBackground(new Background(new BackgroundFill(Color.web(hex), CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setPrefSize(100, 100);
    }

    Pane bluePane = new Pane();
    {
        addPropertiesToPane(bluePane, "#458588");
    }
    
    void selectPiece(Piece targetPiece) { // Highlights all possible moves
        resetMoveUI();
        currentSelectedPiece = targetPiece;
        if(targetPiece != null) {
            // Highlights
            currentSelectedPiece.getSquare().getPane().getChildren().add(currentSelectedPiece.getSquare().getPane().getChildren().size() - 1, bluePane);
            
            List<Square> moveSet = targetPiece.getPossibleMoves();
            for (Square move : moveSet) {
                if (move.hasPiece()) { // If square has piece add green pane
                    System.out.println("Added green pane to " + move.getX() + ", " + move.getY());
                    // JavaFX automatically removes children that are added to multiple nodes, therefore, we need to create a new node each time.
                    Pane tempGreenPane = new Pane();
                    addPropertiesToPane(tempGreenPane, "#b8bb26");
                    tempGreenPane.setId(greenPaneID);
                    move.getPane().getChildren().add(move.getPane().getChildren().size() - 1, tempGreenPane);
                } else { // Else put black dot
                    System.out.println("Added black dot to " + move.getX() + ", " + move.getY());
                    // JavaFX automatically removes children that are added to multiple nodes, therefore, we need to create a new node each time.
                    ImageView tempDot = new ImageView(blackDot);
                    addPropertiesToImageView(tempDot);
                    tempDot.setId(blackDotId);
                    move.getPane().getChildren().add(tempDot);
                }
            }
        }
    }
    
    void resetMoveUI() { // Resets the UI of the available moves based on the current piece
        if(currentSelectedPiece != null) {
            currentSelectedPiece.getSquare().getPane().getChildren().remove(bluePane);
            List<Square> moveSet = currentSelectedPiece.getPossibleMoves();
            for (Square move : moveSet) {
                if (move.hasPiece()) { // If square has piece remove green pane
                    // Removes based on ID. Add a # in front because it is a CSS selector.
                    System.out.println("Removing Green Pane");
                    move.getPane().getChildren().removeAll(move.getPane().lookupAll("#" + greenPaneID));
                } else { // Else remove black dot
                    // Removes based on ID. Add a # in front because it is a CSS selector.
                    System.out.println("Removing Black Dot");
                    move.getPane().getChildren().removeAll(move.getPane().lookupAll("#" + blackDotId));
                }
            }
        }
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
                    pane.getChildren().add(imageView);
                }
            }
        }
        // Checks if the king is in check, if yes puts a red square.
        if(((King) board.getWhiteKing()).inCheck() && board.getPieces().contains(board.getWhiteKing())) {
            System.out.println("White King in check");
            Pane redPane = new Pane();
            addPropertiesToPane(redPane, "#fb4934");
            board.getWhiteKing().getSquare().getPane().getChildren().add(board.getWhiteKing().getSquare().getPane().getChildren().size() - 1, redPane);
        }
        if(((King) board.getBlackKing()).inCheck() && board.getPieces().contains(board.getBlackKing())) {
            System.out.println("Black King in check");
            Pane redPane = new Pane();
            addPropertiesToPane(redPane, "#fb4934");
            board.getBlackKing().getSquare().getPane().getChildren().add(board.getBlackKing().getSquare().getPane().getChildren().size() - 1, redPane);
        }
    }
    
    void nextTurn() {
        selectPiece(null);
        game.getBoard().updateMoves();
        updateBoard();
        switch(currentPlayer) {
            case WHITE: currentPlayer = PieceColor.BLACK;
                break;
            case BLACK: currentPlayer = PieceColor.WHITE;
                break;
        }
        if(game.checkWin()) endScreen();
    }
    
    void endScreen() { // End screen
        
    }
}
