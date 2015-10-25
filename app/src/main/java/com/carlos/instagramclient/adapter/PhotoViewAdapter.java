package com.carlos.instagramclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.carlos.instagramclient.R;
import com.carlos.instagramclient.data.Photo;
import com.squareup.picasso.Picasso;

import org.solovyev.android.views.llm.LinearLayoutManager;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

// wrap_content doesn't work for the support LinearLayoutManager but this one works

public class PhotoViewAdapter extends RecyclerView.Adapter<PhotoViewAdapter.PhotoViewHolder>{

    private List<Photo> instagramPhotos;
    private Context context;

    public PhotoViewAdapter(List<Photo> instagramPhotos, Context context){
        this.instagramPhotos = instagramPhotos;
        this.context = context;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new PhotoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        holder.instagramPhoto.setImageDrawable(null);

        final Photo photo = instagramPhotos.get(position);
        String photoUrl = photo.getPhotoUrl();
        Picasso.with(context)
               .load(photoUrl)
               .placeholder(R.drawable.progress_animation)
               .into(holder.instagramPhoto);

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
        }
    }
}
