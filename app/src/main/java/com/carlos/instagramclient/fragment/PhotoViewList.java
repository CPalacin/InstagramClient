package com.carlos.instagramclient.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carlos.instagramclient.R;
import com.carlos.instagramclient.data.Photo;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class PhotoViewList extends Fragment{
    public static final String POPULAR_URL = "https://api.instagram.com/v1/media/popular?client_id=";
    public static final String CLIENT_ID = "6b6289cb87ae492d84abd9a2e3793bd6";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_photo_view, container, false);

        // Retrieving the RecyclerView from the fragment layout
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv);

        // Setting a LinearLayoutManager as LayoutManager (Make it look like a ListView)
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        rv.setLayoutManager(linearLayoutManager);

        // Fetching new photos
        fetchPopularPhotos();
        return rootView;
    }

    private void fetchPopularPhotos() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(POPULAR_URL+CLIENT_ID, null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    JSONArray photosJSON = response.getJSONArray("data");
                    for (int i = 0; i < photosJSON.length(); i++) {
                        JSONObject photoJSON = photosJSON.getJSONObject(i);

                        JSONObject standardRes = photoJSON.getJSONObject("images")
                                                          .getJSONObject("standard_resolution");

                        String photoUrl = standardRes.getString("url");
                        int height = standardRes.getInt("height");
                        Date created = new Date(photoJSON.getLong("created_time"));

                        JSONObject user = photoJSON.getJSONObject("user");
                        int likes = photoJSON.getJSONObject("likes").getInt("count");
                        String userName = user.getString("username");
                        String profilePicture = user.getString("profile_picture");
                        JSONArray comments = photoJSON.getJSONObject("comments").getJSONArray("data");
                        List<String> commentList = new ArrayList<String>(comments.length());
                        for (int j = comments.length()-1; j >= 0; j--) {
                            JSONObject comment = comments.getJSONObject(j);
                            commentList.add(comment.getString("text"));
                        }

                        Photo photo = new Photo(photoUrl, height, created, likes, userName, profilePicture, commentList);
                        Log.i("PHOTO", "photo: " + photo);
                    }
                }catch(JSONException e){
                    Log.e("ERROR", "Error parsing JSON", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }
        });
    }
}
