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
                Pane square = game.getBoard().getSquareAt(x, y).getPane();
                if((x + ySize - 1 - y) % 2 == 0)
                    square.setBackground(new Background(new BackgroundFill(Color.web("#fbf1c7"), CornerRadii.EMPTY, Insets.EMPTY)));
                else
                    square.setBackground(new Background(new BackgroundFill(Color.web("#8ec07c"), CornerRadii.EMPTY, Insets.EMPTY)));
                chessBoard.add(square, x, ySize - 1 - y);
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
                if(square.hasPiece()) {
                    ImageView imageView = square.getPiece().getImageView();
                    imageView.resize(pane.getWidth(), pane.getHeight());
                    pane.getChildren().add(imageView);
                }
            }
        }
    }
}
