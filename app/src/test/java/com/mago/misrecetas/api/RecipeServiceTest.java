package com.mago.misrecetas.api;


import com.mago.misrecetas.BaseTest;
import com.mago.misrecetas.BuildConfig;
import com.mago.misrecetas.entities.Recipe;
import com.mago.misrecetas.recipemain.ui.repository.RecipeMainRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Response;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by jorgemartinez on 29/11/18.
 */
@RunWith(RobolectricTestRunner.class)
public class RecipeServiceTest extends BaseTest {
    private APIServiceRetrofit serviceRetrofit;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        RetrofitClient client = new RetrofitClient();
        serviceRetrofit = client.getRecipeService();
    }

    @Test
    public void doSearch_getRecipeFromBackend() throws Exception {
        String sort = RecipeMainRepository.RECENT_SORT;
        int count = RecipeMainRepository.COUNT;
        int page = 1;
        Call<RecipeSearchResponse> call = serviceRetrofit.searchRecipe(BuildConfig.FOOD_API_KEY, sort, count, page);

        Response<RecipeSearchResponse> response = call.execute();
        assertTrue(response.isSuccessful());

        RecipeSearchResponse recipeSearchResponse = response.body();
        assertEquals(1, recipeSearchResponse.getCount());

        Recipe recipe = recipeSearchResponse.getFirstRecipe();
        assertNotNull(recipe);
    }

    @Test
    public void doSearch_getNoRecipeFromBackend() throws Exception {
        String sort = RecipeMainRepository.RECENT_SORT;
        int count = RecipeMainRepository.COUNT;
        int page = 1000000000;
        Call<RecipeSearchResponse> call = serviceRetrofit.searchRecipe(BuildConfig.FOOD_API_KEY, sort, count, page);

        Response<RecipeSearchResponse> response = call.execute();
        assertTrue(response.isSuccessful());

        RecipeSearchResponse recipeSearchResponse = response.body();
        assertEquals(0, recipeSearchResponse.getCount());

        Recipe recipe = recipeSearchResponse.getFirstRecipe();
        assertNull(recipe);
    }

    @Test
    public void doSearch_getRandomRecipeFromBackend() throws Exception {
        String sort = RecipeMainRepository.RECENT_SORT;
        int count = RecipeMainRepository.COUNT;
        int page = new Random().nextInt(RecipeMainRepository.RECIPE_RANGE);
        Call<RecipeSearchResponse> call = serviceRetrofit.searchRecipe(BuildConfig.FOOD_API_KEY, sort, count, page);

        Response<RecipeSearchResponse> response = call.execute();
        assertTrue(response.isSuccessful());

        RecipeSearchResponse recipeSearchResponse = response.body();
        if (recipeSearchResponse.getCount() == 1) {
            Recipe recipe = recipeSearchResponse.getFirstRecipe();
            assertNotNull(recipe);
        } else {
            System.out.println("Invalid recipe, try again");
        }

    }
}
