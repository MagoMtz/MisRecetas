package com.mago.misrecetas.recipelist.di;

import com.mago.misrecetas.libs.di.LibsModule;
import com.mago.misrecetas.recipelist.adapter.RecipesListAdapter;
import com.mago.misrecetas.recipelist.ui.presenter.RecipeListPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jorgemartinez on 29/11/18.
 */
@Singleton
@Component(modules = {RecipeListModule.class, LibsModule.class})
public interface RecipeListComponent {
    RecipesListAdapter getAdapter();
    RecipeListPresenter getPresenter();
}
