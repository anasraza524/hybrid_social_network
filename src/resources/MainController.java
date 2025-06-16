package resources;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainController {
    @FXML private TextField userCountField;
    @FXML private TextField fromUserField;
    @FXML private TextField toUserField;
    @FXML private TextField acceptUserField;
    @FXML private TextField suggestUserField;
    @FXML private TextField lcaAField;
    @FXML private TextField lcaBField;
    @FXML private TextField shortPathAField;
    @FXML private TextField shortPathBField;
    @FXML private TextField removeUserField;
    @FXML private TextArea outputArea;
    @FXML private Pane graphPane;

    private SocialNetworkManager manager;

    @FXML
    private void initializeNetwork() {
        try {
            int size = Integer.parseInt(userCountField.getText());
            manager = new SocialNetworkManager(size);
            outputArea.setText("Network initialized with " + size + " users.");
        } catch (NumberFormatException e) {
            outputArea.setText("Please enter a valid number of users.");
        }
    }

    @FXML
    private void sendRequest() {
        try {
            int from = Integer.parseInt(fromUserField.getText());
            int to = Integer.parseInt(toUserField.getText());
            manager.sendRequest(from, to);
            outputArea.setText("Friend request sent from " + from + " to " + to);
        } catch (NumberFormatException e) {
            outputArea.setText("Please enter valid user IDs.");
        }
    }

    @FXML
    private void acceptRequest() {
        try {
            int userId = Integer.parseInt(acceptUserField.getText());
            manager.acceptRequest(userId);
            outputArea.setText("Processed accept request for user " + userId);
        } catch (NumberFormatException e) {
            outputArea.setText("Please enter a valid user ID.");
        }
    }

    @FXML
    private void buildSuggestionTree() {
        try {
            int userId = Integer.parseInt(suggestUserField.getText());
            manager.buildSuggestionTree(userId);
            outputArea.setText("Suggestion tree built for user " + userId);
        } catch (NumberFormatException e) {
            outputArea.setText("Please enter a valid user ID.");
        }
    }

    @FXML
    private void printSuggestionTree() {
        String tree = manager.printSuggestionTree();
        outputArea.setText("Suggestion Tree (Level Order):\n" + tree);
    }

    @FXML
    private void findLCA() {
        try {
            int a = Integer.parseInt(lcaAField.getText());
            int b = Integer.parseInt(lcaBField.getText());
            Integer lca = manager.findLCA(a, b);
            outputArea.setText(lca != null ? "LCA of " + a + " and " + b + " is " + lca : "No LCA found.");
        } catch (NumberFormatException e) {
            outputArea.setText("Please enter valid user IDs.");
        }
    }

    @FXML
    private void shortestPath() {
        try {
            int a = Integer.parseInt(shortPathAField.getText());
            int b = Integer.parseInt(shortPathBField.getText());
            int pathLength = manager.getShortestPath(a, b);
            outputArea.setText(pathLength >= 0 ? "Shortest path length between " + a + " and " + b + ": " + pathLength : "No path exists.");
        } catch (NumberFormatException e) {
            outputArea.setText("Please enter valid user IDs.");
        }
    }

    @FXML
    private void addFriendToGraph() {
        try {
            int a = Integer.parseInt(fromUserField.getText());
            int b = Integer.parseInt(toUserField.getText());
            manager.addFriendToGraph(a, b);
            outputArea.setText("Friendship added between " + a + " and " + b);
        } catch (NumberFormatException e) {
            outputArea.setText("Please enter valid user IDs.");
        }
    }

    @FXML
    private void removeUser() {
        try {
            int userId = Integer.parseInt(removeUserField.getText());
            manager.removeUser(userId);
            outputArea.setText("User " + userId + " removed.");
        } catch (NumberFormatException e) {
            outputArea.setText("Please enter a valid user ID.");
        }
    }

    @FXML
    private void printVisualGraph() {
        String visualGraph = manager.getVisualGraph();
        outputArea.setText(visualGraph);
    }

    @FXML
    private void renderGraph() {
        if (manager == null) {
            outputArea.setText("Please initialize the network first.");
            return;
        }

        // Create a new stage for the graph
        Stage graphStage = new Stage();
        graphStage.setTitle("Social Network Graph");

        // Create a new pane for the graph
        Pane newGraphPane = new Pane();
        newGraphPane.setPrefSize(600, 400);
        newGraphPane.setStyle("-fx-background-color: #f5f5f5;");

        // Get the graph data
        Map<Integer, List<Integer>> graph = manager.getGraph().getGraph();
        int nodeCount = graph.size();
        if (nodeCount == 0) {
            outputArea.setText("Graph is empty.");
            graphStage.close();
            return;
        }

        // Simple circular layout with dynamic radius
        double centerX = newGraphPane.getPrefWidth() / 2;
        double centerY = newGraphPane.getPrefHeight() / 2;
        double radius = Math.min(newGraphPane.getPrefWidth(), newGraphPane.getPrefHeight()) / Math.max(3, Math.sqrt(nodeCount));
        Random random = new Random(42);

        // Map to store node positions
        Map<Integer, double[]> positions = new java.util.HashMap<>();
        int index = 0;
        for (int userId : graph.keySet()) {
            if (userId >= manager.getUsers().size() || manager.getUsers().get(userId) == null) {
                continue; // Skip removed users
            }
            double angle = 2 * Math.PI * index / nodeCount;
            double x = centerX + radius * Math.cos(angle) + random.nextDouble(-20, 20);
            double y = centerY + radius * Math.sin(angle) + random.nextDouble(-20, 20);
            positions.put(userId, new double[]{x, y});
            index++;
        }

        // Draw edges
        for (int userId : graph.keySet()) {
            if (userId >= manager.getUsers().size() || manager.getUsers().get(userId) == null) {
                continue;
            }
            double[] pos1 = positions.get(userId);
            if (pos1 == null) continue;
            for (int friendId : graph.get(userId)) {
                double[] pos2 = positions.get(friendId);
                if (pos2 != null) {
                    Line edge = new Line(pos1[0], pos1[1], pos2[0], pos2[1]);
                    edge.setStroke(Color.BLACK);
                    newGraphPane.getChildren().add(edge);
                }
            }
        }

        // Draw nodes
        for (int userId : positions.keySet()) {
            double[] pos = positions.get(userId);
            Circle node = new Circle(pos[0], pos[1], 15, Color.LIGHTBLUE);
            node.setStroke(Color.BLACK);
            Text label = new Text(pos[0] - 5, pos[1] + 5, String.valueOf(userId));
            newGraphPane.getChildren().addAll(node, label);
        }

        // Create a scene and set it to the stage
        Scene scene = new Scene(newGraphPane, 600, 400);
        scene.getStylesheets().add(getClass().getResource("/resources/style.css").toExternalForm());
        graphStage.setScene(scene);
        graphStage.show();

        outputArea.setText("Graph rendered in a new window.");
    }
}