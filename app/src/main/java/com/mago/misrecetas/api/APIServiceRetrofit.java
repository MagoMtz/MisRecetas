package com.mago.misrecetas.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jorgemartinez on 26/11/18.
 */
public interface APIServiceRetrofit {
    @GET("search")
    Call<RecipeSearchResponse> searchRecipe(@Query("key") String key,
                                            @Query("sort") String sort,
                                            @Query("count") int count,
                                            @Query("page") int page);
}
