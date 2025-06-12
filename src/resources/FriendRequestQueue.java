package resources;

import java.util.LinkedList;
import java.util.Queue;
import java.util.List;

public class FriendRequestQueue {
    private Queue<int[]> requestQueue;

    public FriendRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void sendRequest(int from, int to) {
        requestQueue.add(new int[]{from, to});
    }

    public int[] getNextRequestFor(int userId) {
        for (int[] req : requestQueue) {
            if (req[1] == userId) {
                return req;
            }
        }
        return null;
    }

    public boolean acceptRequest(int userId, List<User> users) {
        int[] req = getNextRequestFor(userId);
        if (req != null) {
            User fromUser = users.get(req[0]);
            User toUser = users.get(req[1]);

            fromUser.addFriend(toUser);
            toUser.addFriend(fromUser);
            requestQueue.remove(req);
            return true;
        }
        return false;
    }
}
