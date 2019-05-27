package com.example.flickr_clone.data.api;

import com.example.flickr_clone.data.model.Photo;
import com.example.flickr_clone.data.model.ResponsePhotoInfo;
import com.example.flickr_clone.data.model.ResponsePhotoSizes;
import com.example.flickr_clone.data.model.ResponsePhotos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PhotosApi {
    @GET("services/rest/?method=flickr.photos.search&format=json&nojsoncallback=1&extras=url_m&per_page=10")
    Call<ResponsePhotos> getPhotosByTag(@Query("tags") String tags, @Query("api_key") String api_key);

    @GET("services/rest/?method=flickr.photos.getInfo&format=json&nojsoncallback=1")
    Call<ResponsePhotoInfo> getPhotoInfo(@Query("api_key") String api_key, @Query("photo_id") String photo_id);

    @GET("services/rest/?method=flickr.photos.getSizes&format=json&nojsoncallback=1")
    Call<ResponsePhotoSizes> getPhotoSizes(@Query("api_key") String api_key, @Query("photo_id") String photo_id);
}