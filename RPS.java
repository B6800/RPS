//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RPS extends JFrame implements ActionListener {
    private int players = 1;
    private int rounds = 3;
    private int currentRound = 1;
    private int player1Score = 0;
    private int player2Score = 0;

    private Item player1Choice;
    private boolean waitingForPlayer2 = false;

    private final JLabel statusLabel = new JLabel("Choose game mode...");
    private final JLabel scoreLabel = new JLabel("Score: 0 - 0");

    public RPS() {
        setTitle("Rock Paper Scissors");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top status
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.add(statusLabel);
        topPanel.add(scoreLabel);
        add(topPanel, BorderLayout.NORTH);

        // Center buttons
        JPanel buttonPanel = new JPanel();
        JButton rockButton = new JButton("Rock");
        rockButton.addActionListener(this);
        JButton paperButton = new JButton("Paper");
        paperButton.addActionListener(this);
        JButton scissorsButton = new JButton("Scissors");
        scissorsButton.addActionListener(this);
        buttonPanel.add(rockButton);
        buttonPanel.add(paperButton);
        buttonPanel.add(scissorsButton);
        add(buttonPanel, BorderLayout.CENTER);

        // Prompt game setup
        setupGame();

        setVisible(true);
    }

    private void setupGame() {
        String[] options = {"1 Player", "2 Players"};
        int mode = JOptionPane.showOptionDialog(
                this,
                "Choose game mode:",
                "Game Mode",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        if (mode != -1) {
            players = mode + 1;
        }

        String roundsInput = JOptionPane.showInputDialog(this, "How many rounds?");
        try {
            rounds = Integer.parseInt(roundsInput);
        } catch (Exception e) {
            rounds = 3;
        }

        statusLabel.setText("Round 1 of " + rounds + " | Player 1's turn");
        scoreLabel.setText("Score: 0 - 0");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Item selected = switch (e.getActionCommand()) {
            case "Rock" -> Item.ROCK;
            case "Paper" -> Item.PAPER;
            case "Scissors" -> Item.SCISSORS;
            default -> null;
        };

        if (players == 2) {
            if (!waitingForPlayer2) {
                player1Choice = selected;
                waitingForPlayer2 = true;
                statusLabel.setText("Round " + currentRound + " | Player 2's turn");
            } else {
                playRound(player1Choice, selected);
                waitingForPlayer2 = false;
                nextRound();
            }
        } else {
            Item computerChoice = Item.random();
            playRound(selected, computerChoice);
            nextRound();
        }
    }

    private void playRound(Item p1, Item p2) {
        String result;
        if (p1 == p2) {

            result = "It's a draw!";
        } else if (
                (p1 == Item.ROCK && p2 == Item.SCISSORS) ||
                        (p1 == Item.PAPER && p2 == Item.ROCK) ||
                        (p1 == Item.SCISSORS && p2 == Item.PAPER)
        ) {
            player1Score++;
            result = "Player 1 wins the round!";
        } else {
            player2Score++;
            result = (players == 2 ? "Player 2" : "Computer") + " wins the round!";
        }

        JOptionPane.showMessageDialog(this,
                "Player 1 chose: " + p1 + "\n" +
                        (players == 2 ? "Player 2" : "Computer") + " chose: " + p2 + "\n" + result);

        scoreLabel.setText("Score: " + player1Score + " - " + player2Score);
    }

    private void nextRound() {
        currentRound++;
        if (currentRound > rounds) {
            String finalResult;
            if (player1Score == player2Score) {
                finalResult = "Game Draw!";
            } else if (player1Score > player2Score) {
                finalResult = "Player 1 wins the game!";
            } else {
                finalResult = (players == 2 ? "Player 2" : "Computer") + " wins the game!";
            }

            JOptionPane.showMessageDialog(this, finalResult);
            System.exit(0);
        } else {
            statusLabel.setText("Round " + currentRound + " of " + rounds + " | Player 1's turn");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RPS::new);
    }
}
