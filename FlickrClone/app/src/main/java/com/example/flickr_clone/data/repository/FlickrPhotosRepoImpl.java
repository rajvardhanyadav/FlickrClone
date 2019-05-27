package com.example.flickr_clone.data.repository;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import com.example.flickr_clone.R;
import com.example.flickr_clone.data.api.PhotosApi;
import com.example.flickr_clone.data.model.ResponsePhotoInfo;
import com.example.flickr_clone.data.model.ResponsePhotoSizes;
import com.example.flickr_clone.data.model.ResponsePhotos;
import com.example.flickr_clone.data.service.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlickrPhotosRepoImpl implements FlickrPhotosRepo {
    private PhotosApi photosApi;
    private static FlickrPhotosRepo flickrPhotosRepo;
    private String key;
    MutableLiveData<ResponsePhotos> photos=new MutableLiveData<>();
    MutableLiveData<ResponsePhotoInfo> photoInfo=new MutableLiveData<>();
    MutableLiveData<ResponsePhotoSizes> photoSizes=new MutableLiveData<>();

    @Override
    public MutableLiveData<ResponsePhotos> getPhotosByTag(String tag) {
        photosApi.getPhotosByTag(tag, key).enqueue(new Callback<ResponsePhotos>() {
            @Override
            public void onResponse(Call<ResponsePhotos> call,
                                   Response<ResponsePhotos> response) {
                if (response.isSuccessful()){
                    photos.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponsePhotos> call, Throwable t) {
                photos.setValue(null);
            }
        });
        return photos;
    }

    @Override
    public MutableLiveData<ResponsePhotoInfo> getPhotoInfo(String photoId) {
        photosApi.getPhotoInfo(key,photoId).enqueue(new Callback<ResponsePhotoInfo>() {
            @Override
            public void onResponse(Call<ResponsePhotoInfo> call,
                                   Response<ResponsePhotoInfo> response) {
                if (response.isSuccessful()){
                    photoInfo.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponsePhotoInfo> call, Throwable t) {
                photoInfo.setValue(null);
            }
        });
        return photoInfo;
    }

    @Override
    public MutableLiveData<ResponsePhotoSizes> getPhotoSizes(String photoId) {
        photosApi.getPhotoSizes(key,photoId).enqueue(new Callback<ResponsePhotoSizes>() {
            @Override
            public void onResponse(Call<ResponsePhotoSizes> call,
                                   Response<ResponsePhotoSizes> response) {
                if (response.isSuccessful()){
                    photoSizes.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponsePhotoSizes> call, Throwable t) {
                photoSizes.setValue(null);
            }
        });
        return photoSizes;
    }

    public static FlickrPhotosRepo getInstance(Application application){
        if(flickrPhotosRepo==null){
            flickrPhotosRepo=new FlickrPhotosRepoImpl(application);
        }
        return flickrPhotosRepo;
    }

    private FlickrPhotosRepoImpl(Application application){
        photosApi= RetrofitService.createService(PhotosApi.class);
        key=application.getString(R.string.api_key);
    }
}
