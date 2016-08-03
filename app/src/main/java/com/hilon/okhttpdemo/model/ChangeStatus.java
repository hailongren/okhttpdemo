
package com.hilon.okhttpdemo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeStatus {

    @SerializedName("total")
    @Expose
    public Integer total;
    @SerializedName("additions")
    @Expose
    public Integer additions;
    @SerializedName("deletions")
    @Expose
    public Integer deletions;

}
