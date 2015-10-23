package com.carlos.instagramclient.data;

import java.util.Date;
import java.util.List;

public class Photo {
    private String photoUrl;
    private int height;
    private Date date;
    private int likes;
    private String user;
    private String userProfileImageUrl;
    private List<String> comments;

    public Photo(String photoUrl, int height, Date date, int likes, String user, String userProfileImageUrl, List<String> comments) {
        this.photoUrl = photoUrl;
        this.height = height;
        this.date = date;
        this.likes = likes;
        this.user = user;
        this.userProfileImageUrl = userProfileImageUrl;
        this.comments = comments;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public int getHeight() {
        return height;
    }

    public Date getDate() {
        return date;
    }

    public int getLikes() {
        return likes;
    }

    public String getUser() {
        return user;
    }

    public String getUserProfileImageUrl() {
        return userProfileImageUrl;
    }

    public List<String> getComments() {
        return comments;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "photoUrl='" + photoUrl + '\'' +
                ", height=" + height +
                ", date=" + date +
                ", likes=" + likes +
                ", user='" + user + '\'' +
                ", userProfileImageUrl='" + userProfileImageUrl + '\'' +
                ", comments=" + comments +
                '}';
    }
}
