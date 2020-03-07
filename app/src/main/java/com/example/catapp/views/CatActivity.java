package com.example.catapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.catapp.R;
import com.example.catapp.busineslogic.CatPresenter;
import com.example.catapp.busineslogic.MainPresenter;
import com.example.catapp.databinding.ActivityMainBinding;
import com.squareup.picasso.Picasso;

public class CatActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Toolbar toolbar;
    private TextView textLabel;
    private ImageView imageView;
    private TextView textDescription;
    private CatPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);
        presenter = new CatPresenter(this);

        setupToolbar();
        presenter.setupRetrofit();
        Intent intent = getIntent();
        String catLabel = intent.getStringExtra("catHeader");
        String catDescription = intent.getStringExtra("catDescription");

        textLabel = findViewById(R.id.header);
        textDescription = findViewById(R.id.description);
        imageView = findViewById(R.id.cat_image);

        textLabel.setText(catLabel);
        textDescription.setText(catDescription);
    }

    public void setupPicasso(String imageUrl) {
        Picasso.with(this)
                .load(imageUrl)
                .into(imageView);
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
