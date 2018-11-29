package com.mago.misrecetas.recipemain.di;

import android.app.Activity;

import com.mago.misrecetas.api.APIServiceRetrofit;
import com.mago.misrecetas.libs.base.EventBus;
import com.mago.misrecetas.recipemain.ui.interactor.GetNextRecipeInteractor;
import com.mago.misrecetas.recipemain.ui.interactor.GetNextRecipeInteractorImpl;
import com.mago.misrecetas.recipemain.ui.interactor.SaveRecipeInteractor;
import com.mago.misrecetas.recipemain.ui.interactor.SaveRecipeInteractorImpl;
import com.mago.misrecetas.recipemain.ui.presenter.RecipeMainPresenter;
import com.mago.misrecetas.recipemain.ui.presenter.RecipeMainPresenterImpl;
import com.mago.misrecetas.recipemain.ui.repository.RecipeMainRepository;
import com.mago.misrecetas.recipemain.ui.repository.RecipeMainRepositoryImpl;
import com.mago.misrecetas.recipemain.ui.view.RecipeMainView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jorgemartinez on 27/11/18.
 */
@Module
public class RecipeMainModule {
    RecipeMainView view;

    public RecipeMainModule(RecipeMainView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    RecipeMainView providesRecipeMainView() {
        return this.view;
    }

    @Provides
    @Singleton
    RecipeMainPresenter providesRecipeMainPresenter(EventBus eventBus, RecipeMainView view, SaveRecipeInteractor saveRecipeInteractor, GetNextRecipeInteractor getNextRecipeInteractor) {
        return new RecipeMainPresenterImpl(eventBus, view, saveRecipeInteractor, getNextRecipeInteractor);
    }

    @Provides
    @Singleton
    SaveRecipeInteractor providesSaveRecipeInteractor(RecipeMainRepository repository) {
        return new SaveRecipeInteractorImpl(repository);
    }

    @Provides
    @Singleton
    GetNextRecipeInteractor providesGetNextRecipeInteractor(RecipeMainRepository repository) {
        return new GetNextRecipeInteractorImpl(repository);
    }

    @Provides
    @Singleton
    RecipeMainRepository providesRecipeMainRepository(EventBus eventBus, APIServiceRetrofit service, Activity activity){
        return new RecipeMainRepositoryImpl(eventBus, service, activity);
    }

}
