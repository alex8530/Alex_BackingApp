package com.example.alex.alex_backingapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.alex.alex_backingapp.R;
import com.example.alex.alex_backingapp.model.Recipe;
import com.example.alex.alex_backingapp.model.Step;

import java.util.ArrayList;

public class StepsActivity extends AppCompatActivity
        implements Steps_ingredients_Fragment.OnSteps_ingredients_FragmentInteractionListener,
        DetailsStepFragment.OnDetailsStepFragmentInteractionListener {
//this  activity contian two fragment ,(steps and ingredients) ,,, and (deltails steps)
private static final String TAG = "StepsActivity";

    Recipe mRecipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);


        if (getIntent().hasExtra("Recipe") && getIntent().getParcelableExtra("Recipe")!=null){
            Log.d(TAG, "onCreate: ");
            mRecipe=getIntent().getParcelableExtra("Recipe");

            Log.d(TAG, "onCreata : "+mRecipe.getName());
            Toast.makeText(this, ""+mRecipe.getName(), Toast.LENGTH_SHORT).show();
        }else {
            Log.d(TAG, "onCreate: there is Some problem in getting recpie intent");
        }




        // Only create new fragments when there is no previously saved state
        if(savedInstanceState == null){

//            Steps_ingredients_Fragment fragment= new Steps_ingredients_Fragment();
            //this is the better way for creating instance fragment
            Steps_ingredients_Fragment fragment = Steps_ingredients_Fragment.newInstance(mRecipe);
            getSupportFragmentManager().beginTransaction().add(R.id.framelayout_steps_ingred,fragment).commit();

        }

    }

    @Override
    public void onFragmentInteraction(ArrayList<Step > steps, int position ) {
 //if phone mode , replace current fragment with details fragment


        DetailsStepFragment detailsStepFragment= new DetailsStepFragment();
         //we can put data as extra , but i will put it as methods !
        detailsStepFragment.setmStep(steps.get(position));

        //sent the index
        Bundle bundle= new Bundle();
        bundle.putInt("position",position);
        bundle.putInt("SizeOfList",steps.size());
        bundle.putParcelableArrayList("steps",steps );

        //Note , this is the other way
        detailsStepFragment.setBundleFromActivity(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayout_steps_ingred, detailsStepFragment)
                .commit();
//
    }

    @Override
    public void setCurrnt(int position) {

    }
}
