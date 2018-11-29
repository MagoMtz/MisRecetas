package com.mago.misrecetas.recipelist.ui.repository;

import android.app.Activity;

import com.mago.misrecetas.db.RecipesDataBase;
import com.mago.misrecetas.entities.Recipe;
import com.mago.misrecetas.libs.base.EventBus;
import com.mago.misrecetas.recipelist.events.RecipeListEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jorgemartinez on 28/11/18.
 */
public class RecipeListRepositoryImpl implements RecipeListRepository {
    private EventBus eventBus;
    private Activity activity;

    public RecipeListRepositoryImpl(EventBus eventBus, Activity activity) {
        this.eventBus = eventBus;
        this.activity = activity;
    }

    @Override
    public void getSavedRecipes() {
        List<Recipe> recipeList = Arrays.asList(RecipesDataBase.getINSTANCE(activity).recipeDAO().allRecipes());
        post(RecipeListEvent.READ_EVENT, recipeList);
    }

    @Override
    public void updateRecipe(Recipe recipe) {
        RecipesDataBase.getINSTANCE(activity).recipeDAO().updateRecipe(recipe);
        List<Recipe> recipeList= new LinkedList<>(Collections.singleton(recipe));

        post(RecipeListEvent.UPDATE_EVENT, recipeList);
    }

    @Override
    public void removeRecipe(Recipe recipe) {
        RecipesDataBase.getINSTANCE(activity).recipeDAO().deleteRecipe(recipe);
        List<Recipe> recipeList= new LinkedList<>(Collections.singleton(recipe));

        post(RecipeListEvent.DELETE_EVENT, recipeList);
    }

    private void post(int type, List<Recipe> recipeList) {
        RecipeListEvent event = new RecipeListEvent();
        event.setType(type);
        event.setRecipeList(recipeList);
        eventBus.post(event);
    }


}