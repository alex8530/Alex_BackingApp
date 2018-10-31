package com.example.alex.alex_backingapp.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.alex.alex_backingapp.R;
import com.example.alex.alex_backingapp.widget.StepsService;
import com.example.alex.alex_backingapp.adapter.MainAdapter;
import com.example.alex.alex_backingapp.listener.MyItemClickListener;
import com.example.alex.alex_backingapp.model.Ingredients;
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
 * {@link RecipeFragment.OnRecipeFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class RecipeFragment extends Fragment implements MyItemClickListener {

    private OnRecipeFragmentInteractionListener mListener;
    ApiInterface mApiInterface;
    private static final String TAG = "RecipeFragmentttttt";
    Context mContext;

    boolean isTabelet=false;


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



        if (this.getTag().equals("tablet")){


             //yes is  tablet
            isTabelet=true;
            Log.d(TAG, "onCreateView: isTabelet=true; ");
        }else {
            //phone
            isTabelet=false;
            Log.d(TAG, "onCreateView: isTabelet=false; ");

        }


        myItemClickListener=this;
        if (!isTabelet){
//            phone
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            Log.d(TAG, "onCreateView:phone ");

        }else {
//            tablet
            mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,3));
            Log.d(TAG, "onCreateView:tablet ");

        }
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
                 mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.scheduleLayoutAnimation();
                mAdapter.notifyDataSetChanged();

                // todo add the list to view model for avoid the recreate !!!


            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {

            }
        });


        return root;


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeFragmentInteractionListener) {
            mListener = (OnRecipeFragmentInteractionListener) context;
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

        //this is well trigger when click on item by adtapter
        mListener.onFragmentInteraction(mRecipesList.get(position));

         //sent the new Ingredients to update the last one and put it  to widget
        updateWidget(mRecipesList.get(position).getIngredients());

    }

    private void updateWidget(ArrayList<Ingredients> ingredients) {
         StepsService.startActionUpdateWidgets(getActivity(),ingredients);
    }


    public interface OnRecipeFragmentInteractionListener {

        void onFragmentInteraction( Recipe recipe);
    }

}
