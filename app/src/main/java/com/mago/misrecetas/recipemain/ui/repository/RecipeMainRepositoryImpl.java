package com.mago.misrecetas.recipemain.ui.repository;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.mago.misrecetas.BuildConfig;
import com.mago.misrecetas.MisRecetasApp;
import com.mago.misrecetas.api.APIServiceRetrofit;
import com.mago.misrecetas.api.RecipeSearchResponse;
import com.mago.misrecetas.db.RecipesDataBase;
import com.mago.misrecetas.entities.Recipe;
import com.mago.misrecetas.libs.base.EventBus;
import com.mago.misrecetas.recipemain.events.RecipeMainEvent;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jorgemartinez on 27/11/18.
 */
public class RecipeMainRepositoryImpl implements RecipeMainRepository {
    private int recipePage;
    private EventBus eventBus;
    private APIServiceRetrofit service;
    private Activity activity;

    public RecipeMainRepositoryImpl(EventBus eventBus, APIServiceRetrofit service, Activity activity) {
        this.eventBus = eventBus;
        this.service = service;
        this.activity = activity;
    }

    @Override
    public void getNextRecipe() {
        Call<RecipeSearchResponse> call = service.searchRecipe(BuildConfig.FOOD_API_KEY, RECENT_SORT, COUNT, recipePage);
        Callback<RecipeSearchResponse> callback = new Callback<RecipeSearchResponse>() {
            @Override
            public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
                if (response.isSuccessful()){
                    RecipeSearchResponse recipeSearchResponse = response.body();
                    if (recipeSearchResponse.getCount() == 0 ){
                        setRecipePage(new Random().nextInt(RECIPE_RANGE));
                        getNextRecipe();
                    } else {
                        Recipe recipe = recipeSearchResponse.getFirstRecipe();
                        if (recipe != null)
                            post(recipe);
                        else
                            post(response.message());
                    }
                }else {
                    post(response.message());
                }
            }

            @Override
            public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {
                post(t.getLocalizedMessage());
            }
        };
        call.enqueue(callback);
    }

    @Override
    public void saveRecipe(Recipe recipe) {
        RecipesDataBase.getINSTANCE(activity).recipeDAO().insertRecipe(recipe);
        post();
    }

    @Override
    public void setRecipePage(int recipePage) {
        this.recipePage = recipePage;
    }

    private void post(String error, int type, Recipe recipe) {
        RecipeMainEvent event = new RecipeMainEvent();
        event.setType(type);
        event.setError(error);
        event.setRecipe(recipe);
        eventBus.post(event);
    }

    private void post(String error) {
        post(error, RecipeMainEvent.NEXT_EVENT, null);
    }

    private void post(Recipe recipe) {
        post(null, RecipeMainEvent.NEXT_EVENT, recipe);
    }

    private void post() {
        post(null, RecipeMainEvent.SAVE_EVENT, null);
    }
}
