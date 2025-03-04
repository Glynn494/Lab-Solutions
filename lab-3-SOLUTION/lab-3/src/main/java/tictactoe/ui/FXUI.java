package tictactoe.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import tictactoe.game.Position;
import tictactoe.game.Col;
import tictactoe.game.Row;
import tictactoe.game.TicTacToeGame;

import java.util.Optional;

public class FXUI extends Application {

    public static String prompt(String message) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Input");
        dialog.setHeaderText(null);
        dialog.setContentText(message);

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    public static void alert(String message) {
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tic Tac Toe");

        var player1 = prompt("Player 1, what is your name?");
        var player2 = prompt("Player 2, what is your name?");

        var game = new TicTacToeGame(player1, player2);

        var grid = new GridPane();

        // Make the grid fill the stage
        for (int i = 0; i < 3; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.ALWAYS);
            colConstraints.setFillWidth(true);
            grid.getColumnConstraints().add(colConstraints);

            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.ALWAYS);
            rowConstraints.setFillHeight(true);
            grid.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0; i < 9; i++) {
            var button = new Button();
            button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            var row = i / 3;
            var col = i % 3;
            button.setOnAction(e -> {
                var move = new Position(Row.from("" + (row+1)), Col.from("" + (col+1)));
                button.setText(game.getTurnToken().toString());
                game.doNextTurn(move);
                button.setDisable(true);

                var status = game.getBoard().getStatus();
                switch (status) {
                    case XWins -> alert(player1 + " wins!");
                    case OWins -> alert(player2 + " wins!");
                    case Draw -> alert("It's a draw!");
                }

                if (status != tictactoe.game.Board.Status.InProgress) {
                    System.exit(0);
                }
            });
            grid.add(button, col, row);
        }

        var scene = new Scene(grid, 300, 300);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
