package com.carlos.instagramclient.data;

import java.util.List;

public class Photo {
    private boolean isVideo;
    private String id;
    private String photoUrl;
    private long created;
    private int likes;
    private String user;
    private String userProfileImageUrl;
    private String caption;
    private List<Comment> comments;

    public Photo(boolean isVideo, String id, String photoUrl, long created, int likes, String user, String userProfileImageUrl, List<Comment> comments, String caption) {
        this.isVideo = isVideo;
        this.id = id;
        this.photoUrl = photoUrl;
        this.created = created;
        this.likes = likes;
        this.user = user;
        this.userProfileImageUrl = userProfileImageUrl;
        this.comments = comments;
        this.caption = caption;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public String getId() {
        return id;
    }

    public String getPhotoUrl() {
        return photoUrl;
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

}
