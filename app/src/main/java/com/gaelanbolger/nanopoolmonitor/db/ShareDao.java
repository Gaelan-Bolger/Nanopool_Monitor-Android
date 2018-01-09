package com.gaelanbolger.nanopoolmonitor.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gaelanbolger.nanopoolmonitor.vo.Share;

import java.util.List;

@Dao
public interface ShareDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Share... shares);

    @Delete
    void deleteAll(Share... shares);

    @Query("DELETE FROM share WHERE address = :address")
    void deleteAll(String address);

    @Query("SELECT * FROM share WHERE address = :address ORDER BY date DESC")
    LiveData<List<Share>> findByAddress(String address);
}
