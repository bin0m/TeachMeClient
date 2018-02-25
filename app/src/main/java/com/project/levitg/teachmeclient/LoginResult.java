package com.project.levitg.teachmeclient;

/**
 * Created by bin0m on 26.02.2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResult {

    @SerializedName("authenticationToken")
    @Expose
    private String authenticationToken;

    @SerializedName("user")
    @Expose
    private User user;

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
