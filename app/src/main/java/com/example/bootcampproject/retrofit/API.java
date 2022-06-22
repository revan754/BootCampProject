package com.example.bootcampproject.retrofit;

import com.example.bootcampproject.service.BaseService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {

    private static API instance = null;
    private final BaseService myApi;

    private API() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.minerstat.com/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myApi = retrofit.create(BaseService.class);
    }

    public static synchronized API getInstance() {
        if (instance == null) {
            instance = new API();
        }
        return instance;
    }

    public BaseService getMyApi() {
        return myApi;
    }
}
