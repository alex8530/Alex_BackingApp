 package com.example.alex.alex_backingapp;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.alex.alex_backingapp.adapter.MainAdapter;
import com.example.alex.alex_backingapp.listener.MyItemClickListener;
import com.example.alex.alex_backingapp.model.Recipe;
import com.example.alex.alex_backingapp.model.RecipeResponse;
import com.example.alex.alex_backingapp.rest.ApiClient;
import com.example.alex.alex_backingapp.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

 public class MainActivity extends AppCompatActivity implements  RecipeFragment.OnFragmentInteractionListener {

     private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }



     @Override
     public void onFragmentInteraction(Uri uri) {

     }
 }
