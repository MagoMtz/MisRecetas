package com.mago.misrecetas.libs;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestListener;
import com.mago.misrecetas.libs.base.ImageLoader;

/**
 * Created by jorgemartinez on 27/11/18.
 */
public class GlideImageLoader implements ImageLoader {
    private RequestManager glideRequestManager;
    private RequestListener onFinishedLoadingListener;

    public GlideImageLoader(RequestManager glideRequestManager) {
        this.glideRequestManager = glideRequestManager;
    }

    @Override
    public void load(ImageView imageView, String URL) {
        if (onFinishedLoadingListener != null){
            glideRequestManager
                    .load(URL)
                    .addListener(onFinishedLoadingListener)
                    .into(imageView);
        }else {
            glideRequestManager
                    .load(URL)
                    .into(imageView);
        }
    }

    @Override
    public void setOnFinishedImageLoadingListener(Object listener) {
        if (listener instanceof RequestListener)
            this.onFinishedLoadingListener = (RequestListener) listener;
    }
}
