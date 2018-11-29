package com.mago.misrecetas.recipemain.ui.view;

import com.mago.misrecetas.entities.Recipe;

/**
 * Created by jorgemartinez on 27/11/18.
 */
public interface RecipeMainView {
    void showProgressBar();
    void hideProgressBar();
    void showUIElements();
    void hideUIElements();
    void likeAnimation();
    void dislikeAnimation();

    void onRecipeSaved();

    void setRecipe(Recipe recipe);
    void onGetRecipeError(String error);
}
