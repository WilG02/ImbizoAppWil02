package com.varsitycollege.imbizoappwil02;

public class User {

    private String userName;
    private String userEmail;
    private String userID;
    private String password;


    public User() {
    }

    public User(String userName, String userEmail, String userID, String password) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userID = userID;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
