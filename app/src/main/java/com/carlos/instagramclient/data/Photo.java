package com.carlos.instagramclient.data;

import java.util.List;

public class Photo {
    private String photoUrl;
    private int height;
    private long created;
    private int likes;
    private String user;
    private String userProfileImageUrl;
    private List<Comment> comments;
    private String caption;



    public Photo(String photoUrl, int height, long created, int likes, String user, String userProfileImageUrl, List<Comment> comments, String caption) {
        this.photoUrl = photoUrl;
        this.height = height;
        this.created = created;
        this.likes = likes;
        this.user = user;
        this.userProfileImageUrl = userProfileImageUrl;
        this.comments = comments;
        this.caption = caption;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public int getHeight() {
        return height;
    }

    public String getTime() {
        long now = System.currentTimeMillis()/1000;
        long timeDiff = now - created;

        String time = null;
        if(timeDiff < 60){
            time = (timeDiff) + "s";
        }else if (timeDiff / 60 < 60){
            time = (timeDiff / 60) + "m";
        }else if (timeDiff / (60 * 60) < 24) {
            time = (timeDiff / (60 * 60)) + "h";
        }else{ // And so on..
            time = (timeDiff / (60 * 60 * 24)) + "d";
        }
        return (timeDiff < 0)? "0s" : time;
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

    public List<Comment> getComments() {
        return comments;
    }

    public String getCaption() {
        return caption;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "photoUrl='" + photoUrl + '\'' +
                ", height=" + height +
                ", created=" + created +
                ", likes=" + likes +
                ", user='" + user + '\'' +
                ", userProfileImageUrl='" + userProfileImageUrl + '\'' +
                ", comments=" + comments +
                '}';
    }
}
