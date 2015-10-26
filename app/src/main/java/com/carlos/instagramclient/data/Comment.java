package com.carlos.instagramclient.data;

import static com.carlos.instagramclient.util.HtmlUtil.atColor;
import static com.carlos.instagramclient.util.HtmlUtil.bold;
import static com.carlos.instagramclient.util.HtmlUtil.color;
import static com.carlos.instagramclient.util.HtmlUtil.hashtagColor;

public class Comment {

    private String user;
    private String comment;

    public Comment(String user, String comment) {
        this.user = user;
        this.comment = comment;
    }

    public String getComment(String colorCode) {
        String finalComment = null;
        if(user != null){
            finalComment = color(bold(user), colorCode) + " " +
                           atColor(hashtagColor(comment, colorCode), colorCode);
        }else{
            finalComment = color(comment, "#bebebe");
        }

        return finalComment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "user='" + user + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
