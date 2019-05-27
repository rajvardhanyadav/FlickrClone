package com.example.flickr_clone.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.flickr_clone.R;
import com.example.flickr_clone.data.model.Photo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {
    private List<Photo> photos=new ArrayList<>();
    private PhotoCallback photoCallback;

    public void renderPhotos(List<Photo> photos){
        this.photos.clear();
        this.photos.addAll(photos);
        notifyDataSetChanged();
    }

    public PhotosAdapter(PhotoCallback photoCallback){
        this.photoCallback=photoCallback;
    }

    @NonNull
    @Override
    public PhotosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_photos, viewGroup, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosAdapter.ViewHolder viewHolder, int i) {
//        viewHolder.imageView.setImageURI(Uri.parse(photos.get(i).getUrlM()));
        viewHolder.bind(photos.get(i));
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textViewDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView=itemView.findViewById(R.id.ivPhoto);
            this.textViewDesc=itemView.findViewById(R.id.tvDesc);
        }

        public void bind(Photo photo){
            this.textViewDesc.setText(photo.getTitle());
            Picasso.get().load(photo.getUrlM()).into(this.imageView);
            itemView.setOnClickListener(v -> photoCallback.onPhotoClicked(photo));
        }
    }

    public interface PhotoCallback{
        void onPhotoClicked(Photo photo);
    }
}
