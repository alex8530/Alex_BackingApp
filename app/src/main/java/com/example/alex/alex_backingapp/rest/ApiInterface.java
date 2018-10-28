package com.example.alex.alex_backingapp.rest;

import com.example.alex.alex_backingapp.model.Recipe;
import com.example.alex.alex_backingapp.model.RecipeResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<ArrayList<Recipe>> getRecipeList();
}
