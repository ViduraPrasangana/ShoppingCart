package com.ayesha.shoppingcart;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.style.FoldingCube;

public class LoadingDialog {
    private Activity activity;
    private Dialog dialog;
    private ProgressBar progressBar;

    public LoadingDialog(Activity activity){
        this.activity = activity;
    }

    public void showDialog(){
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.loading_dialog);
        progressBar = dialog.findViewById(R.id.progress);
        FoldingCube foldingCube = new FoldingCube();
        progressBar.setIndeterminateDrawable(foldingCube);
        dialog.show();
    }

    public void closeDialog(){
        dialog.dismiss();
    }
}
