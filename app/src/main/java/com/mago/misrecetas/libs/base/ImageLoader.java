package com.mago.misrecetas.libs.base;

import android.widget.ImageView;

/**
 * Created by jorgemartinez on 27/11/18.
 */
public interface ImageLoader {
    void load(ImageView imageView, String URL);
    void setOnFinishedImageLoadingListener(Object object);
}
