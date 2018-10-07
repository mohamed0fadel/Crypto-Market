package com.example.mohamedfadel.cryptomarket;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity (tableName = "currency_table")
public class Currency {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int coinId;
    private String name;
    private String symbol;
    private double price;
    private float percentChange;
    private double capacity;

    public Currency(int coinId, String name, String symbol, double price, float percentChange, double capacity) {
        this.coinId = coinId;
        this.name = name;
        this.symbol = symbol;
        this.price = price;
        this.percentChange = percentChange;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoinId() {
        return coinId;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public float getPercentChange() {
        return percentChange;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCoinId(int coinId) {
        this.coinId = coinId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPercentChange(float percentChange) {
        this.percentChange = percentChange;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }
}
