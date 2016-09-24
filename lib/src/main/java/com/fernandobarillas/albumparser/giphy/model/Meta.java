package com.fernandobarillas.albumparser.giphy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("status")
    @Expose
    public int    status;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("response_id")
    @Expose
    public String responseId;

}
