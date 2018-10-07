package com.example.mohamedfadel.cryptomarket;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mohamedfadel.cryptomarket.database.CurrencyViewModel;
import com.example.mohamedfadel.cryptomarket.networking.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //TODO add your api key if you want to try the app 😊😊😊
    private String stringUrl = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?CMC_PRO_API_KEY=YOUR KEY GOES HERE";
    private RequestQueue requestQueue;
    ArrayList<Currency> currencyArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CurrencyViewModel viewModel;
    private CustomReceiver customReceiver = new CustomReceiver(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(CurrencyViewModel.class);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CustomAdapter());

        if (!isNetworkConnected()){
            currencyArrayList.clear();
            viewModel.getCurrencies().observe(this, new Observer<List<Currency>>() {
                @Override
                public void onChanged(@Nullable List<Currency> currencies) {
                    for (Currency currency : currencies){
                        currencyArrayList.add(currency);
                        Log.d("ROOM read", currency.getName());
                    }
                    CustomAdapter databaseAdapter = new CustomAdapter();
                    databaseAdapter.setCurrencies(currencyArrayList);
                    recyclerView.setAdapter(databaseAdapter);
                }
            });
        }
    }

    private void jsonParse(){
        currencyArrayList.clear();
        viewModel.deleteALl();
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, stringUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray data = response.getJSONArray("data");for (int i = 0; i < data.length(); i++){
                                JSONObject currency = data.getJSONObject(i);
                                int coinId = currency.getInt("id");
                                String name = currency.getString("name");
                                String symbol = currency.getString("symbol");
                                JSONObject priceInfo = currency.getJSONObject("quote");
                                JSONObject usd = priceInfo.getJSONObject("USD");
                                double price = usd.getDouble("price");
                                float percentChange = Float.parseFloat(String.valueOf(usd.getString("percent_change_1h")));
                                double capacity = usd.getDouble("market_cap");
                                Currency coin = new Currency(coinId, name, symbol, price, percentChange, capacity);
                                currencyArrayList.add(coin);
                                viewModel.insert(coin);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("VOLLEY", currencyArrayList.get(0).getName());
                        CustomAdapter contentAdapter = new CustomAdapter();
                        contentAdapter.setCurrencies(currencyArrayList);
                        recyclerView.setAdapter(contentAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }){
        };
        requestQueue.add(objectRequest);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


    class CustomReceiver extends BroadcastReceiver{

        private Context context;
        public CustomReceiver(Context context) {
            this.context = context;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
                boolean notConnected = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
                if (notConnected){
                    Toast.makeText(context, "NOT CONNECTED", Toast.LENGTH_SHORT).show();
                }else {
                    if (isNetworkConnected()){
                        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
                        jsonParse();
                        Toast.makeText(context, "Local Database Content updated", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(customReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(customReceiver);
    }

}
