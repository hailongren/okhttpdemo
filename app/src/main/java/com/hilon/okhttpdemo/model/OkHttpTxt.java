
package com.hilon.okhttpdemo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OkHttpTxt {

    @SerializedName("filename")
    @Expose
    public String filename;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("language")
    @Expose
    public String language;
    @SerializedName("raw_url")
    @Expose
    public String rawUrl;
    @SerializedName("size")
    @Expose
    public Integer size;
    @SerializedName("truncated")
    @Expose
    public Boolean truncated;
    @SerializedName("content")
    @Expose
    public String content;

}
