package com.mago.misrecetas.recipelist.ui.presenter;

import com.mago.misrecetas.entities.Recipe;
import com.mago.misrecetas.libs.base.EventBus;
import com.mago.misrecetas.recipelist.events.RecipeListEvent;
import com.mago.misrecetas.recipelist.ui.interactor.RecipeListInteractor;
import com.mago.misrecetas.recipelist.ui.interactor.StoredRecipesInteractor;
import com.mago.misrecetas.recipelist.ui.view.RecipeListView;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by jorgemartinez on 28/11/18.
 */
public class RecipeListPresenterImpl implements RecipeListPresenter{
    private EventBus eventBus;
    private RecipeListView view;
    private RecipeListInteractor listInteractor;
    private StoredRecipesInteractor storedInteractor;

    public RecipeListPresenterImpl(EventBus eventBus, RecipeListView view, RecipeListInteractor listInteractor, StoredRecipesInteractor storedInteractor) {
        this.eventBus = eventBus;
        this.view = view;
        this.listInteractor = listInteractor;
        this.storedInteractor = storedInteractor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        view = null;
    }

    @Override
    public void getRecipe() {
        listInteractor.execute();
    }

    @Override
    public void removeRecipe(Recipe recipe) {
        storedInteractor.executeDelete(recipe);
    }

    @Override
    public void toggleFavorite(Recipe recipe) {
        recipe.setFavorite(!recipe.isFavorite());
        storedInteractor.executeUpdate(recipe);
    }

    @Override
    @Subscribe
    public void onEventMainThread(RecipeListEvent event) {
        if (view == null)
            return;
        switch (event.getType()) {
            case RecipeListEvent.READ_EVENT :
                view.setRecipes(event.getRecipeList());
                break;
            case RecipeListEvent.UPDATE_EVENT :
                view.recipeUpdated();
                break;
            case RecipeListEvent.DELETE_EVENT :
                Recipe recipe = event.getRecipeList().get(0);
                view.recipeDeleted(recipe);
                break;
        }
    }

    @Override
    public RecipeListView getView() {
        return view;
    }
}