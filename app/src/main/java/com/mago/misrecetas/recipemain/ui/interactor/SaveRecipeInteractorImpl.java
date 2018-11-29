package com.mago.misrecetas.recipemain.ui.interactor;

import com.mago.misrecetas.entities.Recipe;
import com.mago.misrecetas.recipemain.ui.repository.RecipeMainRepository;

/**
 * Created by jorgemartinez on 27/11/18.
 */
public class SaveRecipeInteractorImpl implements SaveRecipeInteractor {
    RecipeMainRepository repository;

    public SaveRecipeInteractorImpl(RecipeMainRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(Recipe recipe) {
        repository.saveRecipe(recipe);
    }
}
