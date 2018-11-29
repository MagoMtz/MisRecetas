package com.mago.misrecetas.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.mago.misrecetas.db.dao.RecipeDAO;
import com.mago.misrecetas.entities.Recipe;

/**
 * Created by jorgemartinez on 27/11/18.
 */
@Database(version = RecipeDBContract.RecipeData.DB_VERSION,
            entities = Recipe.class,
            exportSchema = false)
public abstract class RecipesDataBase extends RoomDatabase {
    private static RecipesDataBase INSTANCE;

    abstract public RecipeDAO recipeDAO();

    public static RecipesDataBase getINSTANCE(Context context) {
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RecipesDataBase.class, RecipeDBContract.RecipeData.DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyINSTANCE() {
        INSTANCE = null;
    }
}
