package com.example.alex.alex_backingapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeResponse {


    //well get list of this opject !!
    private List<Recipe> recipeList;

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }
}
