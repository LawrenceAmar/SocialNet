package com.example.socialnet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SocialGraph {
    private Map<String, Set<String>> graph;

    public SocialGraph() {
        graph = new HashMap<>();
    }

    public void addUser(String userName) {
        if (!graph.containsKey(userName)) {
            graph.put(userName, new HashSet<>());
        }
    }

    public void addFriend(String user1, String user2) {
        graph.get(user1).add(user2);
        graph.get(user2).add(user1);
    }

    public void removeFriend(String user1, String user2) {
        graph.get(user1).remove(user2);
        graph.get(user2).remove(user1);
    }

    public Set<String> getFriends(String userName) {
        return graph.get(userName);
    }

    public boolean hasUser(String userName) {
        return graph.containsKey(userName);
    }

    public void removeUser(String userName) {
        if (graph.containsKey(userName)) {
            // Remove the user from the graph and their connections
            Set<String> friends = graph.get(userName);
            for (String friend : friends) {
                graph.get(friend).remove(userName);
            }
            graph.remove(userName);
        }
    }


    // Other graph-related methods can be added as needed.
}
