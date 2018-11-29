package com.mago.misrecetas.recipelist.ui.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.mago.misrecetas.MisRecetasApp;
import com.mago.misrecetas.R;
import com.mago.misrecetas.databinding.ActivityRecipeListBinding;
import com.mago.misrecetas.entities.Recipe;
import com.mago.misrecetas.recipelist.adapter.OnItemClickListener;
import com.mago.misrecetas.recipelist.adapter.RecipesListAdapter;
import com.mago.misrecetas.recipelist.di.RecipeListComponent;
import com.mago.misrecetas.recipelist.ui.presenter.RecipeListPresenter;
import com.mago.misrecetas.recipemain.ui.view.RecipeMainActivity;
import java.util.List;

public class RecipeListActivity extends AppCompatActivity implements RecipeListView, OnItemClickListener {
    private ActivityRecipeListBinding view;

    private RecipeListComponent component;
    private RecipesListAdapter adapter;
    private RecipeListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = DataBindingUtil.setContentView(this, R.layout.activity_recipe_list);

        setupToolbar();
        setupInjection();
        setupRecyclerView();

        setClickListeners();

        presenter.onCreate();
        presenter.getRecipe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.recipe_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_main :
                navigateToMainScreen();
                return true;
            case R.id.action_logout :
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onFavClick(Recipe recipe) {
        presenter.toggleFavorite(recipe);
    }

    @Override
    public void onItemClick(Recipe recipe) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(recipe.getSourceURL())));
    }

    @Override
    public void onDeleteClick(Recipe recipe) {
        presenter.removeRecipe(recipe);
    }

    @Override
    public void setRecipes(List<Recipe> data) {
        adapter.setRecipeList(data);
    }

    @Override
    public void recipeUpdated() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void recipeDeleted(Recipe recipe) {
        adapter.removeRecipe(recipe);
    }


    private void setupInjection() {
        MisRecetasApp app = (MisRecetasApp)getApplication();
        component = app.getRecipeListComponent(this, this, this);
        presenter = getPresenter();
        adapter = getAdapter();
    }

    private RecipesListAdapter getAdapter() {
        return component.getAdapter();
    }

    private RecipeListPresenter getPresenter() {
        return component.getPresenter();
    }

    private void setupRecyclerView() {
        view.contentRecipeList.recyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        view.contentRecipeList.recyclerview.setAdapter(adapter);
    }

    private void setupToolbar() {
        setSupportActionBar(view.toolbar);
    }

    private void setClickListeners() {
        onToolbarClick();
    }

    private void onToolbarClick(){
        view.toolbar.setOnClickListener(
                (v -> view.contentRecipeList.recyclerview.smoothScrollToPosition(0))
        );
    }

    private void logout() {
        ((MisRecetasApp)getApplication()).logout();
    }

    private void navigateToMainScreen() {
        startActivity(new Intent(this, RecipeMainActivity.class));
    }

}
