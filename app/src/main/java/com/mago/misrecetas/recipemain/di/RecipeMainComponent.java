package com.mago.misrecetas.recipemain.di;

import com.mago.misrecetas.libs.base.ImageLoader;
import com.mago.misrecetas.libs.di.LibsModule;
import com.mago.misrecetas.recipemain.ui.presenter.RecipeMainPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jorgemartinez on 27/11/18.
 */
@Singleton
@Component(modules = {RecipeMainModule.class, LibsModule.class})
public interface RecipeMainComponent {
    //void inject(RecipeMainActivity activity);
    ImageLoader getImageLoader();
    RecipeMainPresenter getPresenter();
}
