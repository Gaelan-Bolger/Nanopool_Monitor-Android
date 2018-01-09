package com.gaelanbolger.nanopoolmonitor.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gaelanbolger.nanopoolmonitor.vo.User;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... users);

    @Delete
    void delete(User... users);

    @Query("DELETE FROM user WHERE account = :address")
    void delete(String address);

    @Query("SELECT * FROM user WHERE account = :address")
    LiveData<User> findByAddress(String address);
}
