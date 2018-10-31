package com.example.alex.alex_backingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.alex.alex_backingapp.model.Ingredients;

import java.util.ArrayList;

public class StepsService  extends IntentService {

    public static final String ACTION_UPDATE = "com.example.alex.alex_backingapp.action.update_widgets";
    private static ArrayList<Ingredients> mIngredients;

    public StepsService( ) {
        super("StepsService");
    }

    public static void startActionUpdateWidgets(Context  context, ArrayList<Ingredients> ingredients) {

         Intent intent = new Intent(context, StepsService.class);
        intent.setAction(ACTION_UPDATE);
        mIngredients=ingredients;
         context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE.equals(action)) {

                handleActionUpdateWidgets(this );

            }

        }


    }

    private void handleActionUpdateWidgets(Context context) {
         AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, StepsAppWidgetProvider.class));
        //Now update all widgets
        StepsAppWidgetProvider.updateAppWidgetIngredients(context, appWidgetManager,mIngredients,appWidgetIds);

    }


}

