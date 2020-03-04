package com.example.catapp;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("facts")
    Call<JsonObject> getFacts(@Query("limit") int numbers);

    @GET("search")
    Call<JsonArray> getRandomCatImage();
}
