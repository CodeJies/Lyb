package com.codejies.lyb.utils;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class GlideUtils {
    public static RequestOptions getDefaultOptions(int size) {
        return new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE).override(size);
    }
}
