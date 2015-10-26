package com.carlos.instagramclient.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.carlos.instagramclient.R;
import com.carlos.instagramclient.data.Photo;
import com.carlos.instagramclient.util.RecyclerItemClickListener;
import com.squareup.picasso.Picasso;

import org.solovyev.android.views.llm.LinearLayoutManager;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

// wrap_content doesn't work for the support LinearLayoutManager but this one works

public class PhotoViewAdapter extends RecyclerView.Adapter<PhotoViewAdapter.PhotoViewHolder>{

    private List<Photo> instagramPhotos;
    private Context context;
    private ViewCommentsListener viewCommentListener;

    public PhotoViewAdapter(List<Photo> instagramPhotos, Context context, ViewCommentsListener viewCommentListener){
        this.instagramPhotos = instagramPhotos;
        this.context = context;
        this.viewCommentListener = viewCommentListener;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new PhotoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, final int position) {
        holder.instagramPhoto.setImageDrawable(null);

        final Photo photo = instagramPhotos.get(position);
        String mediaUrl = photo.getPhotoUrl();
        if(photo.isVideo()){
            holder.instagramPhoto.setVisibility(View.GONE);
            holder.instagramVideo.setVisibility(View.VISIBLE);
            Log.i("VIDEO", mediaUrl);
            Log.i("VIDEO", photo.getUser());
            final VideoView mVideoView = holder.instagramVideo;
            mVideoView.setVideoPath(mediaUrl);
            MediaController mediaController = new MediaController(context);
            mediaController.setAnchorView(mVideoView);
            mVideoView.setMediaController(mediaController);
            mVideoView.requestFocus();
            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                // Close the progress bar and play the video
                public void onPrepared(MediaPlayer mp) {
                    mVideoView.start();
                }
            });
        } else {
            holder.instagramPhoto.setVisibility(View.VISIBLE);
            holder.instagramVideo.setVisibility(View.GONE);
            Picasso.with(context)
                    .load(mediaUrl)
                    .placeholder(R.drawable.progress_animation)
                    .into(holder.instagramPhoto);
        }
        String profileUrl = photo.getUserProfileImageUrl();
        Picasso.with(context).load(profileUrl).noFade().into(holder.profileImage);
        
        holder.user.setText(photo.getUser());
        holder.time.setText(photo.getTime());
        holder.likes.setText(photo.getLikes() + " likes"); //TODO move to an xml

        CommentsAdapter commentsAdapter = new CommentsAdapter(photo.getComments(), context);
        // Setting a LinearLayoutManager as LayoutManager (Make it look like a ListView)
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        holder.comments.setLayoutManager(linearLayoutManager);
        holder.comments.setAdapter(commentsAdapter);
        holder.comments.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int p) {

                        String id = instagramPhotos.get(position).getId();
                        viewCommentListener.viewComments(id);
                    }
                })
        );
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return instagramPhotos.size();
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView instagramPhoto;
        VideoView instagramVideo;
        CircleImageView profileImage;
        TextView user;
        TextView time;
        TextView likes;
        RecyclerView comments;

        private PhotoViewHolder(View itemView) {
            super(itemView);
            instagramPhoto = (ImageView) itemView.findViewById(R.id.instagram_photo);
            profileImage = (CircleImageView) itemView.findViewById(R.id.profile_image);
            user = (TextView) itemView.findViewById(R.id.user);
            time = (TextView) itemView.findViewById(R.id.time);
            likes = (TextView) itemView.findViewById(R.id.likes);
            comments = (RecyclerView) itemView.findViewById(R.id.rv_comments);
            instagramVideo = (VideoView) itemView.findViewById(R.id.instagram_video);
        }
    }

    public interface ViewCommentsListener{
        public void viewComments(String id);
    }
}
