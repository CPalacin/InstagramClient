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
import com.carlos.instagramclient.adapter.CommentsAdapter;
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

public class CommentList extends Fragment {
    public static final String COMMENTS_URL = "https://api.instagram.com/v1/media/{media-id}/comments?client_id=";
    public static final String CLIENT_ID = "6b6289cb87ae492d84abd9a2e3793bd6";

    private static final String ID = "ID";
    private String id;
    private List<Comment> comments = new ArrayList<>();
    private CommentsAdapter adapter;

    public static CommentList newInstance(String id) {
        CommentList f = new CommentList();
        Bundle args = new Bundle();
        args.putString(ID, id);
        Log.i("ID", "id "+id);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        id = getArguments().getString(ID);
        Log.i("ID", "id " + id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_comment, container, false);

        // Retrieving the RecyclerView from the fragment layout
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_comment_list);

        // Setting a LinearLayoutManager as LayoutManager (Make it look like a ListView)
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        rv.setLayoutManager(linearLayoutManager);

        // Fetching new photos
        fetchComments();

        adapter = new CommentsAdapter(comments, getActivity());
        rv.setAdapter(adapter);

        return rootView;
    }

    private void fetchComments() {
        AsyncHttpClient client = new AsyncHttpClient();
        String endpointUrl = COMMENTS_URL.replace("{media-id}", id) + CLIENT_ID;
        Log.i("endpoint", endpointUrl);
        client.get(endpointUrl, null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    comments.clear();
                    JSONArray commentsJson = response.getJSONArray("data");
                    for (int i = 0; i < commentsJson.length(); i++) {
                        JSONObject commentJson = commentsJson.getJSONObject(i);

                        String user = commentJson.getJSONObject("from").getString("username");
                        String text = commentJson.getString("text");

                        comments.add(new Comment(user, text));
                    }

                }catch(JSONException e){
                    Log.e("ERROR", "Error parsing JSON", e);
                }

                adapter.notifyDataSetChanged();
            }
        });
    }
}
