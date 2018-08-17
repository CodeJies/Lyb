package com.codejies.lyb.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Jies on 2018/8/17.
 */
@SuppressLint("AppCompatCustomView")

public class LybFullFitImageView  extends ImageView {

        public LybFullFitImageView(Context context) {
            super(context);
        }

        public LybFullFitImageView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
            Drawable drawable = getDrawable();

            if(drawable!=null){
                int width = MeasureSpec.getSize(widthMeasureSpec);
                int height = (int) Math.ceil((float) width * (float) drawable.getIntrinsicHeight() / (float) drawable.getIntrinsicWidth());
                setMeasuredDimension(width, height);
            }else{
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        }

}
