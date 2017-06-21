package com.fernandobarillas.albumparser.giphy.model;

import com.squareup.moshi.Json;

public class Meta {

    @Json(name = "status")
    public Integer status;
    @Json(name = "msg")
    public String  msg;
    @Json(name = "response_id")
    public String  responseId;
}
