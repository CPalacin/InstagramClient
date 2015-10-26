package com.carlos.instagramclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carlos.instagramclient.R;
import com.carlos.instagramclient.data.Comment;

import java.util.List;


public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentHolder>{

    private List<Comment> comments;
    private Context context;

    public CommentsAdapter(List<Comment> comments, Context context) {
        this.context = context;
        this.comments = comments;
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentHolder(v);
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {
        //noinspection ResourceType
        String primaryColor = context.getResources().getString(R.color.primaryColor);
        holder.comment.setText(Html.fromHtml(comments.get(position).getComment(primaryColor)));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class CommentHolder extends RecyclerView.ViewHolder {
        public TextView comment;

        private CommentHolder(View itemView) {
            super(itemView);
            comment = (TextView)itemView.findViewById(R.id.comment);
        }
    }

}