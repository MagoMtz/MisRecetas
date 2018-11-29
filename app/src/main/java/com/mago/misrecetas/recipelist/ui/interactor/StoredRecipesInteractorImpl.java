package com.mago.misrecetas.recipelist.ui.interactor;

import com.mago.misrecetas.entities.Recipe;
import com.mago.misrecetas.recipelist.ui.repository.RecipeListRepository;

/**
 * Created by jorgemartinez on 28/11/18.
 */
public class StoredRecipesInteractorImpl implements StoredRecipesInteractor {
    private RecipeListRepository repository;

    public StoredRecipesInteractorImpl(RecipeListRepository repository) {
        this.repository = repository;
    }

    @Override
    public void executeUpdate(Recipe recipe) {
        repository.updateRecipe(recipe);
    }

    @Override
    public void executeDelete(Recipe recipe) {
        repository.removeRecipe(recipe);
    }
}