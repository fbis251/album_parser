package com.fernandobarillas.albumparser.giphy.model;

import com.fernandobarillas.albumparser.media.BaseApiResponse;
import com.squareup.moshi.Json;

public class GiphyResponse extends BaseApiResponse<Image> {

    @Json(name = "data")
    public Data data;
    @Json(name = "meta")
    public Meta meta;

    @Override
    public Image getMedia() {
        return data != null ? data.images : null;
    }

    @Override
    public boolean isSuccessful() {
        return getMedia() != null;
    }
}
