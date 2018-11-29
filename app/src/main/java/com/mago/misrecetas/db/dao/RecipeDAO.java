package com.mago.misrecetas.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mago.misrecetas.db.RecipeDBContract;
import com.mago.misrecetas.entities.Recipe;

/**
 * Created by jorgemartinez on 27/11/18.
 */
@Dao
public interface RecipeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertRecipe(Recipe recipe);

    @Delete
    void deleteRecipe(Recipe recipe);

    @Update
    void updateRecipe(Recipe recipe);

    @Query("SELECT * FROM "+RecipeDBContract.RecipeData.TABLE_NAME)
    Recipe[] allRecipes();
}
