package com.mago.misrecetas.recipemain.ui.repository;

import com.mago.misrecetas.entities.Recipe;

/**
 * Created by jorgemartinez on 27/11/18.
 */
public interface RecipeMainRepository {
    public static final int COUNT = 1;
    public static final String RECENT_SORT = "r";
    public static final int RECIPE_RANGE = 100000;

    void getNextRecipe();
    void saveRecipe(Recipe recipe);
    void setRecipePage(int recipePage);
}
