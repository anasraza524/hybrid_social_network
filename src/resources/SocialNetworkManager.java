package resources;

import java.util.ArrayList;

public class SocialNetworkManager {
    private ArrayList<User> users;
    private FriendRequestQueue friendRequests;
    private FriendSuggestionTree suggestionTree;
    private NetworkGraph graph;

    public SocialNetworkManager(int size) {
        users = new ArrayList<>();
        friendRequests = new FriendRequestQueue();
        suggestionTree = new FriendSuggestionTree();
        graph = new NetworkGraph();

        for (int i = 0; i < size; i++) {
            users.add(new User(i, "User" + i));
            graph.addUser(i);
        }
    }

    // Send friend request from one user to another
    public void sendRequest(int from, int to) {
        friendRequests.sendRequest(from, to);
        System.out.println("Request from " + from + " to " + to + " sent.");
    }

    // Accept friend request if available for the user
    public void acceptRequest(int userId) {
        if (friendRequests.acceptRequest(userId, users)) {
            System.out.println("User " + userId + " accepted a friend request.");
        } else {
            System.out.println("No request available for user " + userId);
        }
    }

    // Build the friend suggestion tree for a user
    public void buildSuggestionTree(int userId) {
        if (userId >= users.size() || users.get(userId) == null) {
            System.out.println("Invalid user ID or user removed.");
            return;
        }

        suggestionTree.clear(); // clear previous tree if needed
        User user = users.get(userId);

        for (User friend : user.getFriends()) {
            suggestionTree.insert(friend.getId());
        }

        System.out.println("Suggestion tree built for user " + userId);
    }

    // Print the suggestion tree level-order
    public String printSuggestionTree() {
        return suggestionTree.levelOrderPrint();
    }

    // Find Lowest Common Ancestor between two nodes in the suggestion tree
    public Integer findLCA(int a, int b) {
        return LCAUtil.findLCA(suggestionTree.getRoot(), a, b);
    }

    // Get shortest path between two users in the graph
    public int getShortestPath(int a, int b) {
        return graph.shortestPath(a, b);
    }

    // Add an edge between two users (mutual friendship)
    public void addFriendToGraph(int a, int b) {
        graph.addFriendship(a, b);
    }

    // Remove user from the system
    public void removeUser(int userId) {
        if (userId >= users.size() || users.get(userId) == null) {
            System.out.println("Invalid or already removed user.");
            return;
        }

        users.set(userId, null); // mark as removed
        graph.removeUser(userId);
        System.out.println("User " + userId + " removed.");
    }

    // Get list of all users
    public ArrayList<User> getUsers() {
        return users;
    }

    // Return visual graph as string for GUI display
    public String getVisualGraph() {
        return graph.getVisualGraphAsString();
    }

    // Return the internal graph object
    public NetworkGraph getGraph() {
        return graph;
    }
}
