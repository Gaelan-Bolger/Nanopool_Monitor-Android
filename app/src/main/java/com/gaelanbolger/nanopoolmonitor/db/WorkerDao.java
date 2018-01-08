package com.gaelanbolger.nanopoolmonitor.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gaelanbolger.nanopoolmonitor.vo.Worker;

import java.util.List;

@Dao
public interface WorkerDao {

    @Query("SELECT * FROM worker WHERE uid = :address")
    LiveData<List<Worker>> findByAddress(String address);

    @Query("SELECT * FROM worker WHERE uid = :address")
    List<Worker> getByAddress(String address);

    @Query("DELETE FROM worker WHERE uid = :address")
    void deleteAll(String address);

    @Delete
    void deleteAll(Worker... workers);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Worker... worker);
}
