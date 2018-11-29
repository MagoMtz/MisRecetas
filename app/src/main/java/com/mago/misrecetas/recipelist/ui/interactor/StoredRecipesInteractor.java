package com.mago.misrecetas.recipelist.ui.interactor;

import com.mago.misrecetas.entities.Recipe;

/**
 * Created by jorgemartinez on 28/11/18.
 */
public interface StoredRecipesInteractor {
    void executeUpdate(Recipe recipe);
    void executeDelete(Recipe recipe);
}