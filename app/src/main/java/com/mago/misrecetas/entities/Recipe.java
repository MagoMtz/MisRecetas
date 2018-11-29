package com.mago.misrecetas.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.mago.misrecetas.db.RecipesDataBase;

import static com.mago.misrecetas.db.RecipeDBContract.*;

/**
 * Created by jorgemartinez on 26/11/18.
 */
@Entity(tableName = RecipeData.TABLE_NAME,
        indices = @Index(value = RecipeData.ID))
public class Recipe {
    @SerializedName("recipe_id")
    @PrimaryKey
    @ColumnInfo(name = RecipeData.ID)
    @NonNull
    private String recipeId;

    @ColumnInfo(name = RecipeData.TITLE)
    private String title;

    @SerializedName("image_url")
    @ColumnInfo(name = RecipeData.IMAGE_URL)
    private String imageURL;

    @SerializedName("source_url")
    @ColumnInfo(name = RecipeData.SOURCE_URL)
    private String sourceURL;

    @ColumnInfo(name = RecipeData.IS_FAVORITE)
    private boolean favorite;

    public String toJSON() {
        return new Gson().toJson(this);
    }

    public Recipe fromJSON(String json) {
        return new Gson().fromJson(json, Recipe.class);
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSourceURL() {
        return sourceURL;
    }

    public void setSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if (obj instanceof Recipe){
            Recipe recipe = (Recipe)obj;
            equal = this.recipeId.equals(recipe.getRecipeId());
        }
        return equal;
    }

}
