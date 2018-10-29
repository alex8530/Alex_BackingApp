package com.example.alex.alex_backingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.alex.alex_backingapp.adapter.MainAdapter;
import com.example.alex.alex_backingapp.listener.MyItemClickListener;
import com.example.alex.alex_backingapp.model.Recipe;
import com.example.alex.alex_backingapp.rest.ApiClient;
import com.example.alex.alex_backingapp.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class RecipeFragment extends Fragment implements MyItemClickListener {

    private OnFragmentInteractionListener mListener;
    ApiInterface mApiInterface;
    private static final String TAG = "RecipeFragment";
    Context mContext;


    @BindView(R.id.rv_fragment_recipe)
    RecyclerView mRecyclerView;

    List<Recipe> mRecipesList;

    MainAdapter mAdapter;
    MyItemClickListener myItemClickListener;



    public RecipeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
       View root =  inflater.inflate(R.layout.fragment_recipe, container, false);
         mContext=getContext();
        ButterKnife.bind(this,root);

        //        toolbar
        Toolbar toolbar = root.findViewById(R.id.toolebar_fragment_recipe);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);


         myItemClickListener=this;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setHasFixedSize(true);

        LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(
                mRecyclerView.getContext(),
                R.anim.layout_full_down);
        mRecyclerView.setLayoutAnimation(animationController);






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
                mRecyclerView.scheduleLayoutAnimation();
                mAdapter.notifyDataSetChanged();



            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {

            }
        });


        return root;


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClickItemObject(int position) {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
