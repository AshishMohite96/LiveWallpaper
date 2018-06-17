package com.example.admin.livewallpaper.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.ref.WeakReference;

import dmax.dialog.SpotsDialog;

public class SaveImageHelper implements Target {

    private Context context;
    private WeakReference<SpotsDialog> alertDialogWeakReference;
    private WeakReference<ContentResolver> contentResolverWeakReference;
    private String name, desc;

    public SaveImageHelper(Context context, SpotsDialog alertDialog, ContentResolver contentResolver, String name, String desc) {
        this.context = context;
        this.alertDialogWeakReference = new WeakReference<SpotsDialog>(alertDialog);
        this.contentResolverWeakReference = new WeakReference<ContentResolver>(contentResolver);
        this.name = name;
        this.desc = desc;
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

        ContentResolver r = contentResolverWeakReference.get();
        SpotsDialog alertDialog = alertDialogWeakReference.get();
        if (r != null)
            MediaStore.Images.Media.insertImage(r, bitmap, name, desc);

        alertDialog.dismiss();
        Toast.makeText(context, "Download succeed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}
