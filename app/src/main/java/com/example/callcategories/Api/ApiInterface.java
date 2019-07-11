package com.example.callcategories.Api;

import com.example.callcategories.model.Items;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

// https://validation.appetizers.in/

public interface ApiInterface {

    @GET("wp-json/wc/v3/products/categories")
    Call<List<Items>> getItems ();

}
