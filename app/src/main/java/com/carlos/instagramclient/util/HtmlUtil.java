package com.carlos.instagramclient.util;

public class HtmlUtil {

    private HtmlUtil(){
        throw new UnsupportedOperationException("This is a static class and must not be instantiated");
    }

    public static String bold(String text){
        return "<b>"+text+"</b>";
    }

    public static String color(String text, String colorCode){
        String htmlColor = colorCode;
        if (colorCode.length() > 7){
            htmlColor = '#'+colorCode.substring(3);
        }
        return "<font color=\""+htmlColor+"\">"+text+"</font>";
    }

    public static String hashtagColor(String comment, String colorCode){
        return comment.replaceAll("#\\w+",color("$0", colorCode));
    }

    public static String atColor(String comment, String colorCode){
        return comment.replaceAll("@(\\w+)", color(bold("$1"), colorCode));
    }

}
