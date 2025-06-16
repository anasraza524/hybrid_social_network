package resources;

import java.util.*;

public class NetworkGraph {
    private Map<Integer, List<Integer>> adjList;

    public NetworkGraph() {
        adjList = new HashMap<>();
    }

    public void addUser(int id) {
        adjList.putIfAbsent(id, new ArrayList<>());
    }

    public void addFriendship(int u1, int u2) {
        adjList.get(u1).add(u2);
        adjList.get(u2).add(u1);
    }

    public void removeUser(int id) {
        if (!adjList.containsKey(id)) return;
        for (int neighbor : adjList.get(id)) {
            adjList.get(neighbor).remove((Integer) id);
        }
        adjList.remove(id);
    }
   public String getVisualGraphAsString() {
    StringBuilder sb = new StringBuilder();

    sb.append("🔗 Virtual Graph View:\n");
    for (int user : adjList.keySet()) {
        sb.append("User ").append(user).append(" → ");
        for (int friend : adjList.get(user)) {
            sb.append(friend).append("  ");
        }
        sb.append("\n");
    }

    sb.append("\n📌 Structure Preview (tree style):\n");

    // Dynamic visualization using BFS (like tree)
    Set<Integer> visited = new HashSet<>();
    Queue<Integer> queue = new LinkedList<>();
    Map<Integer, Integer> levelMap = new HashMap<>();

    if (adjList.isEmpty()) {
        sb.append("Empty graph.\n");
        return sb.toString();
    }

    int root = adjList.keySet().iterator().next();  // start from any node
    queue.offer(root);
    visited.add(root);
    levelMap.put(root, 0);

    Map<Integer, List<Integer>> levels = new TreeMap<>();
    while (!queue.isEmpty()) {
        int current = queue.poll();
        int level = levelMap.get(current);

        levels.putIfAbsent(level, new ArrayList<>());
        levels.get(level).add(current);

        for (int neighbor : adjList.get(current)) {
            if (!visited.contains(neighbor)) {
                queue.offer(neighbor);
                visited.add(neighbor);
                levelMap.put(neighbor, level + 1);
            }
        }
    }

    // Print levels (like tree layers)
    for (int lvl : levels.keySet()) {
        sb.append("Level ").append(lvl).append(": ");
        for (int node : levels.get(lvl)) {
            sb.append(node).append(" ");
        }
        sb.append("\n");
    }

    return sb.toString();
}


    public int shortestPath(int start, int end) {
        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> distance = new HashMap<>();
        Set<Integer> visited = new HashSet<>();

        queue.offer(start);
        distance.put(start, 0);
        visited.add(start);

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            if (curr == end) return distance.get(curr);

            for (int neighbor : adjList.get(curr)) {
                if (!visited.contains(neighbor)) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                    distance.put(neighbor, distance.get(curr) + 1);
                }
            }
        }

        return -1;
    }

    public Map<Integer, List<Integer>> getGraph() {
        return adjList;
    }
}
