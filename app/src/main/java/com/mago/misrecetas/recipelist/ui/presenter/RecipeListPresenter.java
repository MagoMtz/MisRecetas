package com.mago.misrecetas.recipelist.ui.presenter;

import com.mago.misrecetas.entities.Recipe;
import com.mago.misrecetas.recipelist.events.RecipeListEvent;
import com.mago.misrecetas.recipelist.ui.view.RecipeListView;

/**
 * Created by jorgemartinez on 28/11/18.
 */
public interface RecipeListPresenter {
    void onCreate();
    void onDestroy();

    void getRecipe();
    void removeRecipe(Recipe recipe);
    void toggleFavorite(Recipe recipe);
    void onEventMainThread(RecipeListEvent event);

    RecipeListView getView();
}