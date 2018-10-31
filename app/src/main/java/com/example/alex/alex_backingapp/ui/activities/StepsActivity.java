package com.example.alex.alex_backingapp.ui.activities;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.alex.alex_backingapp.R;
import com.example.alex.alex_backingapp.model.Recipe;
import com.example.alex.alex_backingapp.model.Step;
import com.example.alex.alex_backingapp.ui.fragments.DetailsStepFragment;
import com.example.alex.alex_backingapp.ui.fragments.Steps_ingredients_Fragment;

import java.util.ArrayList;

public class StepsActivity extends AppCompatActivity
        implements Steps_ingredients_Fragment.OnSteps_ingredients_FragmentInteractionListener
         {
//in Tablet mode ,this  activity contian two fragment ,(steps and ingredients) ,,, and (deltails steps)
private static final String TAG = "StepsActivity";
    private static final String POSITION = "position";
    private static final String SIZE_OF_LIST = "SizeOfList";
    private static final String STEPS = "steps";


    Recipe mRecipe;
    boolean isTablet=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);





        if (getIntent().hasExtra(MainActivity.RECIPE) && getIntent().getParcelableExtra(MainActivity.RECIPE)!=null){
             mRecipe=getIntent().getParcelableExtra(MainActivity.RECIPE);

          }else {
            Log.d(TAG, "onCreate: there is Some problem in getting recpie intent");
        }

        View viewForCheck= findViewById(R.id.viewForCheck);

        if (viewForCheck!=null){
            isTablet=true;
            Log.d(TAG, "onCreate: isTablet=true; ");
        }

        //Note , there is no toolbar on tablet mode so you must check that to avoid null !!
        if (!isTablet){
            // toolbar
            Toolbar toolbar = findViewById(R.id.toolebar_steps);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }



        // Only create new fragments when there is no previously saved state
        if(savedInstanceState == null){


            //  Steps_ingredients_Fragment fragment= new Steps_ingredients_Fragment();
                 //this is the better way for creating instance fragment
                 Steps_ingredients_Fragment fragment = Steps_ingredients_Fragment.newInstance(mRecipe);
                 getSupportFragmentManager().beginTransaction().add(R.id.framelayout_steps_ingred,fragment).commit();


        }

    }

    @Override
    public void onFragmentInteraction(ArrayList<Step > steps, int position ) {
 //if phone mode , replace current fragment with details fragment
//this code will excute when click on fragement 1

        DetailsStepFragment detailsStepFragment= new DetailsStepFragment();
         //we can put data as extra , but i will put it as methods !

        //sent the index
        Bundle bundle= new Bundle();
        bundle.putInt(DetailsStepFragment.POSITION,position);
        bundle.putInt(DetailsStepFragment.SIZE_OF_LIST,steps.size());
        bundle.putParcelableArrayList(DetailsStepFragment.STEPS,steps );




        //Note , this is the other way
        detailsStepFragment.setBundleFromActivity(bundle);

        if (isTablet){
            Log.d(TAG, "onFragmentInteraction: isTablet");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.framelayout_steps_details, detailsStepFragment)
                    .commit();
        }else {
            Log.d(TAG, "onFragmentInteraction: !isTablet");

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.framelayout_steps_ingred, detailsStepFragment)
                    .commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
