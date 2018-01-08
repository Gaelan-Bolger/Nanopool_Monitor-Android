package com.gaelanbolger.nanopoolmonitor.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gaelanbolger.nanopoolmonitor.vo.Account;

import java.util.List;

@Dao
public interface AccountDao {

    @Query("SELECT * FROM account")
    LiveData<List<Account>> getAll();

    @Query("SELECT * FROM account WHERE address = :address")
    LiveData<Account> findByAddress(String address);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Account account);

    @Delete
    void delete(Account account);
}
