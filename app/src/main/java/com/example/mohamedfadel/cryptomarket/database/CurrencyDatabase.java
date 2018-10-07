package com.example.mohamedfadel.cryptomarket.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.mohamedfadel.cryptomarket.Currency;

@Database(entities = {Currency.class}, version = 1, exportSchema = false)
public abstract class CurrencyDatabase extends RoomDatabase {

    private static CurrencyDatabase instance;
    public abstract CurrencyDao currencyDao();

    public static synchronized CurrencyDatabase getInstance(Context context) {
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), CurrencyDatabase.class, "currency_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
