package com.example.socialnet;

import java.util.ArrayList;

public class SocialNetList {
    private static ArrayList<SocialNetUsers> socNetList = new ArrayList<>();

    public SocialNetList() {

    }

    public void addName(String name){
        SocialNetUsers socNetUser = new SocialNetUsers(name, null, null, null, null);
        socNetList.add(socNetUser);
    }
    public void remove(int index){
        socNetList.remove(index);
    }
    public SocialNetUsers getUser(int index){
        return socNetList.get(index);
    }
    public int searchUser(String name){
        for (int i = 0; i < socNetList.size(); i++){
            if (socNetList.get(i).getName().equals(name)){
                return i;
            }
        }
        return -1;
    }
    public Boolean isDuplicateName(String name){
        for (int i = 0; i < socNetList.size(); i++){
            if (socNetList.get(i).getName().equals(name))
                return true;
        }
        return false;
    }
}
