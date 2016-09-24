package com.fernandobarillas.albumparser.giphy.model;

import com.fernandobarillas.albumparser.media.BaseApiResponse;
import com.fernandobarillas.albumparser.media.IMedia;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GiphyResponse extends BaseApiResponse {

    @SerializedName("data")
    @Expose
    public Data data;
    @SerializedName("meta")
    @Expose
    public Meta meta;

    @Override
    public IMedia getMedia() {
        return data != null ? data.images : null;
    }

    @Override
    public boolean isSuccessful() {
        return getMedia() != null;
    }
}
