package com.codejies.lyb.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.codejies.lyb.R;

public class LoadingDialog {
    public static class Builder {
        Context context;

        private boolean isRecycle;


        public Builder setIsRecycle(boolean isRecycle) {
            this.isRecycle = isRecycle;
            return this;
        }


        public Builder(Context context) {
            this.context = context;
        }

        public Dialog create() {
            Dialog loadingDialog = new Dialog(context);
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
            loadingDialog.setContentView(view, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return loadingDialog;
        }

    }
}
