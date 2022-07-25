package com.varsitycollege.imbizoappwil02;

public class Collection {
    //Class Variables
    private String CollectionId;
    private String CollectionName;
    private String imageURl;
    private String videoURl;
    private String podcastURl;

    //Default Constructor
    public Collection() {

    }


    public Collection(String collectionId, String collectionName, String imageURl, String videoURl, String podcastURl) {
        CollectionId = collectionId;
        CollectionName = collectionName;
        this.imageURl = imageURl;
        this.videoURl = videoURl;
        this.podcastURl = podcastURl;
    }


    public String getCollectionId() {
        return CollectionId;
    }

    public void setCollectionId(String collectionId) {
        CollectionId = collectionId;
    }

    public String getCollectionName() {
        return CollectionName;
    }

    public void setCollectionName(String collectionName) {
        CollectionName = collectionName;
    }

    public String getImageURl() {
        return imageURl;
    }

    public void setImageURl(String imageURl) {
        this.imageURl = imageURl;
    }

    public String getVideoURl() {
        return videoURl;
    }

    public void setVideoURl(String videoURl) {
        this.videoURl = videoURl;
    }

    public String getPodcastURl() {
        return podcastURl;
    }

    public void setPodcastURl(String podcastURl) {
        this.podcastURl = podcastURl;
    }
}
