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
