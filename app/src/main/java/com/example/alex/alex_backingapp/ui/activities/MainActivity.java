 package com.example.alex.alex_backingapp.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.alex.alex_backingapp.R;
import com.example.alex.alex_backingapp.model.Recipe;
import com.example.alex.alex_backingapp.ui.fragments.RecipeFragment;

 public class MainActivity extends AppCompatActivity implements RecipeFragment.OnRecipeFragmentInteractionListener {

     private static final String TAG = "MainActivity";
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


     @Override
     public void onFragmentInteraction(Recipe recipe) {
        //will recieve the Recipe when click on Recipefragment

         //go to second activity that contins the fragemt  steps and ingredients

         Intent intent= new Intent(this,StepsActivity.class);
         intent.putExtra("Recipe",recipe);
         startActivity(intent);


      }
 }
