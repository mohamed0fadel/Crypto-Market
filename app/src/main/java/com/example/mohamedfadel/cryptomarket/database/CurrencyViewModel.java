package com.example.mohamedfadel.cryptomarket.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.mohamedfadel.cryptomarket.Currency;

import java.util.List;

public class CurrencyViewModel extends AndroidViewModel {

    private CurrencyRepository repository;
    private LiveData<List<Currency>> currencies;

    public CurrencyViewModel(@NonNull Application application) {
        super(application);
        repository = new CurrencyRepository(application);
        currencies = repository.getCurrencyList();
    }

    public void insert(Currency currency){
        repository.insert(currency);
    }

    public void deleteALl(){
        repository.deleteAll();
    }

    public LiveData<List<Currency>> getCurrencies(){
        return currencies;
    }
}
