package com.andreas.projekuts.UnitTest;

import android.content.Context;
import android.content.Intent;

import com.andreas.projekuts.LoginActivity;

public class ActivityUtil {
    private Context context;
    public ActivityUtil(Context context) {
        this.context = context;
    }
    public void startLoginActivity() {
        context.startActivity(new Intent(context, LoginActivity.class));
    }
}
