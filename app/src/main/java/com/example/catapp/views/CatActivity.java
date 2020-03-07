package com.example.catapp.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.catapp.R;
import com.example.catapp.busineslogic.RetrofitService;
import com.example.catapp.databinding.ActivityMainBinding;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CatActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Toolbar toolbar;
    private TextView textLabel;
    private ImageView imageView;
    private TextView textDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);

        setupToolbar();
        setupRetrofit();
        Intent intent = getIntent();
        String catLabel = intent.getStringExtra("catHeader");
        String catDescription = intent.getStringExtra("catDescription");

        textLabel = findViewById(R.id.header);
        textDescription = findViewById(R.id.description);
        imageView = findViewById(R.id.cat_image);

        textLabel.setText(catLabel);
        textDescription.setText(catDescription);


    }

    private void setupPicasso(String imageUrl) {
        Picasso.with(this)
                .load(imageUrl)
                .into(imageView);
    }

    private void setupRetrofit() {
        Retrofit retrofitCatImage = new Retrofit.Builder()
                .baseUrl("https://api.thecatapi.com/v1/images/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService imageService = retrofitCatImage.create(RetrofitService.class);
        Call<JsonArray> imageObject = imageService.getRandomCatImage();

        imageObject.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if(response!=null && response.isSuccessful()){
                    try {

                        JSONArray jArr = new JSONArray(response.body().toString());
                        JSONObject jObje = jArr.getJSONObject(0);
                        String imgUrl = jObje.getString("url");
                        setupPicasso(imgUrl);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(CatActivity.this, "On Response Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Toast.makeText(CatActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
