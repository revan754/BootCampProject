package com.example.bootcampproject.model;

import com.google.gson.annotations.SerializedName;

public class CoinsModel {


    @SerializedName("coin")
    public String coin;

    @SerializedName("name")
    public String name;

    @SerializedName("price")
    public double price;

    @SerializedName("volume")
    public double volume;

    public CoinsModel(String coin, String name, long price, int volume) {
        this.coin = coin;
        this.name = name;
        this.price = price;
        this.volume = volume;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
}
