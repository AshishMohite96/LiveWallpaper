package com.example.admin.livewallpaper.database.datasource;

import com.example.admin.livewallpaper.database.RecentsImages;

import java.util.List;

import io.reactivex.Flowable;

public class RecentsRepository implements ImageRecentsDataSource{

    private ImageRecentsDataSource mLocalDataSource;
    private static RecentsRepository instance;

    public RecentsRepository(ImageRecentsDataSource mLocalDataSource) {
        this.mLocalDataSource = mLocalDataSource;
    }

    public static RecentsRepository getInstance(ImageRecentsDataSource mLocalDataSource){
        if (instance == null)
            instance = new RecentsRepository(mLocalDataSource);

        return instance;
    }

    @Override
    public Flowable<List<RecentsImages>> getAllRecents() {
        return mLocalDataSource.getAllRecents();
    }

    @Override
    public void insertRecents(RecentsImages... recents) {
        mLocalDataSource.insertRecents(recents);
    }

    @Override
    public void updateRecents(RecentsImages... recents) {
        mLocalDataSource.updateRecents(recents);
    }

    @Override
    public void deleteRecents(RecentsImages... recents) {
        mLocalDataSource.deleteRecents(recents);
    }

    @Override
    public void deleteAllRecents() {
        mLocalDataSource.deleteAllRecents();
    }
}
