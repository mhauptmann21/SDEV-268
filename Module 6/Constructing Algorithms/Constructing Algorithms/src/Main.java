import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.*;

public class Main extends Application {

    private Graph graph = new Graph();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("DFS Path Finder – Building Network");

        // Graph Setup 
        graph.addEdge("A", "C");
        graph.addEdge("C", "D");
        graph.addEdge("D", "B");
        graph.addEdge("A", "E");
        graph.addEdge("E", "F");
        graph.addEdge("F", "B");

        // UI Controls
        ComboBox<String> startBox = new ComboBox<>();
        ComboBox<String> endBox = new ComboBox<>();

        startBox.getItems().addAll(graph.getNodes());
        endBox.getItems().addAll(graph.getNodes());

        startBox.setPromptText("Select Start Building");
        endBox.setPromptText("Select End Building");

        Button searchButton = new Button("Find Path (DFS)");
        TextArea output = new TextArea();
        output.setEditable(false);

        searchButton.setOnAction(e -> {
            String start = startBox.getValue();
            String end = endBox.getValue();

            if (start == null || end == null) {
                output.setText("⚠ Please select both buildings.");
                return;
            }

            List<String> path = graph.dfs(start, end);

            if (path != null) {
                output.setText("Path found:\n" + String.join(" → ", path));
            } else {
                output.setText(" No path found between " + start + " and " + end);
            }
        });

        // Layout
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                new Label("Depth-First Search Path Finder"),
                startBox,
                endBox,
                searchButton,
                output
        );

        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Graph Class with DFS -----------
    static class Graph {
        private Map<String, List<String>> adjacencyList = new HashMap<>();

        public void addEdge(String a, String b) {
            adjacencyList.computeIfAbsent(a, k -> new ArrayList<>()).add(b);
            adjacencyList.computeIfAbsent(b, k -> new ArrayList<>()).add(a);
        }

        public Set<String> getNodes() {
            return adjacencyList.keySet();
        }

        public List<String> dfs(String start, String end) {
            Set<String> visited = new HashSet<>();
            List<String> path = new ArrayList<>();

            if (dfsHelper(start, end, visited, path)) {
                return path;
            }
            return null;
        }

        private boolean dfsHelper(String current, String target,
                                  Set<String> visited, List<String> path) {
            visited.add(current);
            path.add(current);

            if (current.equals(target)) return true;

            for (String neighbor : adjacencyList.getOrDefault(current, Collections.emptyList())) {
                if (!visited.contains(neighbor)) {
                    if (dfsHelper(neighbor, target, visited, path)) {
                        return true;
                    }
                }
            }

            path.remove(path.size() - 1); // backtrack
            return false;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
