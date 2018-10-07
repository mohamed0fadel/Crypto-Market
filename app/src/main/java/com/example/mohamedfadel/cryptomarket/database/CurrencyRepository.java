package com.example.mohamedfadel.cryptomarket.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.mohamedfadel.cryptomarket.Currency;

import java.util.List;

public class CurrencyRepository {

    private CurrencyDao currencyDao;
    private LiveData<List<Currency>> currencyList;

    public CurrencyRepository(Application application){
        CurrencyDatabase instance = CurrencyDatabase.getInstance(application);
        currencyDao = instance.currencyDao();
        currencyList = currencyDao.getAllCurrencies();
    }

    public void insert(Currency currency){
        new InsertCurrencyAsyncTask(currencyDao).execute(currency);
    }

    public void deleteAll(){
        new DeleteAllCurrencyAsyncTask(currencyDao).execute();
    }

    public LiveData<List<Currency>> getCurrencyList() {
        return currencyList;
    }


    private static class InsertCurrencyAsyncTask extends AsyncTask<Currency, Void, Void>{
        private CurrencyDao currencyDao;
        public InsertCurrencyAsyncTask(CurrencyDao currencyDao) {
            this.currencyDao = currencyDao;
        }
        @Override
        protected Void doInBackground(Currency... currencies) {
            currencyDao.insert(currencies[0]);
            return null;
        }
    }

    private static class DeleteAllCurrencyAsyncTask extends AsyncTask<Void, Void, Void>{
        private CurrencyDao currencyDao;
        public DeleteAllCurrencyAsyncTask(CurrencyDao currencyDao) {
            this.currencyDao = currencyDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            currencyDao.deleteALL();
            return null;
        }
    }
}

