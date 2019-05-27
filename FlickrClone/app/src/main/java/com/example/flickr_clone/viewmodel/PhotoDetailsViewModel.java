package com.example.flickr_clone.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.flickr_clone.data.model.ResponsePhotoInfo;
import com.example.flickr_clone.data.model.ResponsePhotoSizes;
import com.example.flickr_clone.data.model.ResponsePhotos;
import com.example.flickr_clone.data.repository.FlickrPhotosRepo;
import com.example.flickr_clone.data.repository.FlickrPhotosRepoImpl;
import com.example.flickr_clone.util.Constants;

public class PhotoDetailsViewModel extends AndroidViewModel {
    private FlickrPhotosRepo flickrPhotosRepo;
    private MutableLiveData<ResponsePhotoInfo> photoDetails=new MutableLiveData<>();
    private MutableLiveData<ResponsePhotoSizes> photoSizes=new MutableLiveData<>();

    public PhotoDetailsViewModel(@NonNull Application application) {
        super(application);
        flickrPhotosRepo= FlickrPhotosRepoImpl.getInstance(application);
    }

    public void init(String photoId){
        photoDetails=flickrPhotosRepo.getPhotoInfo(photoId);
        photoSizes=flickrPhotosRepo.getPhotoSizes(photoId);
    }

    public LiveData<ResponsePhotoInfo> getPhotoDetails(){
        return photoDetails;
    }

    public LiveData<ResponsePhotoSizes> getPhotoSizes(){
        return photoSizes;
    }
}
