package com.mago.misrecetas.recipemain.ui.presenter;

import com.mago.misrecetas.entities.Recipe;
import com.mago.misrecetas.libs.base.EventBus;
import com.mago.misrecetas.recipemain.ui.interactor.GetNextRecipeInteractor;
import com.mago.misrecetas.recipemain.ui.interactor.SaveRecipeInteractor;
import com.mago.misrecetas.recipemain.events.RecipeMainEvent;
import com.mago.misrecetas.recipemain.ui.view.RecipeMainView;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by jorgemartinez on 27/11/18.
 */
public class RecipeMainPresenterImpl implements RecipeMainPresenter {
    private EventBus eventBus;
    private RecipeMainView view;
    private SaveRecipeInteractor saveRecipeInteractor;
    private GetNextRecipeInteractor getNextRecipeInteractor;

    public RecipeMainPresenterImpl(EventBus eventBus, RecipeMainView view, SaveRecipeInteractor saveRecipeInteractor, GetNextRecipeInteractor getNextRecipeInteractor) {
        this.eventBus = eventBus;
        this.view = view;
        this.saveRecipeInteractor = saveRecipeInteractor;
        this.getNextRecipeInteractor = getNextRecipeInteractor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        view = null;
    }

    @Override
    public void dismissRecipe() {
        if (view != null)
            view.dislikeAnimation();
        getNextRecipe();
    }

    @Override
    public void getNextRecipe() {
        if (view != null) {
            view.hideUIElements();
            view.showProgressBar();
        }
        getNextRecipeInteractor.execute();
    }

    @Override
    public void saveRecipe(Recipe recipe) {
        if (view != null) {
            view.likeAnimation();
            view.hideUIElements();
            view.showProgressBar();
        }
        saveRecipeInteractor.execute(recipe);
    }

    @Override
    @Subscribe
    public void onEventMainThread(RecipeMainEvent event) {
        if (view == null)
            return;

        String error = event.getError();
        if (error != null){
            view.hideProgressBar();
            view.onGetRecipeError(error);
        } else {
            switch (event.getType()) {
                case RecipeMainEvent.NEXT_EVENT:
                    view.setRecipe(event.getRecipe());
                    break;
                case RecipeMainEvent.SAVE_EVENT:
                    view.onRecipeSaved();
                    getNextRecipeInteractor.execute();
                    break;
            }
        }
    }

    @Override
    public void imageReady() {
        if (view != null) {
            view.hideProgressBar();
            view.showUIElements();
        }
    }

    @Override
    public void imageError(String error) {
        if (view != null) {
            view.onGetRecipeError(error);
        }
    }

    @Override
    public RecipeMainView getView() {
        return view;
    }
}
