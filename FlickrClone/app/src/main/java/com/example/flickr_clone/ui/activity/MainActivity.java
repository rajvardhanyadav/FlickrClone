package com.example.flickr_clone.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.flickr_clone.R;
import com.example.flickr_clone.adapter.PhotosAdapter;
import com.example.flickr_clone.data.model.Photo;
import com.example.flickr_clone.viewmodel.DashboardViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements PhotosAdapter.PhotoCallback{
    private static final String TAG = "MainActivity";
    private String tag;
    private DashboardViewModel dashboardViewModel;
    private PhotosAdapter photosAdapter;
    private RecyclerView recyclerView;
    private TextView tvTags;
    private boolean isGridLayout=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dashboardViewModel= ViewModelProviders.of(this).get(DashboardViewModel.class);
        if(savedInstanceState==null){
            dashboardViewModel.init();
        }else{
            isGridLayout=savedInstanceState.getBoolean("isGridLayout");
        }
        tvTags=findViewById(R.id.tvTag);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(getLayoutManager());
        photosAdapter=new PhotosAdapter(this);
        recyclerView.setAdapter(photosAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> openQueryDialog());
    }

    private RecyclerView.LayoutManager getLayoutManager(){
        if(isGridLayout){
            return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        }else{
            return new LinearLayoutManager(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            if(isGridLayout)
                isGridLayout=false;
            else
                isGridLayout=true;
            recyclerView.setLayoutManager(getLayoutManager());
            recyclerView.setAdapter(photosAdapter);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openQueryDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle(getString(R.string.app_name));
        alertDialog.setMessage(R.string.dialog_msg_enter_tag);

        final EditText input = new EditText(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton(getString(R.string.search),
                (dialog, which) -> {
                    tag = input.getText().toString();
                    if(!tag.isEmpty()) {
                        showLoader();
                        dashboardViewModel.searchPhotosByTag(tag);
                    }
                });

        alertDialog.setNegativeButton(getString(R.string.cancel),
                (dialog, which) -> dialog.cancel());

        alertDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showLoader();
        dashboardViewModel.getTag().observe(this, s -> {
            tvTags.setText(s);
        });

        dashboardViewModel.getPhotosByTag().observe(this, responsePhotos -> {
            if(responsePhotos!=null){
                if(responsePhotos.getPhotos().getPhoto().size()>0){
                    photosAdapter.renderPhotos(responsePhotos.getPhotos().getPhoto());
                    hideLoader();
//                    photosAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isGridLayout",isGridLayout);
    }

    @Override
    public void onPhotoClicked(Photo photo) {
        Intent intent=new Intent(this, PhotoDetailsActivity.class);
        intent.putExtra("photo_id",photo.getId());
        startActivity(intent);
    }
}
