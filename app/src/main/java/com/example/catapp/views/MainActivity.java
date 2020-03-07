package com.example.catapp.views;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.catapp.R;
import com.example.catapp.busineslogic.MainPresenter;
import com.example.catapp.data.CatFact;
import com.example.catapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        recyclerView = binding.recView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        presenter = new MainPresenter(adapter, this, recyclerView);

        setupToolbar();
        presenter.setupRetrofit();
    }

    private void setupToolbar() {
        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
    }


    public ViewHolder.CatFactItemListener getClickListener() {
        return new ViewHolder.CatFactItemListener() {
            @Override
            public void onCatFactItemClicked(CatFact catFact) {
                //Toast.makeText(MainActivity.this,"Gallery Item clicked " +catFact.getHeader(), Toast.LENGTH_LONG ).show();
                Intent intent = new Intent(MainActivity.this, CatActivity.class);
                intent.putExtra("catHeader", catFact.getHeader());
                intent.putExtra("catDescription", catFact.getFact());
                startActivity(intent);
            }
        };
    }
}
