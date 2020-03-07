package com.example.catapp.busineslogic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.catapp.data.CatFact;
import com.example.catapp.views.CatActivity;
import com.example.catapp.views.MainActivity;
import com.example.catapp.views.RecAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPresenter {
    private List<CatFact> items;
    private MainActivity view;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;

    public MainPresenter(RecyclerView.Adapter adapter, MainActivity mainActivity, RecyclerView recyclerView) {
        this.view = mainActivity;
        this.adapter = adapter;
        this.recyclerView = recyclerView;
        items = new ArrayList<>();
    }

    public void setupRetrofit() {

        //open the app
        Retrofit retrofitCatFacts = new Retrofit.Builder()
                .baseUrl("https://catfact.ninja/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService service = retrofitCatFacts.create(RetrofitService.class);


        // show the user the first 10 facts
        Call<JsonObject> factCall = service.getFacts(10);
        factCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response != null && response.isSuccessful()) {
                        try {
                            JSONArray factsArr = new JSONObject(response.body().toString()).getJSONArray("data");
                            for (int i = 0; i < factsArr.length(); i++) {
                                JSONObject jObj = factsArr.getJSONObject(i);
                                CatFact singleCatFact = new CatFact(
                                        "Cat Fact #" + (i + 1),
                                        jObj.getString("fact"),
                                        jObj.getInt("length")
                                );
                                items.add(singleCatFact);
                            }

                            adapter = new RecAdapter(items, view, view.getClickListener());
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(view, "On Response Error", Toast.LENGTH_LONG).show();
                    }
                }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(view, "On Failure Error", Toast.LENGTH_LONG).show();
            }
        });
    }

}
