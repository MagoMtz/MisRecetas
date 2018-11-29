package com.mago.misrecetas.recipelist.adapter;

import com.mago.misrecetas.entities.Recipe;

/**
 * Created by jorgemartinez on 28/11/18.
 */
public interface OnItemClickListener {
    void onFavClick(Recipe recipe);
    void onItemClick(Recipe recipe);
    void onDeleteClick(Recipe recipe);
}
