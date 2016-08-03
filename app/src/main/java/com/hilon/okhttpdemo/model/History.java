
package com.hilon.okhttpdemo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class History {

    @SerializedName("user")
    @Expose
    public User user;
    @SerializedName("version")
    @Expose
    public String version;
    @SerializedName("committed_at")
    @Expose
    public String committedAt;
    @SerializedName("change_status")
    @Expose
    public ChangeStatus changeStatus;
    @SerializedName("url")
    @Expose
    public String url;

}
