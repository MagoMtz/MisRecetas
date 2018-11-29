package com.mago.misrecetas.libs.di;

import android.app.Activity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.mago.misrecetas.api.APIServiceRetrofit;
import com.mago.misrecetas.api.RetrofitClient;
import com.mago.misrecetas.libs.GlideImageLoader;
import com.mago.misrecetas.libs.GreenRobotEventBus;
import com.mago.misrecetas.libs.base.EventBus;
import com.mago.misrecetas.libs.base.ImageLoader;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jorgemartinez on 27/11/18.
 */
@Module
public class LibsModule {
    private Activity activity;

    public LibsModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @Singleton
    ImageLoader providesImageLoader(RequestManager requestManager) {
        return new GlideImageLoader(requestManager);
    }

    @Provides
    @Singleton
    RequestManager providesRequestManager(Activity activity) {
        return Glide.with(activity);
    }

    @Provides
    @Singleton
    Activity providesActivity() {
        return this.activity;
    }

    @Provides
    @Singleton
    EventBus providesEventBus(org.greenrobot.eventbus.EventBus eventBus) {
        return new GreenRobotEventBus(eventBus);
    }

    @Provides
    @Singleton
    org.greenrobot.eventbus.EventBus providesLibraryEventBus() {
        return org.greenrobot.eventbus.EventBus.getDefault();
    }

    @Provides
    @Singleton
    APIServiceRetrofit providesApiServiceRetrofit() {
        return new RetrofitClient().getRecipeService();
    }




}
