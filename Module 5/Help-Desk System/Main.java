import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

public class Main extends Application {

    private final int NUM_DESKS = 5;
    private final int MAX_QUEUE_SIZE = 5;

    private ObservableList<String>[] deskData;
    private Queue<Integer>[] deskQueues;

    private int nextCallerId = 1;
    private TextArea logBox;

    @Override
    public void start(Stage primaryStage) {
        
        deskData = new ObservableList[NUM_DESKS];
        deskQueues = new LinkedList[NUM_DESKS];

        VBox root = new VBox(15);
        root.setPadding(new Insets(15));

        HBox deskBox = new HBox(15);

        // Create 5 desk ListViews
        for (int i = 0; i < NUM_DESKS; i++) {
            deskData[i] = FXCollections.observableArrayList();
            deskQueues[i] = new LinkedList<>();

            ListView<String> listView = new ListView<>(deskData[i]);
            listView.setPrefSize(150, 200);
            VBox box = new VBox(5, new Label("Desk " + i), listView);
            deskBox.getChildren().add(box);
        }

        // Add Caller Button
        Button addCallerBtn = new Button("Add Caller");
        addCallerBtn.setOnAction(e -> addCaller());

        // Serve Caller Controls
        ComboBox<Integer> deskSelector = new ComboBox<>();
        deskSelector.getItems().addAll(0, 1, 2, 3, 4);
        deskSelector.setValue(0);

        Button serveBtn = new Button("Serve Caller");
        serveBtn.setOnAction(e -> serveCaller(deskSelector.getValue()));

        HBox controls = new HBox(10, addCallerBtn, new Label("Serve desk:"), deskSelector, serveBtn);

        // Lof Box
        logBox = new TextArea();
        logBox.setPrefHeight(150);
        logBox.setEditable(false);

        root.getChildren().addAll(deskBox, controls, new Label("Event Log:"), logBox);

        primaryStage.setTitle("Bank Help-Desk Queue System");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.show();
    }

    // Add caller to shortest queue
    private void addCaller() {
        int callerId = nextCallerId++;

        int minDesk = -1;
        int minSize = Integer.MAX_VALUE;

        for (int i = 0; i < NUM_DESKS; i++) {
            int size = deskQueues[i].size();
            if (size < minSize) {
                minSize = size;
                minDesk = i;
            }
        }

        if (minSize == MAX_QUEUE_SIZE) {
            log("All queues full! Caller " + callerId + " rejected.");
            return;
        }

        deskQueues[minDesk].add(callerId);
        deskData[minDesk].add("Caller " + callerId);

        log("Caller " + callerId + " added to desk " + minDesk + ".");
    }

    // Serve current persom from desk
    private void serveCaller(int deskIndex) {
        if (deskQueues[deskIndex].isEmpty()) {
            log("Desk " + deskIndex + " queue is empty! No caller to serve.");
            return;
        }

        int served = deskQueues[deskIndex].poll();
        deskData[deskIndex].remove(0);

        log("Served Caller " + served + " at Desk " + deskIndex + ".");
    }

    private void log(String message) {
        logBox.appendText(message + "\n");
    }

    public static void main(String[] args) {
        launch(args);
    }
}