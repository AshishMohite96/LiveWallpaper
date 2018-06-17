package com.example.admin.livewallpaper.database.localdatabase;

import com.example.admin.livewallpaper.database.RecentsImages;
import com.example.admin.livewallpaper.database.datasource.ImageRecentsDataSource;

import java.util.List;

import io.reactivex.Flowable;

public class RecentsDataSource implements ImageRecentsDataSource{

    private RecentsDAO recentsDAO;
    private static RecentsDataSource instance;

    public RecentsDataSource(RecentsDAO recentsDAO) {
        this.recentsDAO = recentsDAO;
    }

    public static RecentsDataSource getInstance(RecentsDAO recentsDAO) {

        if (instance == null)
            instance = new RecentsDataSource(recentsDAO);
        return instance;
    }

    @Override
    public Flowable<List<RecentsImages>> getAllRecents() {
        return recentsDAO.getAllRecents();
    }

    @Override
    public void insertRecents(RecentsImages... recents) {
        recentsDAO.insertRecents(recents);
    }

    @Override
    public void updateRecents(RecentsImages... recents) {
        recentsDAO.updateRecents(recents);
    }

    @Override
    public void deleteRecents(RecentsImages... recents) {
        recentsDAO.deleteRecents(recents);
    }

    @Override
    public void deleteAllRecents() {
        recentsDAO.deleteRecents();
    }
}
