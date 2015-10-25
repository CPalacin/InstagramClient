package com.carlos.instagramclient.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.carlos.instagramclient.R;
import com.carlos.instagramclient.adapter.PhotoViewAdapter;
import com.carlos.instagramclient.data.Comment;
import com.carlos.instagramclient.data.Photo;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class PhotoViewList implements AdapterView.OnItemClickListener extends Fragment{
    public static final String POPULAR_URL = "https://api.instagram.com/v1/media/popular?client_id=";
    public static final String CLIENT_ID = "6b6289cb87ae492d84abd9a2e3793bd6";
    private static final int NUM_COMMENTS = 2;

    private  List<Photo> instagramPhotos = new ArrayList();
    private PhotoViewAdapter adapter;
    private SwipeRefreshLayout swipeContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_photo_view, container, false);



        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchPopularPhotos();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(R.color.primaryColor);


        // Retrieving the RecyclerView from the fragment layout
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv);

        // Setting a LinearLayoutManager as LayoutManager (Make it look like a ListView)
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        rv.setLayoutManager(linearLayoutManager);

        // Fetching new photos
        fetchPopularPhotos();

        adapter = new PhotoViewAdapter(instagramPhotos, getActivity());
        rv.setAdapter(adapter);

        return rootView;
    }

    private void fetchPopularPhotos() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(POPULAR_URL+CLIENT_ID, null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    instagramPhotos.clear();
                    JSONArray photosJSON = response.getJSONArray("data");
                    for (int i = 0; i < photosJSON.length(); i++) {
                        JSONObject photoJSON = photosJSON.getJSONObject(i);

                        JSONObject standardRes = photoJSON.getJSONObject("images")
                                                          .getJSONObject("standard_resolution");

                        String photoUrl = standardRes.getString("url");
                        int height = standardRes.getInt("height");
                        long created = photoJSON.getLong("created_time");

                        JSONObject user = photoJSON.getJSONObject("user");
                        int likes = photoJSON.getJSONObject("likes").getInt("count");
                        String userName = user.getString("username");
                        String profilePicture = user.getString("profile_picture");

                        JSONArray comments = photoJSON.getJSONObject("comments").getJSONArray("data");

                        List<Comment> commentList = new ArrayList<Comment>();

                        String caption = null;
                        if(!photoJSON.isNull("caption")) {
                            caption = photoJSON.getJSONObject("caption").getString("text");
                            Comment comment = new Comment(userName, caption);
                            commentList.add(comment);
                        }

                        commentList.addAll(getComments(comments, NUM_COMMENTS));

                        if(comments.length() > NUM_COMMENTS){
                            String numComments =  photoJSON.getJSONObject("comments").getString("count");
                            Comment comment = new Comment(null, "View all "+numComments+" comments");
                            commentList.add(comment);
                        }

                        Photo photo = new Photo(photoUrl, height, created, likes, userName,
                                profilePicture, commentList, caption);
                        instagramPhotos.add(photo);
                    }
                    swipeContainer.setRefreshing(false);

                }catch(JSONException e){
                    Log.e("ERROR", "Error parsing JSON", e);
                }

                adapter.notifyDataSetChanged();
            }
        });
    }

    @NonNull
    private List<Comment> getComments(JSONArray comments, int numComments) throws JSONException {
        List<Comment> commentList = new ArrayList<>(numComments);
        int max = (numComments > comments.length())?comments.length():numComments;
        for (int j = 0; j < max; j++) {
            JSONObject jsonComment = comments.getJSONObject(j);
            String user = jsonComment.getJSONObject("from").getString("username");
            String text = jsonComment.getString("text");
            Comment comment = new Comment(user, text);
            commentList.add(comment);
        }
        return commentList;
    }


    @Override
    public void onClick(View v) {
        commentListListener.commentList(String id)
    }


}
