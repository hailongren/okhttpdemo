
package com.hilon.okhttpdemo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Git {

    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("forks_url")
    @Expose
    public String forksUrl;
    @SerializedName("commits_url")
    @Expose
    public String commitsUrl;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("git_pull_url")
    @Expose
    public String gitPullUrl;
    @SerializedName("git_push_url")
    @Expose
    public String gitPushUrl;
    @SerializedName("html_url")
    @Expose
    public String htmlUrl;
    @SerializedName("files")
    @Expose
    public Files files;
    @SerializedName("public")
    @Expose
    public Boolean _public;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("comments")
    @Expose
    public Integer comments;
    @SerializedName("user")
    @Expose
    public Object user;
    @SerializedName("comments_url")
    @Expose
    public String commentsUrl;
    @SerializedName("owner")
    @Expose
    public Owner owner;
    @SerializedName("forks")
    @Expose
    public List<Object> forks = new ArrayList<Object>();
    @SerializedName("history")
    @Expose
    public List<History> history = new ArrayList<History>();
    @SerializedName("truncated")
    @Expose
    public Boolean truncated;

}
