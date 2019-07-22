package com.hankiapps.simplecounter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

public class dialog_ThemePicker extends DialogFragment implements View.OnClickListener {

    private Button theme1, theme2, theme3, theme4, theme5, theme6, theme7, theme8, theme9, theme10, theme11, theme12;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_themepicker, null);

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle(R.string.dialog_theme_title);
        alertDialog.setView(view);
        alertDialog.show();

        //Set buttons
        theme1 = view.findViewById(R.id.button_theme1);
        theme2 = view.findViewById(R.id.button_theme2);
        theme3 = view.findViewById(R.id.button_theme3);
        theme4 = view.findViewById(R.id.button_theme4);
        theme5 = view.findViewById(R.id.button_theme5);
        theme6 = view.findViewById(R.id.button_theme6);
        theme7 = view.findViewById(R.id.button_theme7);
        theme8 = view.findViewById(R.id.button_theme8);
        theme9 = view.findViewById(R.id.button_theme9);
        theme10 = view.findViewById(R.id.button_theme10);
        theme11 = view.findViewById(R.id.button_theme11);
        theme12 = view.findViewById(R.id.button_theme12);

        //Set Button OnClickListeners
        theme1.setOnClickListener(this);
        theme2.setOnClickListener(this);
        theme3.setOnClickListener(this);
        theme4.setOnClickListener(this);
        theme5.setOnClickListener(this);
        theme6.setOnClickListener(this);
        theme7.setOnClickListener(this);
        theme8.setOnClickListener(this);
        theme9.setOnClickListener(this);
        theme10.setOnClickListener(this);
        theme11.setOnClickListener(this);
        theme12.setOnClickListener(this);

        //setButtonColors();

        return alertDialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_theme1:
                sendResult(ContextCompat.getColor(requireContext(), R.color.theme1));
                break;
            case R.id.button_theme2:
                sendResult(ContextCompat.getColor(requireContext(), R.color.theme2));
                break;
            case R.id.button_theme3:
                sendResult(ContextCompat.getColor(requireContext(), R.color.theme3));
                break;
            case R.id.button_theme4:
                sendResult(ContextCompat.getColor(requireContext(), R.color.theme4));
                break;
            case R.id.button_theme5:
                sendResult(ContextCompat.getColor(requireContext(), R.color.theme5));
                break;
            case R.id.button_theme6:
                sendResult(ContextCompat.getColor(requireContext(), R.color.theme6));
                break;
            case R.id.button_theme7:
                sendResult(ContextCompat.getColor(requireContext(), R.color.theme7));
                break;
            case R.id.button_theme8:
                sendResult(ContextCompat.getColor(requireContext(), R.color.theme8));
                break;
            case R.id.button_theme9:
                sendResult(ContextCompat.getColor(requireContext(), R.color.theme9));
                break;
            case R.id.button_theme10:
                sendResult(ContextCompat.getColor(requireContext(), R.color.theme10));
                break;
            case R.id.button_theme11:
                sendResult(ContextCompat.getColor(requireContext(), R.color.theme11));
                break;
            case R.id.button_theme12:
                sendResult(ContextCompat.getColor(requireContext(), R.color.theme12));
                break;
        }
    }

    public void sendResult(int color) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = fragment_main.newIntent(color);

        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        dismiss();
    }

    private void setButtonColors() {
        Context context = theme1.getContext();
        theme1.setBackgroundColor(context.getResources().getColor(R.color.theme1));
        theme2.setBackgroundColor(context.getResources().getColor(R.color.theme2));
        theme3.setBackgroundColor(context.getResources().getColor(R.color.theme3));
        theme4.setBackgroundColor(context.getResources().getColor(R.color.theme4));
        theme5.setBackgroundColor(context.getResources().getColor(R.color.theme5));
        theme6.setBackgroundColor(context.getResources().getColor(R.color.theme6));
        theme7.setBackgroundColor(context.getResources().getColor(R.color.theme7));
        theme8.setBackgroundColor(context.getResources().getColor(R.color.theme8));
        theme9.setBackgroundColor(context.getResources().getColor(R.color.theme9));
        theme10.setBackgroundColor(context.getResources().getColor(R.color.theme10));
        theme11.setBackgroundColor(context.getResources().getColor(R.color.theme11));
        theme12.setBackgroundColor(context.getResources().getColor(R.color.theme12));
    }
}