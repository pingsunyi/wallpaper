package com.example.pingsunyi.wallpaper.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.example.pingsunyi.wallpaper.R;

public class Functions {
    public static void changeMainFragment(FragmentActivity fragmentActivity, Fragment fragment) {
        fragmentActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }
}
