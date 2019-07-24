package com.hankiapps.simplecounter;

import android.content.Context;
import android.widget.Toast;

public final class FunMode {

    private static FunMode INSTANCE;
    private Context context;

    private FunMode(Context context){
        this.context = context;
    }

    public static FunMode getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = new FunMode(context);
        }
        return INSTANCE;
    }

    public void doFun() {
        test();
    }

    public void changeFont() {

    }

    public void test () {
        Toast.makeText(this.context, "TEST", Toast.LENGTH_LONG).show();
    }
}

