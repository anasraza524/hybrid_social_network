package resources;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController {
    @FXML public TextField userCountField, fromUserField, toUserField, acceptUserField;
    @FXML public TextField suggestUserField, lcaAField, lcaBField;
    @FXML public TextField shortPathAField, shortPathBField, removeUserField;
    @FXML public TextArea outputArea;
    
    private SocialNetworkManager manager;

    private void log(String msg) {
        outputArea.appendText(msg + "\n");
    }

    @FXML
    public void initializeNetwork() {
        int count = Integer.parseInt(userCountField.getText());
        manager = new SocialNetworkManager(count);
        log("Initialized network with " + count + " users.");
    }

    @FXML
    public void sendRequest() {
        int from = Integer.parseInt(fromUserField.getText());
        int to = Integer.parseInt(toUserField.getText());
        manager.sendRequest(from, to);
        log("Request sent from " + from + " to " + to);
    }

    @FXML
    public void acceptRequest() {
        int userId = Integer.parseInt(acceptUserField.getText());
        manager.acceptRequest(userId);
        log("User " + userId + " processed friend request.");
    }

    @FXML
    public void addFriendToGraph() {
        int from = Integer.parseInt(fromUserField.getText());
        int to = Integer.parseInt(toUserField.getText());
        manager.addFriendToGraph(from, to);
        log("Graph updated: " + from + " ↔ " + to);
    }

    @FXML
    public void buildSuggestionTree() {
        int userId = Integer.parseInt(suggestUserField.getText());
        manager.buildSuggestionTree(userId);
        log("Built suggestion tree for user " + userId);
    }

    @FXML
    public void printSuggestionTree() {
        log("Level-order suggestion tree:");
        String output = manager.printSuggestionTree();  // ✅ Get the result string
        log(output);                                    // ✅ Show in GUI
    }


    @FXML
    public void findLCA() {
        int a = Integer.parseInt(lcaAField.getText());
        int b = Integer.parseInt(lcaBField.getText());
        manager.findLCA(a, b);
    }

    @FXML
    public void shortestPath() {
        int a = Integer.parseInt(shortPathAField.getText());
        int b = Integer.parseInt(shortPathBField.getText());
        manager.getShortestPath(a, b);
    }

    @FXML
    public void removeUser() {
        int id = Integer.parseInt(removeUserField.getText());
        manager.removeUser(id);
        log("User " + id + " removed from the network.");
    }
}
