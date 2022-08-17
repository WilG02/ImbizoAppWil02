package com.varsitycollege.imbizoappwil02;

public class User {

    private String userName;
    private String userEmail;
    private String userID;
    //private String userRole;

    public User() {
    }

    public User(String userName, String userEmail, String userID) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userID = userID;
        //this.userRole = userRole;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    /*public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }*/
}
