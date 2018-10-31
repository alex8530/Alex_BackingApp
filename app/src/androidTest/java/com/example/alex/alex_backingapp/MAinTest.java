package com.example.alex.alex_backingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.alex.alex_backingapp.ui.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
@RunWith(AndroidJUnit4.class)
public class MAinTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickGridViewItem_OpensStepActivity() {



        //if the position not apear on screen , i must usr this..
//        onView(withId(R.id.rv_fragment_recipe)).perform(RecyclerViewActions.scrollToPosition(1));



        //Note i should using idling for that operation that need long time but for now i use sleep !!
        //todo use ideling
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.rv_fragment_recipe)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
         onView(withId(R.id.tv_ingredients_fragment)).check(matches(isDisplayed()));


    }


}
