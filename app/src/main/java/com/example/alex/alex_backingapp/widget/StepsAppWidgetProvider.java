package com.example.alex.alex_backingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.alex.alex_backingapp.R;
import com.example.alex.alex_backingapp.model.Ingredients;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class StepsAppWidgetProvider extends AppWidgetProvider {

     static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, ArrayList<Ingredients> mIngredients,
                                int appWidgetId) {
         StringBuilder s =new StringBuilder();
//todo
        for (Ingredients ingredients : mIngredients){

            s.append( ingredients.getIngredient()+">>>"+ingredients.getQuantity() +" "+ingredients.getMeasure() + "\n");
        }
 
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.steps_app_widget);
        views.setTextViewText(R.id._ingredient_appwidget_text, s);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

  

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
     }

    public static void updateAppWidgetIngredients(Context context, AppWidgetManager appWidgetManager, ArrayList<Ingredients> mIngredients, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
             updateAppWidget(context, appWidgetManager,mIngredients, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

