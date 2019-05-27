package com.example.flickr_clone.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.flickr_clone.R;
import com.example.flickr_clone.data.model.Photo;
import com.example.flickr_clone.data.model.ResponsePhotoInfo;
import com.example.flickr_clone.data.model.ResponsePhotoSizes;
import com.example.flickr_clone.viewmodel.PhotoDetailsViewModel;
import com.squareup.picasso.Picasso;

public class PhotoDetailsActivity extends AppCompatActivity {
    private ImageView ivPhoto;
    private TextView tvOwner;
    private TextView tvTitle;
    private TextView tvTags;
    private TextView tvDesc;
    private String photoId;
    private PhotoDetailsViewModel photoDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);
        ivPhoto=findViewById(R.id.ivPhoto);
        tvOwner=findViewById(R.id.tvOwner);
        tvTitle=findViewById(R.id.tvTitle);
        tvTags=findViewById(R.id.tvTags);
        tvDesc=findViewById(R.id.tvDesc);
        if(getIntent().getStringExtra("photo_id")!=null){
            photoId=getIntent().getStringExtra("photo_id");
        }
        photoDetailsViewModel= ViewModelProviders.of(this).get(PhotoDetailsViewModel.class);
        if(savedInstanceState==null){
            photoDetailsViewModel.init(photoId);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        photoDetailsViewModel.getPhotoDetails().observe(this, responsePhotoInfo -> {
            if(responsePhotoInfo!=null) {
                tvOwner.setText("Photo by " + responsePhotoInfo.getPhoto().getOwner().getUsername());
                tvTitle.setText(responsePhotoInfo.getPhoto().getTitle().getContent());
                tvTags.setText(responsePhotoInfo.getPhoto().getTags().getTag().get(0).getContent());
                tvDesc.setText(responsePhotoInfo.getPhoto().getDescription().getContent());
            }
        });

        photoDetailsViewModel.getPhotoSizes().observe(this, responsePhotoSizes -> {
            if(responsePhotoSizes!=null) {
                int sizesCount = responsePhotoSizes.getSizes().getSize().size();
                String url = responsePhotoSizes.getSizes().getSize().get(sizesCount - 1).getSource();
                Picasso.get().load(url).into(ivPhoto);
            }
        });
    }
}
