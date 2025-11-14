import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Random;

public class GameGUI extends Application {

    private final int[] dice = new int[5];
    private final boolean[] keep = new boolean[5];
    private final Button[] diceButtons = new Button[5];

    private int rollCount = 0;
    private int player = 1;               // Player 1 starts
    private int player1Score = 0;
    private int player2Score = 0;

    private Label statusLabel = new Label("Player 1: Click Roll to begin.");
    private Label probabilityLabel = new Label("");

    @Override
    public void start(Stage stage) {

        // Dice buttons
        HBox diceBox = new HBox(10);
        diceBox.setAlignment(Pos.CENTER);

        for (int i = 0; i < diceButtons.length; i++) {
            int index = i;
            Button btn = new Button("-");
            btn.setPrefSize(70, 70);
            btn.setStyle("-fx-font-size: 24px; -fx-background-color: lightgray;");
            btn.setOnAction(e -> toggleKeep(index));
            diceButtons[i] = btn;
            diceBox.getChildren().add(btn);
        }

        // Buttons
        Button rollBtn = new Button("Roll");
        rollBtn.setPrefWidth(100);
        rollBtn.setOnAction(e -> rollDice());

        Button nextBtn = new Button("End Turn");
        nextBtn.setPrefWidth(100);
        nextBtn.setOnAction(e -> endTurn());

        HBox controlBox = new HBox(20, rollBtn, nextBtn);
        controlBox.setAlignment(Pos.CENTER);

        VBox root = new VBox(20, statusLabel, diceBox, probabilityLabel, controlBox);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 20px;");

        Scene scene = new Scene(root, 500, 350);
        stage.setScene(scene);
        stage.setTitle("Dice Game");
        stage.show();
    }

    private void rollDice() {
        if (rollCount >= 3) {
            statusLabel.setText("No rolls left! Click End Turn.");
            return;
        }

        Random r = new Random();
        rollCount++;

        for (int i = 0; i < dice.length; i++) {
            if (!keep[i]) {
                dice[i] = r.nextInt(6) + 1;
            }
        }

        Arrays.sort(dice);
        updateDiceButtons();
        showProbability();

        statusLabel.setText("Player " + player + ": Roll #" + rollCount);
    }

    private void toggleKeep(int index) {
        keep[index] = !keep[index];
        diceButtons[index].setStyle(
                keep[index]
                        ? "-fx-font-size: 24px; -fx-background-color: lightgreen;"
                        : "-fx-font-size: 24px; -fx-background-color: lightgray;"
        );
    }

    private void updateDiceButtons() {
        for (int i = 0; i < diceButtons.length; i++) {
            diceButtons[i].setText(String.valueOf(dice[i]));
        }
    }

    private void showProbability() {
        int sum = Arrays.stream(dice).sum();
        double probability = Math.max(0, (30 - sum) / 24.0);
        probabilityLabel.setText(String.format("Probability of Improvement: %.2f%%", probability * 100));
    }

    private void endTurn() {
        int score = Arrays.stream(dice).sum();

        if (player == 1) {
            player1Score = score;
            player = 2;
            statusLabel.setText("Player 2: Begin your turn.");
        } else {
            player2Score = score;
            showWinner();
        }

        resetForNextTurn();
    }

    private void resetForNextTurn() {
        rollCount = 0;
        Arrays.fill(keep, false);
        for (Button btn : diceButtons) {
            btn.setText("-");
            btn.setStyle("-fx-font-size: 24px; -fx-background-color: lightgray;");
        }
        probabilityLabel.setText("");
    }

    private void showWinner() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Game Over!");

        String winner;

        if (player1Score > player2Score) winner = "Player 1 Wins!";
        else if (player2Score > player1Score) winner = "Player 2 Wins!";
        else winner = "It's a Tie!";

        alert.setContentText(
                "Player 1 Score: " + player1Score +
                "\nPlayer 2 Score: " + player2Score +
                "\n\n" + winner
        );

        alert.showAndWait();
    }
}
