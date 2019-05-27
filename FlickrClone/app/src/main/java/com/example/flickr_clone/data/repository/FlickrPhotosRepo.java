package com.example.flickr_clone.data.repository;

import android.arch.lifecycle.MutableLiveData;

import com.example.flickr_clone.data.model.Photo;
import com.example.flickr_clone.data.model.ResponsePhotoInfo;
import com.example.flickr_clone.data.model.ResponsePhotoSizes;
import com.example.flickr_clone.data.model.ResponsePhotos;

import java.util.List;

public interface FlickrPhotosRepo {
    MutableLiveData<ResponsePhotos> getPhotosByTag(String tag);
    MutableLiveData<ResponsePhotoInfo> getPhotoInfo(String photoId);
    MutableLiveData<ResponsePhotoSizes> getPhotoSizes(String photoId);
}
