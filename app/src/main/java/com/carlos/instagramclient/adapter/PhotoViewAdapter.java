package com.carlos.instagramclient.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.carlos.instagramclient.R;
import com.carlos.instagramclient.data.Photo;

import java.util.List;

public class PhotoViewAdapter extends RecyclerView.Adapter<PhotoViewAdapter.PhotoViewHolder>{

    private List<Photo> instagramPhotos;

    public PhotoViewAdapter(List<Photo> instagramPhotos){
        this.instagramPhotos = instagramPhotos;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_photo, parent, false);
        PhotoViewHolder pvh = new PhotoViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return instagramPhotos.size();
    }

    // http://code.tutsplus.com/es/tutorials/getting-started-with-recyclerview-and-cardview-on-android--cms-23465
    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;

        private PhotoViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }
}
