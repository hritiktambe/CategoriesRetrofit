package com.example.callcategories;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.callcategories.Api.ApiInterface;
import com.example.callcategories.Api.ServiceFactory;
import com.example.callcategories.model.Items;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private List<Items> itemsList = new ArrayList<>();
    private Adapter adapter;
    private  LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: started");

        initRecyclerView();
        LoadJson();

    }

    private void LoadJson(){

        Log.d(TAG, "LoadJson: called");
        
        ApiInterface apiInterface = ServiceFactory.getApiClient().create(ApiInterface.class);

        Call<List<Items>> call = apiInterface.getItems();

        call.enqueue(new Callback<List<Items>>() {
            @Override
            public void onResponse(Call<List<Items>> call, Response<List<Items>> response) {
                if (response.isSuccessful() && response.body() != null){

                    Log.d("Response :", String.valueOf(response.isSuccessful()));

                    itemsList = response.body();
                    adapter = new Adapter(itemsList, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    initListener();

                }

            }

            @Override
            public void onFailure(Call<List<Items>> call, Throwable t) {

                Log.d("Error",t.toString());

            }
        });


    }

    private void initRecyclerView()
    {
        Log.d(TAG, "initRecyclerView: Initialised");
        recyclerView = findViewById(R.id.category_container);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void initListener(){

        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Items item = itemsList.get(position);
                Toast.makeText(MainActivity.this, item.getName(), Toast.LENGTH_SHORT).show();

//                Intent intent = new Intent(MainActivity.this,NewsDetailActivity.class);
//
//                Article article = articles.get(position);
//                intent.putExtra("url", article.getUrl());
//                intent.putExtra("title", article.getTitle());
//                intent.putExtra("img",  article.getUrlToImage());
//                intent.putExtra("date",  article.getPublishedAt());
//                intent.putExtra("source",  article.getSource().getName());
//                intent.putExtra("author", article.getAuthor());
//
//                startActivity(intent);
            }
        });

    }


}
