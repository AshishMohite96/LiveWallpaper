package com.example.admin.livewallpaper.database.datasource;

import com.example.admin.livewallpaper.database.RecentsImages;

import java.util.List;

import io.reactivex.Flowable;

public interface ImageRecentsDataSource {

    Flowable<List<RecentsImages>> getAllRecents();
    void insertRecents(RecentsImages... recents);
    void updateRecents(RecentsImages... recents);
    void deleteRecents(RecentsImages... recents);
    void deleteAllRecents();
}
