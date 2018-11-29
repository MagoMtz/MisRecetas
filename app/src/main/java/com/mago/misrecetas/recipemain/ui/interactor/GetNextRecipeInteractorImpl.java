package com.mago.misrecetas.recipemain.ui.interactor;

import com.mago.misrecetas.recipemain.ui.repository.RecipeMainRepository;

import java.util.Random;

/**
 * Created by jorgemartinez on 27/11/18.
 */
public class GetNextRecipeInteractorImpl implements GetNextRecipeInteractor {
    RecipeMainRepository repository;

    public GetNextRecipeInteractorImpl(RecipeMainRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute() {
        int recipePage = new Random().nextInt();
        repository.setRecipePage(recipePage);
        repository.getNextRecipe();
    }
}
