package com.gaelanbolger.nanopoolmonitor.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gaelanbolger.nanopoolmonitor.vo.Payment;

import java.util.List;

@Dao
public interface PaymentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Payment... payments);

    @Delete
    void deleteAll(Payment... payments);

    @Query("DELETE FROM payment WHERE address = :address")
    void deleteAll(String address);

    @Query("SELECT * FROM payment WHERE address = :address ORDER BY date DESC")
    LiveData<List<Payment>> findByAddress(String address);
}
