package com.example.bootcampproject.service;

import com.example.bootcampproject.model.CoinsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface RestInterface {

    @GET("coins?list=ETH,TNB,BTC,ENG")
    Call<List<CoinsModel>> getCoins();
}
