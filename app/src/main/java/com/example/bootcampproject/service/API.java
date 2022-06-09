package com.example.bootcampproject.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {

    private static API instance = null;
    private RestInterface myApi;

    private API() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.minerstat.com/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myApi = retrofit.create(RestInterface.class);
    }

    public static synchronized API getInstance() {
        if (instance == null) {
            instance = new API();
        }
        return instance;
    }

    public RestInterface getMyApi() {
        return myApi;
    }
}
