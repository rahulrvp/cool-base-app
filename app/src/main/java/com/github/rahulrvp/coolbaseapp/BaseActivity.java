package com.github.rahulrvp.coolbaseapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Rahul Raveendran V P
 *         Created on 13/1/17 @ 3:39 PM
 *         https://github.com/rahulrvp
 */

public class BaseActivity extends AppCompatActivity {

    protected Context mContext;

    private ProgressDialog mProgressDialog;
    private View mContentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        /**
         * Activity orientation change
         * -------------------------
         * If developer wants to lock all the activities in portrait mode, instead of
         * doing it individually for each activity in manifest, they can do it like this.
         *
         * Uncomment the following line.
         */

        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        mContentView = findViewById(android.R.id.content);

        /**
         * Activity background color change
         * --------------------------------
         * Developer can set a common background color to all the activities in the app.
         *
         * Uncomment the following line and pass in a valid color resource id.
         */

        // setContentViewBackground(R.color.background)

        /**
         * Activity common font
         * --------------------
         * Developer can set a common font for the entire activity. Uncommenting the
         * following method and passing in a typeface object into it will set that
         * typeface to all the TexView children available in the current content view.
         *
         * Note: This will not alter the font of Lists' cells as they're getting added
         * after this.
         */

        // typeface = null; /* read from file or assets */
        // setCommonFont(typeface)
    }

    protected void setContentViewBackground(int colorResId) {
        if (mContentView != null) {
            mContentView.setBackgroundColor(ContextCompat.getColor(mContext, colorResId));
        }
    }

    protected void setCommonFont(Typeface typeface) {
        setCommonFont(mContentView, typeface);
    }

    private void setCommonFont(View rootView, Typeface typeface) {
        if (rootView != null && typeface != null) {
            if (rootView instanceof TextView) {
                ((TextView) rootView).setTypeface(typeface);
            } else if (rootView instanceof ViewGroup) {
                ViewGroup cView = (ViewGroup) rootView;
                int vCount = cView.getChildCount();
                for (int index = 0; index < vCount; index++) {
                    setCommonFont(rootView, typeface);
                }
            }
        }
    }

    protected void showToast(int stringResId, int duration) {
        showToast(getString(stringResId), duration);
    }

    protected void showToast(String message, int duration) {
        Toast.makeText(mContext, message, duration).show();
    }

    protected void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected void showProgressDialog(int stringResId, boolean isCancellable) {
        showProgressDialog(getString(stringResId), isCancellable);
    }

    protected void showProgressDialog(String message, boolean isCancellable) {
        if (!isFinishing()) {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(mContext);
                mProgressDialog.setCancelable(isCancellable);
            }

            mProgressDialog.setMessage(message);
            mProgressDialog.show();
        }
    }

    protected void hideProgressDialog() {
        if (!isFinishing()) {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
    }

    public boolean isConnectedToInternet() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public boolean hasPermission(Context context, String permission) {
        boolean status = false;

        try {
            status = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
        } catch (Throwable ignored) {
        }

        return status;
    }
}
