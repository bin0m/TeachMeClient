package com.project.levitg.teachmeclient;

import java.util.Date;

/**
 * Created by Greg L on 04.12.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Student {

    @SerializedName("deleted")
    @Expose(serialize = false)
    private boolean deleted;

    @SerializedName("updatedAt")
    @Expose(serialize = false)
    private String updatedAt;

    @SerializedName("createdAt")
    @Expose(serialize = false)
    private String createdAt;

    @SerializedName("version")
    @Expose(serialize = false)
    private String version;

    @SerializedName("id")
    @Expose(serialize = false)
    private String id;

    @SerializedName("registerDate")
    @Expose
    private String registerDate;

    @SerializedName("completed\u0421oursesCount")
    @Expose
    private int completedOursesCount;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("fullName")
    @Expose
    private String fullName;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public int getCompletedOursesCount() {
        return completedOursesCount;
    }

    public void setCompletedOursesCount(int completedOursesCount) {
        this.completedOursesCount = completedOursesCount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}