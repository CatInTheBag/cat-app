package com.example.catapp.busineslogic;

import android.content.Context;
import android.widget.Toast;

import com.example.catapp.views.CatActivity;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CatPresenter {

    private CatActivity view;

    public CatPresenter(CatActivity view) {
        this.view = view;
    }

    public void setupRetrofit() {
        Retrofit retrofitCatImage = new Retrofit.Builder()
                .baseUrl("https://api.thecatapi.com/v1/images/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService imageService = retrofitCatImage.create(RetrofitService.class);
        Call<JsonArray> imageObject = imageService.getRandomCatImage();

        imageObject.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response != null && response.isSuccessful()) {
                    try {

                        JSONArray jArr = new JSONArray(response.body().toString());
                        JSONObject jObje = jArr.getJSONObject(0);
                        String imgUrl = jObje.getString("url");
                        view.setupPicasso(imgUrl);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(view, "On Response Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Toast.makeText(view, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
