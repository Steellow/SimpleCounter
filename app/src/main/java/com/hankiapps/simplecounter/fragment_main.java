package com.hankiapps.simplecounter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import static android.content.Context.VIBRATOR_SERVICE;

public class fragment_main extends Fragment {

    //Views
    private TextView counter;
    private ConstraintLayout background;
    private ImageButton ib_menu_goal, ib_menu_main, ib_menu_fun, ib_menu_theme;
    private LinearLayout ll_menu_fun, ll_menu_theme, ll_menu_goal;
    private Animation anim_menu_close_text, anim_menu_open_text, anim_menu_open_rotate, anim_menu_close_rotate, anim_popup, anim_close_icons, anim_easteregg;
    private TextView tv_menu_fun, tv_menu_theme, tv_menu_goal;
    private CircularProgressBar progressBar;

    //Variables
    private int currentCount;
    private int goalMax;
    private boolean isMenuOpen;
    private boolean goalEnabled;
    private boolean funModeEnabled;

    //Keys
    private static final String KEY_CURRENT_COUNT = "current_count";
    private static final String KEY_MENU_OPEN = "menu_opened";
    private static final String KEY_GOAL_ENABLED = "goal_enabled";
    private static final String KEY_GOAL_MAX = "goal_max";

    //Request Codes
    private static final int RC_RESET_DIALOG = 0;
    private static final int RC_PICK_THEME = 1;
    private static final int RC_SET_GOAL = 2;

    //Extras
    private static final String INT_EXTRA = "theme_choice";

    //Settings
    public String PREF_FILE_KEY;
    private FunMode funMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        //Settings Stuff
        PREF_FILE_KEY = getString(R.string.pref_file_key);
        SharedPreferences sharedPrefs = getActivity().getSharedPreferences(PREF_FILE_KEY, Context.MODE_PRIVATE);

        this.funMode = FunMode.getInstance(getActivity());
        funModeEnabled = false;

        currentCount = sharedPrefs.getInt(getString(R.string.KEY_CURRENTCOUNT), 0);
        goalMax = sharedPrefs.getInt(getString(R.string.KEY_CURRENTGOAL), 0);
        if(goalMax > 0) {
            goalEnabled = true;
        }

        //Screen orientation save
        if(savedInstanceState != null) {
            isMenuOpen = savedInstanceState.getBoolean(KEY_MENU_OPEN);
        } else {
            isMenuOpen = false;
        }


        //Define Views//
        ////////////////
        this.background = v.findViewById(R.id.view_background);

        //Menu buttons
        ib_menu_main = (ImageButton) v.findViewById(R.id.menu_main);
        ib_menu_fun = (ImageButton) v.findViewById(R.id.menu_fun);
        ib_menu_theme = (ImageButton) v.findViewById(R.id.menu_theme);
        ib_menu_goal = (ImageButton) v.findViewById(R.id.menu_goal);
        ll_menu_fun = (LinearLayout) v.findViewById(R.id.fab1_with_text);
        ll_menu_theme = (LinearLayout) v.findViewById(R.id.fab2_with_text);
        ll_menu_goal = (LinearLayout) v.findViewById(R.id.fab3_with_text);
        tv_menu_fun = (TextView) v.findViewById(R.id.tv_menu_fun);
        tv_menu_theme = (TextView) v.findViewById(R.id.tv_menu_theme);
        tv_menu_goal = (TextView) v.findViewById(R.id.tv_menu_goal);

        //Animations
        anim_menu_close_text = AnimationUtils.loadAnimation(getContext(), R.anim.menu_close_text);
        anim_menu_open_text = AnimationUtils.loadAnimation(getContext(), R.anim.menu_open_text);
        anim_menu_open_rotate = AnimationUtils.loadAnimation(getContext(), R.anim.anim_menu_open_flip);
        anim_menu_close_rotate = AnimationUtils.loadAnimation(getContext(), R.anim.anim_menu_close_flip);
        anim_popup = AnimationUtils.loadAnimation(getContext(), R.anim.popup);
        anim_close_icons = AnimationUtils.loadAnimation(getContext(), R.anim.menu_close_icons);
        anim_easteregg = AnimationUtils.loadAnimation(getContext(), R.anim.easteregg);

        this.counter = v.findViewById(R.id.textView);
        progressBar = v.findViewById(R.id.progressBar);
        ////////////////////////////////////////////

        this.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCount++;
                savePref(getString(R.string.KEY_CURRENTCOUNT), currentCount);
                updateCounter();
                if(isMenuOpen) closeMenu();
            }
        });
        this.background.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                FragmentManager manager = getFragmentManager();
                dialog_ResetCounter dialog = new dialog_ResetCounter();
                dialog.setTargetFragment(fragment_main.this, RC_RESET_DIALOG);
                dialog.show(manager, null);
                vibrate();
                return true;
            }
        });

        if(goalEnabled) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgressMax(goalMax);
            tv_menu_goal.setText(getText(R.string.menu_hide_goal));
        }

        //Menu button bigger touch area
        //TODO: Increase every button's touch area
        increaseTouchArea(ib_menu_main);

        if(isMenuOpen) setMenuVisible();

        ib_menu_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isMenuOpen) {
                    closeMenu();
                } else {
                    openMenu();
                }
            }
        });

        /*
        ib_menu_fun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Coming soon", Toast.LENGTH_LONG).show();
                //funModeEnabled ^= true;
            }
        });
        */

        ib_menu_theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                dialog_ThemePicker dialog = new dialog_ThemePicker();
                dialog.setTargetFragment(fragment_main.this, RC_PICK_THEME);
                dialog.show(manager, null);
            }
        });

        ib_menu_goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goalEnabled) {
                    closeMenu();
                    progressBar.setVisibility(View.INVISIBLE);
                    goalEnabled = false;
                    removePref(getString(R.string.KEY_CURRENTGOAL));
                    tv_menu_goal.setText(R.string.menu_set_goal);
                } else {
                    FragmentManager manager = getFragmentManager();
                    dialog_SetGoal dialog = new dialog_SetGoal();
                    dialog.setTargetFragment(fragment_main.this, RC_SET_GOAL);
                    dialog.show(manager, null);
                }
            }
        });

        //Easteregg
        //TODO: Easteregg doesn't work correctly if menu is open
        ib_menu_main.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ib_menu_main.startAnimation(anim_easteregg);
                return true;
            }
        });

        updateCounter();
        applyTheme(v, sharedPrefs.getInt(getString(R.string.KEY_THEME), ContextCompat.getColor(requireContext(), R.color.theme1)));

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK) return;
        if(requestCode == RC_RESET_DIALOG) {
            resetCounter();
        } else if(requestCode == RC_PICK_THEME) {
            int colorCode = data.getIntExtra(INT_EXTRA, Color.parseColor("#ff1744"));
            applyTheme(getView(), colorCode);
            savePref(getString(R.string.KEY_THEME), colorCode);
        } else if(requestCode == RC_SET_GOAL) {
            goalMax = data.getIntExtra(INT_EXTRA, 0);
            savePref(getString(R.string.KEY_CURRENTGOAL), goalMax);
            progressBar.setProgressMax(goalMax);
            progressBar.setVisibility(View.VISIBLE);
            goalEnabled = true;
            updateProgressBar();
            closeMenu();
            tv_menu_goal.setText(R.string.menu_hide_goal);

            //Hide keyboard
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_CURRENT_COUNT, currentCount);
        savedInstanceState.putBoolean(KEY_MENU_OPEN, isMenuOpen);
        savedInstanceState.putBoolean(KEY_GOAL_ENABLED, goalEnabled);
        savedInstanceState.putInt(KEY_GOAL_MAX, goalMax);
    }

    private void savePref(String KEY, int i) {
        SharedPreferences sharedPrefs = getActivity().getSharedPreferences(PREF_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt(KEY, i);
        editor.apply();
    }

    private void removePref(String KEY) {
        SharedPreferences sharedPrefs = getActivity().getSharedPreferences(PREF_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.remove(KEY);
        editor.apply();
    }

    public static Intent newIntent(int i) {
        Intent intent = new Intent();
        intent.putExtra(INT_EXTRA, i);
        return intent;
    }

    private void applyTheme(View view, int colorInt) {
        if(colorInt == 0) return;

        view.setBackgroundColor(colorInt);
        progressBar.setBackgroundColor(colorInt);
    }

    private void closeMenu() {
        //Main button animation
        ib_menu_main.startAnimation(anim_menu_close_rotate);

        //Hide TextView animations
        //tv_menu_fun.startAnimation(anim_menu_close_text);
        tv_menu_theme.startAnimation(anim_menu_close_text);
        tv_menu_goal.startAnimation(anim_menu_close_text);

        //Hide Icons animations
        //ib_menu_fun.startAnimation(anim_close_icons);
        ib_menu_theme.startAnimation(anim_close_icons);
        ib_menu_goal.startAnimation(anim_close_icons);

        //Set everything invisible (can't use LinearLayout.setVisibility here, it cuts animations)
        //tv_menu_fun.setVisibility(View.INVISIBLE);
        tv_menu_theme.setVisibility(View.INVISIBLE);
        tv_menu_goal.setVisibility(View.INVISIBLE);
        //ib_menu_fun.setVisibility(View.INVISIBLE);
        ib_menu_theme.setVisibility(View.INVISIBLE);
        ib_menu_goal.setVisibility(View.INVISIBLE);

        //Set clickable false
        //ib_menu_fun.setClickable(false);
        ib_menu_theme.setClickable(false);
        ib_menu_goal.setClickable(false);

        isMenuOpen = false;
    }

    private void openMenu() {
        //Main button animation
        ib_menu_main.startAnimation(anim_menu_open_rotate);

        //TextView animations
        //tv_menu_fun.startAnimation(anim_menu_open_text);
        tv_menu_theme.startAnimation(anim_menu_open_text);
        tv_menu_goal.startAnimation(anim_menu_open_text);

        //Display Icons animations
        //ib_menu_fun.startAnimation(anim_popup);
        ib_menu_theme.startAnimation(anim_popup);
        ib_menu_goal.startAnimation(anim_popup);

        setMenuVisible();
    }

    private void setMenuVisible() {
        //Set everything visible
        //ll_menu_fun.setVisibility(View.VISIBLE);
        ll_menu_theme.setVisibility(View.VISIBLE);
        ll_menu_goal.setVisibility(View.VISIBLE);

        //Set icons clickable
        //ib_menu_fun.setClickable(true);
        ib_menu_theme.setClickable(true);
        ib_menu_goal.setClickable(true);

        isMenuOpen = true;
    }

    private void resetCounter() {
        currentCount = 0;
        savePref(getString(R.string.KEY_CURRENTCOUNT), currentCount);
        updateCounter();
    }

    private void updateCounter() {
        this.counter.setText(Integer.toString(currentCount));
        updateProgressBar();
        if(funModeEnabled) {
            funMode.doFun();
        }
    }

    private void updateProgressBar() {
        if(goalEnabled) {
            progressBar.setProgressWithAnimation(currentCount, 400);
        }
    }

    private void vibrate() {
        if(Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            ((Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE)).vibrate(150);
        }
    }

    private void increaseTouchArea(final ImageButton imageButton) {
        final View parent = (View) imageButton.getParent();
        final int amount = 55;
        parent.post(new Runnable() {
            @Override
            public void run() {
                final Rect rect = new Rect();
                imageButton.getHitRect(rect);
                rect.top -= amount;
                rect.left -= amount;
                rect.bottom += amount;
                rect.right += amount;
                parent.setTouchDelegate(new TouchDelegate(rect,imageButton));
            }
        });
    }
}