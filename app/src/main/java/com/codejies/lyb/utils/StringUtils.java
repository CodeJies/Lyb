package com.codejies.lyb.utils;

import android.text.TextUtils;

/**
 * Created by Jies on 2018/5/11.
 */

public class StringUtils {

    public static String noEmptyString(String text){
        return TextUtils.isEmpty(text)?"":text;
    }

    public static String noEmptyString(String text,String defaultText){
        return TextUtils.isEmpty(text)?defaultText:text;
    }
}
