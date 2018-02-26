package com.project.levitg.teachmeclient;

/**
 * Created by bin0m on 27.02.2018.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AuthTokenCacheManager {

    public static final String SHAREDPREFFILE = "temp";
    public static final String USERIDPREF = "uid";
    public static final String TOKENPREF = "tkn";
    public static final String USERNAMEPREF = "unm";

    public static void cacheUserToken(Context context, LoginResult loginResult) {
        SharedPreferences prefs = context.getSharedPreferences(SHAREDPREFFILE, Context.MODE_PRIVATE);
        Editor editor = prefs.edit();
        editor.putString(TOKENPREF, loginResult.getAuthenticationToken());
        editor.putString(USERIDPREF, loginResult.getUser().getId());
        editor.putString(USERNAMEPREF, loginResult.getUser().getLogin());
        editor.commit();
    }

    public static CachedUser loadUserTokenCache(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHAREDPREFFILE, Context.MODE_PRIVATE);

        CachedUser cachedUser = new CachedUser();
        cachedUser.setAuthToken(prefs.getString(TOKENPREF, null));
        cachedUser.setId(prefs.getString(USERIDPREF, null));
        cachedUser.setUserName(prefs.getString(USERNAMEPREF, null));

        return cachedUser;
    }
}
