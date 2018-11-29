package com.mago.misrecetas.recipemain.ui.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mago.misrecetas.recipelist.ui.view.RecipeListActivity;
import com.mago.misrecetas.MisRecetasApp;
import com.mago.misrecetas.R;
import com.mago.misrecetas.databinding.ActivityRecipeMainBinding;
import com.mago.misrecetas.entities.Recipe;
import com.mago.misrecetas.libs.base.ImageLoader;
import com.mago.misrecetas.recipemain.di.RecipeMainComponent;
import com.mago.misrecetas.recipemain.ui.presenter.RecipeMainPresenter;

public class RecipeMainActivity extends AppCompatActivity implements RecipeMainView, SwipeGestureListener {
    private ActivityRecipeMainBinding view;

    private RecipeMainPresenter presenter;
    private ImageLoader imageLoader;
    private Recipe currentRecipe;
    private RecipeMainComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = DataBindingUtil.setContentView(this, R.layout.activity_recipe_main);
        setOnClickListener();

        setupInjection();
        setupImageLoader();
        setupGestureDetector();

        presenter.onCreate();
        presenter.getNextRecipe();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipes_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_liked_recipes:
                navigateToLikedRecipesScreen();
                return true;
            case R.id.action_logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupInjection() {
        MisRecetasApp app = (MisRecetasApp)getApplication();
        component = app.getRecipeMainComponent(this, this);
        imageLoader = getImageLoader();
        presenter = getPresenter();
    }

    private RecipeMainPresenter getPresenter() {
        return component.getPresenter();
    }

    private ImageLoader getImageLoader() {
        return component.getImageLoader();
    }

    private void setupImageLoader() {
        RequestListener glideRequestListener = new RequestListener() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                String errMsg = "Error";

                if (e != null)
                    errMsg = e.getMessage();

                presenter.imageError(errMsg);
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                presenter.imageReady();
                return false;
            }
        };
        imageLoader.setOnFinishedImageLoadingListener(glideRequestListener);
    }

    private void setupGestureDetector() {
        GestureDetector gestureDetector = new GestureDetector(this, new SwipeGestureDetector(this));
        View.OnTouchListener gestureOnTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        view.ivRecipeImg.setOnTouchListener(gestureOnTouchListener);
    }

    private void logout() {
        MisRecetasApp app = (MisRecetasApp)getApplication();
        app.logout();
    }

    private void navigateToLikedRecipesScreen() {
        startActivity(new Intent(this, RecipeListActivity.class));
    }


    private void setOnClickListener(){
        view.btnLike.setOnClickListener((v) -> onLike());
        view.btnDislike.setOnClickListener((v) -> onDislike());
    }

    private void clearImage() {
        view.ivRecipeImg.setImageResource(0);
    }

    private Animation.AnimationListener getAnimationListener() {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                clearImage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
    }

    @Override
    public void onLike() {
        if (currentRecipe != null)
            presenter.saveRecipe(currentRecipe);
    }

    @Override
    public void onDislike() {
        presenter.dismissRecipe();
    }


    @Override
    public void showProgressBar() {
        view.progressCircular.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        view.progressCircular.setVisibility(View.GONE);
    }

    @Override
    public void showUIElements() {
        view.lyButtons.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideUIElements() {
        view.lyButtons.setVisibility(View.GONE);
    }

    @Override
    public void likeAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.like_animation);
        animation.setAnimationListener(getAnimationListener());
        view.ivRecipeImg.setAnimation(animation);
    }

    @Override
    public void dislikeAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.dislike_animation);
        animation.setAnimationListener(getAnimationListener());
        view.ivRecipeImg.setAnimation(animation);
    }

    @Override
    public void onRecipeSaved() {
        Snackbar.make(view.getRoot(), getString(R.string.recipe_main_activity_msg_saved), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setRecipe(Recipe recipe) {
        this.currentRecipe = recipe;
        imageLoader.load(view.ivRecipeImg, recipe.getImageURL());
    }

    @Override
    public void onGetRecipeError(String error) {
        String errMsg = String.format(getString(R.string.recipe_main_activity_msg_error_getting_recipe), error);
        Snackbar.make(view.getRoot(), errMsg, Snackbar.LENGTH_SHORT).show();
    }

}
