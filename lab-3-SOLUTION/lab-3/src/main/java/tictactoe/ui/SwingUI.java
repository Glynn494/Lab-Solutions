package tictactoe.ui;

import tictactoe.game.Col;
import tictactoe.game.Position;
import tictactoe.game.Row;
import tictactoe.game.TicTacToeGame;

import javax.swing.*;
import java.awt.*;

public class SwingUI {

    public static String prompt(String message) {
        return JOptionPane.showInputDialog(null, message);
    }


    public static void start(TicTacToeGame game, String player1name, String player2name) {

        var frame = new JFrame("Tic Tac Toe");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);

        // 9 buttons in a 3x3 grid
        var panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));

        for (int i = 0; i < 9; i++) {
            var button = new JButton();
            var row = i / 3;
            var col = i % 3;
            button.addActionListener(e -> {
                var move = new Position(Row.from("" + (row+1)), Col.from("" + (col+1)));
                button.setText(game.getTurnToken().toString());
                game.doNextTurn(move);
                button.setEnabled(false);

                var status = game.getBoard().getStatus();
                switch (status) {
                    case XWins -> JOptionPane.showMessageDialog(null, player1name + " wins!");
                    case OWins -> JOptionPane.showMessageDialog(null, player2name + " wins!");
                    case Draw -> JOptionPane.showMessageDialog(null, "It's a draw!");
                }

                if (status != tictactoe.game.Board.Status.InProgress) {
                    System.exit(0);
                }
            });
            panel.add(button);
        }

        frame.add(panel);

        frame.setVisible(true);
    }


}
