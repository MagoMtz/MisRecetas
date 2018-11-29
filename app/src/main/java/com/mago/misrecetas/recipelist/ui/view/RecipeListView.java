package com.mago.misrecetas.recipelist.ui.view;

import com.mago.misrecetas.entities.Recipe;

import java.util.List;

/**
 * Created by jorgemartinez on 28/11/18.
 */
public interface RecipeListView {
    void setRecipes(List<Recipe> data);
    void recipeUpdated();
    void recipeDeleted(Recipe recipe);
}