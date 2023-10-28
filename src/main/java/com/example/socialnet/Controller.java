package com.example.socialnet;

import com.example.socialnet.SocialGraph;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List; // Import List

public class Controller {
    @FXML
    public TextField nameField;
    @FXML
    public Label statusLabel;
    @FXML
    public TextField statusChangeLabel;
    @FXML
    public TextField pictureLabel;
    @FXML
    public TextField friendLabel;
    @FXML
    public TextField unfriendLabel;
    @FXML
    public TextField quoteLabel;
    @FXML
    public ImageView pictureView;
    @FXML
    public ListView listView;
    @FXML
    public Label currentStatus;
    @FXML
    public Label favoriteQuote;
    @FXML
    public Label nameLabel;

    ObservableList<String> friendList;
    private ArrayList<String> friendArray;
    private SocialNetList socNetList = new SocialNetList();  // Create an instance of SocialNetList
    private String currentProfile = null;
    private SocialGraph socialGraph; // Make sure you import the SocialGraph class.

    @FXML
    protected void initialize(){
        friendList = FXCollections.observableArrayList();
        friendArray = new ArrayList<>();
        socialGraph = new SocialGraph();
        listView.setItems(friendList);
    }
    @FXML
    protected void handleAdd() {
        try {
            if (!nameField.getText().trim().isEmpty()) {
                String userName = nameField.getText().toLowerCase();
                if (!socialGraph.hasUser(userName)) {
                    socialGraph.addUser(userName);
                    statusLabel.setText(nameField.getText() + " added");
                } else {
                    statusLabel.setText(nameField.getText() + " already added");
                    nameField.setText(null);
                }
            } else {
                statusLabel.setText("input a name");
            }
        } catch (NullPointerException e) {
            statusLabel.setText("input a name");
        }
    }

    @FXML
    protected void handleDelete() {
        try {
            if (!nameField.getText().trim().isEmpty()) {
                String userName = nameField.getText().toLowerCase();
                if (socialGraph.hasUser(userName)) {
                    // Remove the user and update the graph
                    socialGraph.removeUser(userName);
                    // Rest of your code to clear user information...
                    statusLabel.setText(nameField.getText() + " deleted");
                    nameField.setText(null);
                } else {
                    statusLabel.setText(nameField.getText() + " is not yet added");
                }
            } else {
                statusLabel.setText("input a name");
            }
        } catch (NullPointerException e) {
            statusLabel.setText("input a name");
        }
    }

    @FXML
    protected void handleAddFriend() {
        try {
            if (currentProfile != null) {
                if (!friendLabel.getText().trim().isEmpty()) {
                    String friendName = friendLabel.getText().toLowerCase();
                    if (socialGraph.hasUser(friendName)) {
                        // Add a friend connection in the graph
                        socialGraph.addFriend(currentProfile, friendName);
                        // Rest of your code...
                        statusLabel.setText(friendLabel.getText() + " added as friend");
                    } else {
                        statusLabel.setText(friendLabel.getText() + " has no profile");
                    }
                } else {
                    statusLabel.setText("input a name to add");
                }
            } else {
                statusLabel.setText("use lookup to edit a profile");
            }
        } catch (NullPointerException e) {
            statusLabel.setText("use lookup to edit a profile");
        }
    }
    @FXML
    protected void handleLookup() {
        try {
            if (!nameField.getText().trim().isEmpty()) {
                int index = socNetList.searchUser(nameField.getText().toLowerCase());
                if (index >= 0) {
                    SocialNetUsers user = socNetList.getUser(index);
                    currentProfile = nameField.getText().toLowerCase();
                    nameLabel.setText(user.getName());
                    currentStatus.setText(user.getStatus());
                    if (socNetList.getUser(index).getPicture() != null) {
                        pictureView.setImage(new Image(user.getPicture()));
                    } else {
                        pictureView.setImage(null);
                    }
                    // Handle friends as a List<String>
                    if (user.getFriends() != null) {
                        List<String> friendListData = user.getFriends();
                        friendArray = new ArrayList<>(friendListData);
                        friendList.clear(); // Use the class-level friendList here
                        friendList.addAll(friendArray);
                    }

                else {
                        friendList.clear(); // Use the class-level friendList here
                        friendArray.clear();
                        System.out.println(friendList.toString());
                    }


                    listView.setItems(friendList); // Use the class-level friendList here
                    favoriteQuote.setText(user.getQuote());
                    statusLabel.setText(nameField.getText() + " lookup");
                    nameField.setText(null);
                } else {
                    statusLabel.setText(nameField.getText() + " is not yet added");
                }
            } else {
                statusLabel.setText("input a name");
            }
        } catch (NullPointerException e) {
            statusLabel.setText("input a name");
        }
    }

    @FXML
    protected void handleStatus() {
        try{
            if (currentProfile != null){
                if (!statusChangeLabel.getText().trim().isEmpty()) {
                    int index = socNetList.searchUser(currentProfile);
                    socNetList.getUser(index).setStatus(statusChangeLabel.getText());
                    currentStatus.setText(statusChangeLabel.getText());
                    statusLabel.setText("status changed to " + statusChangeLabel.getText());
                }
                else{
                    statusLabel.setText("input a status message");
                }
            }
            else{
                statusLabel.setText("use lookup to edit a profile");
            }
        }
        catch (NullPointerException e){
            statusLabel.setText("use lookup to edit a profile");
        }
    }
    @FXML
    protected void handlePicture() {
        try{
            if (currentProfile != null) {
                if (!pictureLabel.getText().trim().isEmpty()) {
                    try {
                        int index = socNetList.searchUser(currentProfile);
                        socNetList.getUser(index).setPicture(pictureLabel.getText());
                        pictureView.setImage(new Image(pictureLabel.getText()));
                        statusLabel.setText("picture changed to " + pictureLabel.getText());
                    } catch (IllegalArgumentException e) {
                        statusLabel.setText("cannot find image");
                    }
                }
                else{
                    statusLabel.setText("input an image url");
                }
            }
            else{
                statusLabel.setText("use lookup to edit a profile");
            }
        }
        catch (NullPointerException e){
            statusLabel.setText("use lookup to edit a profile");
        }
    }

    @FXML
    protected void handleUnfriend() {
        try {
            if (currentProfile != null) {
                if (!unfriendLabel.getText().trim().isEmpty()) {
                    if (!unfriendLabel.getText().toLowerCase().equals(currentProfile)) {
                        if (socNetList.searchUser(unfriendLabel.getText()) >= 0) {
                            if (friendArray.contains(unfriendLabel.getText())) {
                                SocialNetUsers user = socNetList.getUser(socNetList.searchUser(currentProfile));
                                SocialNetUsers removed = socNetList.getUser(socNetList.searchUser(unfriendLabel.getText()));

                                friendList.remove(unfriendLabel.getText());
                                friendArray.remove(unfriendLabel.getText());

                                // Set the updated friends list for the user
                                user.setFriends(friendArray);

                                if (removed.getFriends() != null) {
                                    List<String> removedFriendsList = removed.getFriends();
                                    removedFriendsList.remove(currentProfile);
                                    // Set the updated friends list for the removed user
                                    removed.setFriends(removedFriendsList);
                                } else {
                                    // Set an empty list for the removed user
                                    removed.setFriends(new ArrayList<>());
                                }

                                listView.setItems(friendList);
                                statusLabel.setText(unfriendLabel.getText() + " removed as a friend");
                            } else {
                                statusLabel.setText("cannot find " + unfriendLabel.getText());
                            }
                        } else {
                            statusLabel.setText(unfriendLabel.getText() + " has no profile");
                        }
                    } else {
                        statusLabel.setText("cannot unfriend yourself");
                    }
                } else {
                    statusLabel.setText("input a name to unfriend");
                }
            } else {
                statusLabel.setText("use lookup to edit a profile");
            }

        } catch (NullPointerException e) {
            statusLabel.setText("use lookup to edit a profile");
        }
    }
    @FXML
    protected void handleQuote() {
        try{
            if (currentProfile != null){
                if (!quoteLabel.getText().trim().isEmpty()) {
                    int index = socNetList.searchUser(currentProfile);
                    socNetList.getUser(index).setQuote(quoteLabel.getText());
                    favoriteQuote.setText(quoteLabel.getText());
                    statusLabel.setText("Quote changed to " + quoteLabel.getText());
                }
                else{
                    statusLabel.setText("input a quote");
                }
            }
            else{
                statusLabel.setText("use lookup to edit a profile");
            }
        }
        catch (NullPointerException e){
            statusLabel.setText("use lookup to edit a profile");
        }
    }
}
