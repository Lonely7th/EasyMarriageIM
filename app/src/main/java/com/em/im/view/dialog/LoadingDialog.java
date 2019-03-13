package com.em.im.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;

import com.em.im.R;


/**
 * Time ： 2018/12/18 .
 * Author ： JN Zhang .
 * Description ：等待弹窗 .
 */
public class LoadingDialog extends Dialog {

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.LoadingDialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);

        setCanceledOnTouchOutside(false);
        getWindow().setGravity(Gravity.CENTER);
    }
}
