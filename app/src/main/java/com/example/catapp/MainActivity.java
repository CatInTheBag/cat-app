package com.example.catapp;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.catapp.databinding.ActivityMainBinding;
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

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<CatFact> items;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        recyclerView = binding.recView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        items = new ArrayList<>();

        setupToolbar();
        setupRetroFit();
    }

    private void setupToolbar() {
        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
    }

    private void setupRetroFit() {

        Retrofit retrofitCatFacts = new Retrofit.Builder()
                .baseUrl("https://catfact.ninja/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService service = retrofitCatFacts.create(RetrofitService.class);

        Call<JsonObject> factCall = service.getFacts(25);

        factCall.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response != null && response.isSuccessful()) {
                    try {
                        JSONArray factsArr = new JSONObject(response.body().toString()).getJSONArray("data");
                        for (int i = 0; i < factsArr.length(); i++) {
                            JSONObject jObj = factsArr.getJSONObject(i);
                            CatFact singleCatFact = new CatFact(
                                    "Cat Fact #" + (i+1),
                                    jObj.getString("fact"),
                                    jObj.getInt("length")
                            );
                            items.add(singleCatFact);
                        }

                        adapter = new RecAdapter(items,getApplicationContext(),getClickListener());
                        recyclerView.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "On Response Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(MainActivity.this, "On Failure Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private ViewHolder.CatFactItemListener getClickListener() {
        return new ViewHolder.CatFactItemListener() {
            @Override
            public void onCatFactItemClicked(CatFact catFact) {
                //Toast.makeText(MainActivity.this,"Gallery Item clicked " +catFact.getHeader(), Toast.LENGTH_LONG ).show();
                Intent intent = new Intent(MainActivity.this,CatActivity.class);
                intent.putExtra("catHeader",catFact.getHeader());
                intent.putExtra("catDescription",catFact.getFact());
                startActivity(intent);
            }
        };
    }
}
