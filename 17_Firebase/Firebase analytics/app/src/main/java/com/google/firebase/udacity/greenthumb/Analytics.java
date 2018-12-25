package com.google.firebase.udacity.greenthumb;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.udacity.greenthumb.data.Plant;

public class Analytics {

    public static void logEventAddToCart(Context context, Plant plant, int quantity) {
        Bundle params = new Bundle();
        params.putInt(FirebaseAnalytics.Param.ITEM_ID, plant.id);
        params.putString(FirebaseAnalytics.Param.ITEM_NAME, plant.name);
        params.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "plants");
        params.putDouble(FirebaseAnalytics.Param.QUANTITY, quantity);
        params.putDouble(FirebaseAnalytics.Param.PRICE, plant.price);

        FirebaseAnalytics.getInstance(context)
                .logEvent(FirebaseAnalytics.Event.ADD_TO_CART, params);
    }

    public static void logEventUserViewedPlant(Context context, Plant plant) {
        Bundle params = new Bundle();
        params.putInt(FirebaseAnalytics.Param.ITEM_ID, plant.id);
        params.putString(FirebaseAnalytics.Param.ITEM_NAME, plant.name);
        params.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "plants");
        params.putDouble(FirebaseAnalytics.Param.PRICE, plant.price);

        FirebaseAnalytics.getInstance(context)
                .logEvent(FirebaseAnalytics.Event.VIEW_ITEM, params);
    }

    public static void logEventUserViewedShoppingCart(Context context) {
        Bundle params = new Bundle();

        FirebaseAnalytics.getInstance(context)
                .logEvent("shopping_cart_viewed", params);
    }

    public static void logEventShoppingCartIsCheckedOut(Context context) {
        Bundle params = new Bundle();

        FirebaseAnalytics.getInstance(context)
                .logEvent("user_checked_out", params);
    }

    public static void setUserPropertyGardeningExperience(Context context, int experienceIndex) {
        String userPropertyKey = context.getString(R.string.user_property_key_gardening_experience);
        String[] userPropertyValues = context.getResources().getStringArray(R.array.gardening_experience_rating_labels);

        FirebaseAnalytics.getInstance(context)
                .setUserProperty(userPropertyKey, userPropertyValues[experienceIndex]);
    }

}
