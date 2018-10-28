 package com.example.alex.alex_backingapp;

import android.content.Context;
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
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

 public class MainActivity extends AppCompatActivity implements MyItemClickListener {
     ApiInterface mApiInterface;
     private static final String TAG = "MainActivity";
     Context mContext;


     @BindView(R.id.rv_activity_main)
     RecyclerView mRecyclerView;

     List<Recipe> mRecipesList;

     MainAdapter mAdapter;
     MyItemClickListener myItemClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        ButterKnife.bind(this);
        myItemClickListener=this;

//        toolbar
        Toolbar toolbar = findViewById(R.id.toolebar_activity_maim);
        setSupportActionBar(toolbar);



        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<Recipe>> arrayListCall = mApiInterface.getRecipeList();
        arrayListCall.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {

                mRecipesList=response.body();
                mAdapter = new MainAdapter(mContext);
                mAdapter.setmMyItemClickListener(myItemClickListener);
                mAdapter.setmRecipeList(mRecipesList);
                Log.d(TAG, "onCreate: mRecipesList"+mRecipesList.toString());
                mRecyclerView.setAdapter(mAdapter);



            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {

            }
        });





        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

//     LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(
//             mRecyclerView.getContext(),
//             R.anim.layout_full_down);
//        mRecyclerView.setLayoutAnimation(animationController);


    }


     @Override
     public void onClickItemObject(int position) {
         Log.d(TAG, "onClickItemObject: ");
     }
 }
