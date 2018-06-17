package com.example.admin.livewallpaper.database.localdatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.admin.livewallpaper.database.RecentsImages;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface RecentsDAO {
    @Query("SELECT * FROM recents ORDER BY saveTime DESC LIMIT 10")
    Flowable<List<RecentsImages>> getAllRecents();

    @Insert
    void insertRecents(RecentsImages... recents);

    @Update
    void updateRecents(RecentsImages... recents);

    @Delete
    void deleteRecents(RecentsImages... recents);

    @Query("DELETE FROM recents")
    void deleteAllRecents();
}
