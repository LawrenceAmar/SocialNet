package com.example.socialnet;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
    private SocialNetList socNetList;
    private String currentProfile = null;

    @FXML
    protected void initialize(){
        friendList = FXCollections.observableArrayList();
        friendArray = new ArrayList<>();
        socNetList = new SocialNetList();
        listView.setItems(friendList);
    }
    @FXML
    protected void handleAdd() {
        try{
            if (!nameField.getText().trim().isEmpty()) {
                if (!socNetList.isDuplicateName((nameField.getText().toLowerCase()))){
                    socNetList.addName(nameField.getText().toLowerCase());
                    statusLabel.setText(nameField.getText() + " added");
                }
                else{
                    statusLabel.setText(nameField.getText() + " already added");
                    nameField.setText(null);
                }
            }
            else{
                statusLabel.setText("input a name");
            }
        }
        catch (NullPointerException e){
            statusLabel.setText("input a name");
        }
    }
    @FXML
    protected void handleDelete() {
        try{
            if (!nameField.getText().trim().isEmpty()) {
                int index = socNetList.searchUser(nameField.getText().toLowerCase());
                if (index >= 0){
                    socNetList.remove(index);
                    nameLabel.setText(null);
                    currentStatus.setText(null);
                    pictureView.setImage(null);
                    favoriteQuote.setText(null);
                    statusChangeLabel.setText(null);
                    pictureLabel.setText(null);
                    quoteLabel.setText(null);
                    statusLabel.setText(nameField.getText() + " deleted");
                    nameField.setText(null);
                }
                else{
                    statusLabel.setText(nameField.getText() + " is not yet added");
                }
            }
            else{
                statusLabel.setText("input a name");
            }
        }
        catch (NullPointerException e){
            statusLabel.setText("input a name");
        }
    }
    @FXML
    protected void handleLookup() {
        try{
            if (!nameField.getText().trim().isEmpty()) {
                int index = socNetList.searchUser(nameField.getText().toLowerCase());
                if (index >= 0){
                    SocialNetUsers user = socNetList.getUser(index);
                    currentProfile = nameField.getText().toLowerCase();
                    nameLabel.setText(user.getName());
                    currentStatus.setText(user.getStatus());
                    if (socNetList.getUser(index).getPicture() != null){
                        pictureView.setImage(new Image(user.getPicture()));
                    }
                    else{
                        pictureView.setImage(null);
                    }
                    if (user.getFriends() != null){
                        friendList.clear();
                        String[] temp = user.getFriends().substring(1, user.getFriends().length()-1).split(", ");
                        friendArray = new ArrayList<>(Arrays.asList(temp));
                        friendList = FXCollections.observableArrayList(friendArray);
                    }
                    else{
                        friendList.clear();
                        friendArray.clear();
                        System.out.println(friendList.toString());
                    }
                    listView.setItems(friendList);
                    favoriteQuote.setText(user.getQuote());
                    statusLabel.setText(nameField.getText() + " lookup");
                    nameField.setText(null);
                }
                else{
                    statusLabel.setText(nameField.getText() + " is not yet added");
                }
            }
            else{
                statusLabel.setText("input a name");
            }
        }
        catch (NullPointerException e){
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
    protected void handleAddFriend() {
        try{
            if (currentProfile != null) {
                if (!friendLabel.getText().trim().isEmpty()) {
                    if (!friendLabel.getText().toLowerCase().equals(currentProfile)){
                        if (socNetList.searchUser(friendLabel.getText()) >= 0) {
                            if (!friendArray.contains(friendLabel.getText())){
                                SocialNetUsers user = socNetList.getUser(socNetList.searchUser(currentProfile));
                                SocialNetUsers added = socNetList.getUser(socNetList.searchUser(friendLabel.getText().toLowerCase()));
                                friendList.add(friendLabel.getText());
                                friendArray.add(friendLabel.getText());
                                user.setFriends(friendArray.toString());
                                if (added.getFriends() != null){
                                    String[] temp = added.getFriends().substring(1, added.getFriends().length()-1).split(", ");
                                    ArrayList<String> tempArray = new ArrayList<>(Arrays.asList(temp));
                                    tempArray.add(currentProfile);
                                    added.setFriends(tempArray.toString());
                                }
                                else {
                                    ArrayList<String> tempArray = new ArrayList<>();
                                    tempArray.add(currentProfile);
                                    added.setFriends(tempArray.toString());
                                }
                                listView.setItems(friendList);
                                statusLabel.setText(friendLabel.getText() + " added as friend");
                            } else {
                                statusLabel.setText(friendLabel.getText() + " is already added");
                            }
                        } else {
                            statusLabel.setText(friendLabel.getText() + " has no profile");
                        }
                    } else {
                        statusLabel.setText("cannot add yourself");
                    }
                } else {
                    statusLabel.setText("input a name to add");
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
        try{
            if (currentProfile != null){
                if (!unfriendLabel.getText().trim().isEmpty()) {
                    if (!unfriendLabel.getText().toLowerCase().equals(currentProfile)){
                        if (socNetList.searchUser(unfriendLabel.getText()) >= 0) {
                            if (friendArray.contains(unfriendLabel.getText())) {
                                SocialNetUsers user = socNetList.getUser(socNetList.searchUser(currentProfile));
                                SocialNetUsers removed = socNetList.getUser(socNetList.searchUser(unfriendLabel.getText()));
                                friendList.remove(unfriendLabel.getText());
                                friendArray.remove(unfriendLabel.getText());
                                user.setFriends(friendArray.toString());
                                if (removed.getFriends() != null){
                                    String[] temp = removed.getFriends().substring(1, removed.getFriends().length()-1).split(", ");
                                    ArrayList<String> tempArray = new ArrayList<>(Arrays.asList(temp));
                                    tempArray.remove(currentProfile);
                                    removed.setFriends(tempArray.toString());
                                }
                                else {
                                    ArrayList<String> tempArray = new ArrayList<>();
                                    tempArray.remove(currentProfile);
                                    removed.setFriends(tempArray.toString());
                                }
                                listView.setItems(friendList);
                                statusLabel.setText(unfriendLabel.getText() + " removed as friend");
                            } else {
                                statusLabel.setText("cannot find " + unfriendLabel.getText());
                            }
                        } else {
                            statusLabel.setText(unfriendLabel.getText() + " has no profile");
                        }
                    } else{
                        statusLabel.setText("cannot unfriend yourself");
                    }
                }
                else{
                    statusLabel.setText("input a name to unfriend");
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
