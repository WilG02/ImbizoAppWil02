package com.varsitycollege.imbizoappwil02;

import android.os.Parcel;
import android.os.Parcelable;

public class Collection implements Parcelable {
    //Class Variables
    private String CategoryId;
    private String CategoryName;
    private String CategoryInformation;
    private String CategoryImageUrl;
    private String CategoryVideoUrl;
    private String CategoryPodcastUrl;
    private String CategoryImageName;
    private String CategoryVideoName;
    private String CategoryPodcastName;

    //Default Constructor
    public Collection() {

    }

    public Collection(String categoryId, String categoryName, String categoryInformation, String categoryImageUrl, String categoryVideoUrl, String categoryPodcastUrl) {
        CategoryId = categoryId;
        CategoryName = categoryName;
        CategoryInformation = categoryInformation;
        CategoryImageUrl = categoryImageUrl;
        CategoryVideoUrl = categoryVideoUrl;
        CategoryPodcastUrl = categoryPodcastUrl;
    }


    public Collection(String categoryId, String categoryName, String categoryInformation, String categoryImageUrl, String categoryVideoUrl, String categoryPodcastUrl, String categoryImageName, String categoryVideoName, String categoryPodcastName) {
        CategoryId = categoryId;
        CategoryName = categoryName;
        CategoryInformation = categoryInformation;
        CategoryImageUrl = categoryImageUrl;
        CategoryVideoUrl = categoryVideoUrl;
        CategoryPodcastUrl = categoryPodcastUrl;
        CategoryImageName = categoryImageName;
        CategoryVideoName = categoryVideoName;
        CategoryPodcastName = categoryPodcastName;
    }


    protected Collection(Parcel in) {
        CategoryId = in.readString();
        CategoryName = in.readString();
        CategoryInformation = in.readString();
        CategoryImageUrl = in.readString();
        CategoryVideoUrl = in.readString();
        CategoryPodcastUrl = in.readString();
        CategoryImageName = in.readString();
        CategoryVideoName = in.readString();
        CategoryPodcastName = in.readString();
    }

    public static final Creator<Collection> CREATOR = new Creator<Collection>() {
        @Override
        public Collection createFromParcel(Parcel in) {
            return new Collection(in);
        }

        @Override
        public Collection[] newArray(int size) {
            return new Collection[size];
        }
    };

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getCategoryInformation() {
        return CategoryInformation;
    }

    public void setCategoryInformation(String categoryInformation) {
        CategoryInformation = categoryInformation;
    }

    public String getCategoryImageUrl() {
        return CategoryImageUrl;
    }

    public void setCategoryImageUrl(String categoryImageUrl) {
        CategoryImageUrl = categoryImageUrl;
    }

    public String getCategoryVideoUrl() {
        return CategoryVideoUrl;
    }

    public void setCategoryVideoUrl(String categoryVideoUrl) {
        CategoryVideoUrl = categoryVideoUrl;
    }

    public String getCategoryPodcastUrl() {
        return CategoryPodcastUrl;
    }

    public void setCategoryPodcastUrl(String categoryPodcastUrl) {
        CategoryPodcastUrl = categoryPodcastUrl;
    }

    public String getCategoryImageName() {
        return CategoryImageName;
    }

    public void setCategoryImageName(String categoryImageName) {
        CategoryImageName = categoryImageName;
    }

    public String getCategoryVideoName() {
        return CategoryVideoName;
    }

    public void setCategoryVideoName(String categoryVideoName) {
        CategoryVideoName = categoryVideoName;
    }

    public String getCategoryPodcastName() {
        return CategoryPodcastName;
    }

    public void setCategoryPodcastName(String categoryPodcastName) {
        CategoryPodcastName = categoryPodcastName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(CategoryId);
        dest.writeString(CategoryName);
        dest.writeString(CategoryInformation);
        dest.writeString(CategoryImageUrl);
        dest.writeString(CategoryVideoUrl);
        dest.writeString(CategoryPodcastUrl);
        dest.writeString(CategoryImageName);
        dest.writeString(CategoryVideoName);
        dest.writeString(CategoryPodcastName);
    }
}
