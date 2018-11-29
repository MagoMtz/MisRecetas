package com.mago.misrecetas.recipelist.ui.repository;

import com.mago.misrecetas.entities.Recipe;

/**
 * Created by jorgemartinez on 28/11/18.
 */
public interface RecipeListRepository {
    void getSavedRecipes();
    void updateRecipe(Recipe recipe);
    void removeRecipe(Recipe recipe);
}