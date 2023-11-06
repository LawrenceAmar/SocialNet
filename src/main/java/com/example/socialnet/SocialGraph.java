package com.example.socialnet;

import java.util.*;

public class SocialGraph {
    private Map<String, SocialNetUsers> graph;

    public SocialGraph() {
        graph = new HashMap<>();
    }

    public void addUser(String userName) {
        SocialNetUsers socNetUser = new SocialNetUsers(userName, null, null, new ArrayList<>(), null);
        graph.put(userName, socNetUser);
    }

    public void addFriend(String user1, String user2) {
        graph.get(user1).getFriends().add(user2);
        graph.get(user2).getFriends().add(user1);
    }

    public void removeFriend(String user1, String user2) {
        graph.get(user1).getFriends().remove(user2);
        graph.get(user2).getFriends().remove(user1);
    }

    public List<String> getFriends(String userName) {
        return graph.get(userName).getFriends();
    }

    public boolean hasUser(String userName) {
        return graph.containsKey(userName);
    }
    public SocialNetUsers getUser (String username) {
        return graph.get(username);
    }

    public void removeUser(String userName) {
        if (graph.containsKey(userName)) {
            // Remove the user from the graph and their connections
            List<String> friends = graph.get(userName).getFriends();
            for (String friend : friends) {
                graph.get(friend).getFriends().remove(userName);
            }
            graph.remove(userName);
        }
    }
    // Other graph-related methods can be added as needed.
}
