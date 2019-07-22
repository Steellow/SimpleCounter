package com.hankiapps.simplecounter;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

public class dialog_ResetCounter extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = (LayoutInflater) getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_resetcounter, null);

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setCustomTitle(view);
        //alertDialog.setContentView(view);
        //alertDialog.setTitle(R.string.dialog_reset_title);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // nothing here
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendResult();
            }
        });
        alertDialog.show();

        //Dialog size
        Window window = alertDialog.getWindow();
        window.setLayout(700, WindowManager.LayoutParams.WRAP_CONTENT);

        //Center buttons
        Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) negativeButton.getLayoutParams();
        layoutParams.weight = 10;
        negativeButton.setLayoutParams(layoutParams);
        positiveButton.setLayoutParams(layoutParams);

        return alertDialog;
    }

    private void sendResult() {
        if(getTargetFragment() == null) return;

        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
        dismiss();
    }
}