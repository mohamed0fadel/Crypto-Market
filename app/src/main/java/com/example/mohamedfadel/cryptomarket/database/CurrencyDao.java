package com.example.mohamedfadel.cryptomarket.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.mohamedfadel.cryptomarket.Currency;

import java.util.List;

@Dao
public interface CurrencyDao {

    @Insert
    void insert(Currency currency);

    @Query("SELECT * FROM currency_table")
    LiveData<List<Currency>> getAllCurrencies();

    @Query("DELETE FROM currency_table")
    void deleteALL();
}
