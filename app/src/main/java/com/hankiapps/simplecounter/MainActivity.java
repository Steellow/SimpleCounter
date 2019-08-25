package com.hankiapps.simplecounter;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.util.logging.Handler;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new fragment_main();
    }


    //Double click back to exit
    private static final int time_interval = 2000;
    private long backPressed;

    @Override
    public void onBackPressed() {
        if(backPressed + time_interval > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            backPressed = System.currentTimeMillis();
        }
    }
}