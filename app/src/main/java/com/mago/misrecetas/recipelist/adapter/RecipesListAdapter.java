package com.mago.misrecetas.recipelist.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.facebook.share.model.ShareLinkContent;
import com.mago.misrecetas.entities.Recipe;
import com.mago.misrecetas.libs.base.ImageLoader;

import com.mago.misrecetas.databinding.ElementStoredRecipesBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jorgemartinez on 28/11/18.
 */
public class RecipesListAdapter extends RecyclerView.Adapter<RecipesListAdapter.ViewHolder> {
    private ArrayList<Recipe> recipeList;
    private ImageLoader imageLoader;
    private OnItemClickListener onItemClickListener;

    public RecipesListAdapter(List<Recipe> recipeList, ImageLoader imageLoader, OnItemClickListener onItemClickListener) {
        this.recipeList = new ArrayList<>(recipeList);
        this.imageLoader = imageLoader;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ElementStoredRecipesBinding view = ElementStoredRecipesBinding.inflate(layoutInflater, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Recipe currentRecipe = recipeList.get(i);

        imageLoader.load(viewHolder.view.ivRecipeImg, currentRecipe.getImageURL());

        viewHolder.view.tvRecipeTitle.setText(currentRecipe.getTitle());
        viewHolder.view.btnLike.setTag(currentRecipe.isFavorite());
        if (currentRecipe.isFavorite())
            viewHolder.view.btnLike.setImageResource(android.R.drawable.btn_star_big_on);
        else
            viewHolder.view.btnLike.setImageResource(android.R.drawable.btn_star_big_off);

        viewHolder.setOnItemClickListener(currentRecipe, onItemClickListener);

    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = new ArrayList<>(recipeList);
        notifyDataSetChanged();
    }

    public void removeRecipe(Recipe recipe) {
        recipeList.remove(recipe);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ElementStoredRecipesBinding view;

        ViewHolder(@NonNull ElementStoredRecipesBinding itemView) {
            super(itemView.getRoot());
            this.view = itemView;
        }


        void setOnItemClickListener(Recipe currentRecipe, OnItemClickListener onItemClickListener) {
            view.getRoot().setOnClickListener(
                    (v -> onItemClickListener.onItemClick(currentRecipe))
            );
            view.btnLike.setOnClickListener(
                    (v -> onItemClickListener.onFavClick(currentRecipe))
            );
            view.btnRemove.setOnClickListener(
                    (v -> onItemClickListener.onDeleteClick(currentRecipe))
            );

            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse(currentRecipe.getSourceURL()))
                    .build();

            view.btnFbShare.setShareContent(content);
            view.btnFbSend.setShareContent(content);
        }
    }
}
