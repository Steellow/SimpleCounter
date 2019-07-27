package com.hankiapps.simplecounter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class dialog_SetGoal extends DialogFragment {

    //Views
    private EditText editText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_setgoal, null);

        AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.setTitle("Set goal:");
        dialog.setView(view);
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showKeyboard();
            }
        });
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendResult(Integer.parseInt(editText.getText().toString()));
            }
        });
        dialog.show();

        editText = view.findViewById(R.id.editText);
        showKeyboard();

        return dialog;
    }

    public void showKeyboard() {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public void sendResult(int i) {
        if(getTargetFragment() == null) {
            return;
        }
        Intent intent = fragment_main.newIntent(i);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        dismiss();
    }
}
