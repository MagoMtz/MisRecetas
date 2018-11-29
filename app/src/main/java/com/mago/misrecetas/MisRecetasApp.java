package com.mago.misrecetas;

import android.app.Application;
import android.content.Intent;

import com.facebook.login.LoginManager;
import com.mago.misrecetas.db.RecipesDataBase;
import com.mago.misrecetas.libs.di.LibsModule;
import com.mago.misrecetas.login.LoginActivity;
import com.mago.misrecetas.recipelist.adapter.OnItemClickListener;
import com.mago.misrecetas.recipelist.di.DaggerRecipeListComponent;
import com.mago.misrecetas.recipelist.di.RecipeListComponent;
import com.mago.misrecetas.recipelist.di.RecipeListModule;
import com.mago.misrecetas.recipelist.ui.view.RecipeListActivity;
import com.mago.misrecetas.recipelist.ui.view.RecipeListView;
import com.mago.misrecetas.recipemain.di.DaggerRecipeMainComponent;
import com.mago.misrecetas.recipemain.di.RecipeMainComponent;
import com.mago.misrecetas.recipemain.di.RecipeMainModule;
import com.mago.misrecetas.recipemain.ui.view.RecipeMainActivity;
import com.mago.misrecetas.recipemain.ui.view.RecipeMainView;

/**
 * Created by jorgemartinez on 26/11/18.
 */
public class MisRecetasApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        destroyDB();
    }

    private void destroyDB() {
        RecipesDataBase.destroyINSTANCE();
    }

    public void logout() {
        LoginManager.getInstance().logOut();
        startActivity(new Intent(this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }


    public RecipeMainComponent getRecipeMainComponent(RecipeMainActivity activity, RecipeMainView view) {
        return DaggerRecipeMainComponent
                .builder()
                .libsModule(new LibsModule(activity))
                .recipeMainModule(new RecipeMainModule(view))
                .build();
    }

    public RecipeListComponent getRecipeListComponent(RecipeListActivity activity, RecipeListView view, OnItemClickListener clickListener) {
        return DaggerRecipeListComponent
                .builder()
                .libsModule(new LibsModule(activity))
                .recipeListModule(new RecipeListModule(view, clickListener))
                .build();

    }

}
