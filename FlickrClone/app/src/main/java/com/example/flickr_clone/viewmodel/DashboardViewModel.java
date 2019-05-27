package com.example.flickr_clone.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.flickr_clone.data.model.Photo;
import com.example.flickr_clone.data.model.ResponsePhotos;
import com.example.flickr_clone.data.repository.FlickrPhotosRepo;
import com.example.flickr_clone.data.repository.FlickrPhotosRepoImpl;
import com.example.flickr_clone.util.Constants;

import java.util.List;

public class DashboardViewModel extends AndroidViewModel {
    private FlickrPhotosRepo flickrPhotosRepo;
    private MutableLiveData<ResponsePhotos> photos=new MutableLiveData<>();
    private MutableLiveData<String> tags=new MutableLiveData<>();
    private final String tagPrefix="TAG : ";

    public DashboardViewModel(@NonNull Application application) {
        super(application);
        flickrPhotosRepo= FlickrPhotosRepoImpl.getInstance(application);
    }

    public void init(){
        photos=flickrPhotosRepo.getPhotosByTag(Constants.DEFAULT_QUERY);
        tags.setValue(tagPrefix+Constants.DEFAULT_QUERY);
    }

    public LiveData<ResponsePhotos> getPhotosByTag(){
        if(photos==null){
            photos=new MutableLiveData<>();
        }
        return photos;
    }

    public LiveData<String> getTag(){
        if(tags==null){
            tags=new MutableLiveData<>();
        }
        return tags;
    }

    public void searchPhotosByTag(String tag){
        tags.setValue(tagPrefix+tag);
        photos.setValue(flickrPhotosRepo.getPhotosByTag(tag).getValue());
    }
}
