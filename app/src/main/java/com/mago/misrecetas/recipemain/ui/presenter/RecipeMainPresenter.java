package com.mago.misrecetas.recipemain.ui.presenter;

import com.mago.misrecetas.entities.Recipe;
import com.mago.misrecetas.recipemain.events.RecipeMainEvent;
import com.mago.misrecetas.recipemain.ui.view.RecipeMainView;

/**
 * Created by jorgemartinez on 27/11/18.
 */
public interface RecipeMainPresenter {
    void onCreate();
    void onDestroy();

    void dismissRecipe();
    void getNextRecipe();
    void saveRecipe(Recipe recipe);
    void onEventMainThread(RecipeMainEvent event);

    void imageReady();
    void imageError(String error);

    RecipeMainView getView();
}
