package com.example.app_buoi2.auth;

import android.content.Context;
import android.content.SharedPreferences;

/** Trạng thái đăng nhập lưu bằng SharedPreferences */
public final class AuthPreferences {

    private static final String PREFS = "movie_ticket_auth";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USERNAME = "username";

    private AuthPreferences() {}

    public static void login(Context context, long userId, String username) {
        prefs(context).edit().putLong(KEY_USER_ID, userId).putString(KEY_USERNAME, username).apply();
    }

    public static void logout(Context context) {
        prefs(context).edit().remove(KEY_USER_ID).remove(KEY_USERNAME).apply();
    }

    public static boolean isLoggedIn(Context context) {
        return prefs(context).getLong(KEY_USER_ID, -1L) > 0;
    }

    public static long getUserId(Context context) {
        return prefs(context).getLong(KEY_USER_ID, -1L);
    }

    public static String getUsername(Context context) {
        return prefs(context).getString(KEY_USERNAME, "");
    }

    private static SharedPreferences prefs(Context context) {
        return context.getApplicationContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }
}
