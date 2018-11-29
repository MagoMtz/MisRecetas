package com.mago.misrecetas.recipelist.ui.interactor;

import com.mago.misrecetas.recipelist.ui.repository.RecipeListRepository;

/**
 * Created by jorgemartinez on 28/11/18.
 */
public class RecipeListInteractorImpl implements RecipeListInteractor {
    private RecipeListRepository repository;

    public RecipeListInteractorImpl(RecipeListRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute() {
        repository.getSavedRecipes();
    }
}