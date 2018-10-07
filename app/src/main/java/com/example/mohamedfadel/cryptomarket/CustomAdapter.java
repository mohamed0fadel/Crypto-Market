package com.example.mohamedfadel.cryptomarket;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<Currency> currencies = new ArrayList<>();


    public CustomAdapter() {
    }

    public ArrayList<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(ArrayList<Currency> currencies) {
        this.currencies = currencies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        if (currencies != null) {
            Currency currency = currencies.get(i);
            myViewHolder.name.setText(currency.getName());
            myViewHolder.capacity.setText(String.valueOf(currency.getCapacity()));
            if (currency.getPercentChange() > 0) {
                myViewHolder.percent.setText("+ " + String.valueOf(currency.getPercentChange()));
                myViewHolder.symbol.setText(currency.getSymbol());
            } else {
                myViewHolder.percent.setText(String.valueOf(currency.getPercentChange()));
                myViewHolder.symbol.setBackgroundResource(R.drawable.red_textview);
                myViewHolder.symbol.setText(currency.getSymbol());
            }
            myViewHolder.price.setText(String.valueOf(currency.getPrice()));
            myViewHolder.capacity.setText(String.valueOf(currency.getCapacity()));
        }
    }

    @Override
    public int getItemCount() {
        if (currencies == null)
            return 0;
        return currencies.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, symbol, percent, price, capacity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            symbol = itemView.findViewById(R.id.tv_symbol);
            percent = itemView.findViewById(R.id.tv_percent);
            price = itemView.findViewById(R.id.tv_price);
            capacity = itemView.findViewById(R.id.tv_capacity);
        }
    }
}
