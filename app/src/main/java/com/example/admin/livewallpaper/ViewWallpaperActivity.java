package com.example.admin.livewallpaper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.livewallpaper.utils.Common;
import com.example.admin.livewallpaper.utils.SaveImageHelper;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.UUID;

import dmax.dialog.SpotsDialog;

public class ViewWallpaperActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton floatingActionButton, fabDownload;
    ImageView viewImageView;
    CoordinatorLayout rootLayout;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case Common.PERMISION_REQUEST_CODE: if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "You need to allow this permission to download image", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private Target target = new Target() {
        @SuppressLint("MissingPermission")
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
            try {
                wallpaperManager.setBitmap(bitmap);
                Snackbar.make(rootLayout, "Wallpaper was set", Snackbar.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_wallpaper);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rootLayout = (CoordinatorLayout) findViewById(R.id.root_layout_view);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);

        collapsingToolbarLayout.setTitle(Common.CATEGORY_SELECTED);

        viewImageView = (ImageView) findViewById(R.id.image_thumb);
        Picasso.with(this)
                .load(Common.select_background.getImageLink())
                .into(viewImageView);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_wallpaper);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(getBaseContext())
                        .load(Common.select_background.getImageLink())
                        .into(target);
            }
        });

        fabDownload = (FloatingActionButton) findViewById(R.id.fab_download);
        fabDownload.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(ViewWallpaperActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Common.PERMISION_REQUEST_CODE);
                } else {
                    SpotsDialog dialog = new SpotsDialog(ViewWallpaperActivity.this);
                    dialog.show();
                    dialog.setMessage("Please wait...");

                    String fileName = UUID.randomUUID().toString()+".png";
                    Picasso.with(getBaseContext())
                            .load(Common.select_background.getImageLink())
                            .into(new SaveImageHelper(getBaseContext(),
                                    dialog,
                                    getApplicationContext().getContentResolver(),
                                    fileName,
                                    "Live Wallpaper Image"));
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        Picasso.with(this).cancelRequest(target);
        super.onDestroy();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
