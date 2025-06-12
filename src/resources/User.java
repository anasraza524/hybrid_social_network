package resources;

import java.util.LinkedList;

public class User {
    private int id;
    private String name;
    private LinkedList<User> friends;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
        this.friends = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LinkedList<User> getFriends() {
        return friends;
    }

    public void addFriend(User user) {
        if (!friends.contains(user)) {
            friends.add(user);
        }
    }

    public void removeFriend(User user) {
        friends.remove(user);
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "'}";
    }
}
