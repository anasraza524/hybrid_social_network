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

    public void sendRequest(int from, int to) {
        friendRequests.sendRequest(from, to);
        System.out.println("Request from " + from + " to " + to + " sent.");
    }

    public void acceptRequest(int userId) {
        if (friendRequests.acceptRequest(userId, users)) {
            System.out.println("User " + userId + " accepted a friend request.");
        } else {
            System.out.println("No request available for user " + userId);
        }
    }

    public void buildSuggestionTree(int userId) {
        User user = users.get(userId);
        for (User friend : user.getFriends()) {
            suggestionTree.insert(friend.getId());
        }
        System.out.println("Suggestion tree built for user " + userId);
    }

    public String printSuggestionTree() {
        return suggestionTree.levelOrderPrint();
    }


    public void findLCA(int a, int b) {
        Integer lca = LCAUtil.findLCA(suggestionTree.getRoot(), a, b);
        System.out.println("LCA of " + a + " and " + b + " is " + lca);
    }

    public void getShortestPath(int a, int b) {
        int distance = graph.shortestPath(a, b);
        System.out.println("Shortest path from " + a + " to " + b + " is " + distance);
    }

    public void addFriendToGraph(int a, int b) {
        graph.addFriendship(a, b);
    }

    public void removeUser(int userId) {
        users.set(userId, null); // mark as removed
        graph.removeUser(userId);
        System.out.println("User " + userId + " removed.");
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public NetworkGraph getGraph() {
        return graph;
    }
}
