package com.example.bootcampproject.service;

import com.example.bootcampproject.model.CoinsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface BaseService {

    @GET("coins?list=ETH,ANTPOOL BTC,BTC,2MINERS ETH")
    Call<List<CoinsModel>> getCoins();
}
