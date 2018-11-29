package com.mago.misrecetas.recipelist.di;

import android.app.Activity;
import android.content.Context;

import com.mago.misrecetas.entities.Recipe;
import com.mago.misrecetas.libs.base.EventBus;
import com.mago.misrecetas.libs.base.ImageLoader;
import com.mago.misrecetas.recipelist.adapter.OnItemClickListener;
import com.mago.misrecetas.recipelist.adapter.RecipesListAdapter;
import com.mago.misrecetas.recipelist.ui.interactor.RecipeListInteractor;
import com.mago.misrecetas.recipelist.ui.interactor.RecipeListInteractorImpl;
import com.mago.misrecetas.recipelist.ui.interactor.StoredRecipesInteractor;
import com.mago.misrecetas.recipelist.ui.interactor.StoredRecipesInteractorImpl;
import com.mago.misrecetas.recipelist.ui.presenter.RecipeListPresenter;
import com.mago.misrecetas.recipelist.ui.presenter.RecipeListPresenterImpl;
import com.mago.misrecetas.recipelist.ui.repository.RecipeListRepository;
import com.mago.misrecetas.recipelist.ui.repository.RecipeListRepositoryImpl;
import com.mago.misrecetas.recipelist.ui.view.RecipeListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jorgemartinez on 29/11/18.
 */
@Module
public class RecipeListModule {
    RecipeListView view;
    OnItemClickListener clickListener;

    public RecipeListModule(RecipeListView view, OnItemClickListener clickListener) {
        this.view = view;
        this.clickListener = clickListener;
    }

    @Provides
    @Singleton
    RecipeListView providesRecipeListView() {
        return view;
    }

    @Provides
    @Singleton
    OnItemClickListener providesClickListener() {
        return clickListener;
    }

    @Provides
    @Singleton
    RecipeListPresenter providesRecipeListPresenter(EventBus eventBus, RecipeListView view, RecipeListInteractor listInteractor, StoredRecipesInteractor storedInteractor) {
        return new RecipeListPresenterImpl(eventBus, view, listInteractor, storedInteractor);
    }

    @Provides
    @Singleton
    RecipeListInteractor providesRecipeListInteractor(RecipeListRepository repository) {
        return new RecipeListInteractorImpl(repository);
    }

    @Provides
    @Singleton
    StoredRecipesInteractor providesStoredRecipesInteractor(RecipeListRepository repository) {
        return new StoredRecipesInteractorImpl(repository);
    }

    @Provides
    @Singleton
    RecipeListRepository providesListRepository(EventBus eventBus, Activity activity) {
        return new RecipeListRepositoryImpl(eventBus, activity);
    }

    @Provides
    @Singleton
    RecipesListAdapter providesRecipesListAdapter(List<Recipe> recipeList, ImageLoader imageLoader, OnItemClickListener onItemClickListener) {
        return new RecipesListAdapter(recipeList, imageLoader, onItemClickListener);
    }

    @Provides
    @Singleton
    List<Recipe> providesEmptyList() {
        return new ArrayList<>();
    }

}
