package tictactoe;

import tictactoe.game.TicTacToeGame;
import tictactoe.ui.SwingUI;

public class MainSwing {

    public static void main(String[] args) {

        var player1 = SwingUI.prompt("Player 1, what is your name? ");
        var player2 = SwingUI.prompt("Player 2, what is your name? ");

        var game = new TicTacToeGame(player1, player2);

        SwingUI.start(game, player1, player2);

    }
}
