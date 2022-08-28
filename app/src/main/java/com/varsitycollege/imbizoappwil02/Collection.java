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

    protected Collection(Parcel in) {
        CategoryId = in.readString();
        CategoryName = in.readString();
        CategoryInformation = in.readString();
        CategoryImageUrl = in.readString();
        CategoryVideoUrl = in.readString();
        CategoryPodcastUrl = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(CategoryId);
        parcel.writeString(CategoryName);
        parcel.writeString(CategoryInformation);
        parcel.writeString(CategoryImageUrl);
        parcel.writeString(CategoryVideoUrl);
        parcel.writeString(CategoryPodcastUrl);
    }
}
