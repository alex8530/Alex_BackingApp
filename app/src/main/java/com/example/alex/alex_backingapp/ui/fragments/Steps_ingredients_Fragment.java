package com.example.alex.alex_backingapp.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.alex.alex_backingapp.R;
import com.example.alex.alex_backingapp.adapter.SetpAdapter;
import com.example.alex.alex_backingapp.listener.MyItemClickListener;
import com.example.alex.alex_backingapp.model.Ingredients;
import com.example.alex.alex_backingapp.model.Recipe;
import com.example.alex.alex_backingapp.model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Steps_ingredients_Fragment.OnSteps_ingredients_FragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Steps_ingredients_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Steps_ingredients_Fragment extends Fragment implements MyItemClickListener{

     Context mContext;
    @BindView(R.id.rv_steps_fragment)
    RecyclerView mRecyleViewStepFragment;
    @BindView(R.id.tv_ingredients_fragment)
    TextView mTextViewIngredients;

    MyItemClickListener myItemClickListener;

     private static final String ARG_PARAM1 = "param1";
    private Recipe mRecipe;

    // TODO: Rename and change types of parameters



    private OnSteps_ingredients_FragmentInteractionListener mListener;

    public Steps_ingredients_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment Steps_ingredients_Fragment.
     */
    // i do the same thing in the second fragment when i create method setBundleFromActivity to pass data from activity to fragment
    //but this is the bessssst way for passing data ,and also i do not need onSaveInstanceState..
    public static Steps_ingredients_Fragment newInstance(Recipe param1) {
        Steps_ingredients_Fragment fragment = new Steps_ingredients_Fragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1,param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().getParcelable(ARG_PARAM1) != null) {
            mRecipe = getArguments().getParcelable(ARG_PARAM1);        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View root=  inflater.inflate(R.layout.fragment_steps_ingredients_, container, false);
        mContext=getContext();
        ButterKnife.bind(this,root);
        myItemClickListener=this;




        StringBuilder s =new StringBuilder();

        for (Ingredients ingredients : mRecipe.getIngredients()){

            s.append( ingredients.getIngredient()+">>>"+ingredients.getQuantity() +" "+ingredients.getMeasure() + "\n");
        }
        mTextViewIngredients.setText(s);

        mRecyleViewStepFragment.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyleViewStepFragment.setHasFixedSize(true);


        LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(
                mRecyleViewStepFragment.getContext(),
                R.anim.layout_full_down);
        mRecyleViewStepFragment.setLayoutAnimation(animationController);

        SetpAdapter adapter = new SetpAdapter(mContext);
        adapter.setmMyItemClickListener(this);
        adapter.setmStepList(mRecipe.getSteps());
        mRecyleViewStepFragment.setAdapter(adapter);
        mRecyleViewStepFragment.scheduleLayoutAnimation();
        adapter.notifyDataSetChanged();


        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSteps_ingredients_FragmentInteractionListener) {
            mListener = (OnSteps_ingredients_FragmentInteractionListener) context;
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
        //this is for handel the click from adapter

        mListener.onFragmentInteraction(mRecipe.getSteps() ,position );
    }
    public interface OnSteps_ingredients_FragmentInteractionListener {
         void onFragmentInteraction(ArrayList<Step> steps, int position );
    }


}
