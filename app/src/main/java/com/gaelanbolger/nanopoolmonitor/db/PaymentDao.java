package com.gaelanbolger.nanopoolmonitor.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gaelanbolger.nanopoolmonitor.vo.Payment;

import java.util.List;

@Dao
public interface PaymentDao {

    @Query("SELECT * FROM payment WHERE address = :address")
    LiveData<List<Payment>> findByAddress(String address);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Payment... payments);
}
