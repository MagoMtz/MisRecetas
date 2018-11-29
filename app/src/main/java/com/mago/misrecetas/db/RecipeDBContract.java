package com.mago.misrecetas.db;

import android.provider.BaseColumns;

/**
 * Created by jorgemartinez on 27/11/18.
 */
public class RecipeDBContract {

    public static abstract class RecipeData implements BaseColumns {
        public static final int DB_VERSION = 1;
        public static final String DB_NAME = "recipes_db";
        public static final String TABLE_NAME = "recipe";
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String IMAGE_URL = "image_url";
        public static final String SOURCE_URL = "source_url";
        public static final String IS_FAVORITE = "is_favorite";

    }
}
