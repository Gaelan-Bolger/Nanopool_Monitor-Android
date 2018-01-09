package com.gaelanbolger.nanopoolmonitor.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gaelanbolger.nanopoolmonitor.vo.Share;

import java.util.List;

@Dao
public interface ShareDao {

    @Query("SELECT * FROM share WHERE address = :address")
    LiveData<List<Share>> findByAddress(String address);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Share... shares);
}
