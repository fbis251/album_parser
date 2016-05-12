/*
 * The MIT License (MIT)
 * Copyright (c) 2016 Fernando Barillas (FBis251)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.fernandobarillas.albumparser.imgur.model;

import com.fernandobarillas.albumparser.imgur.api.ImgurApi;
import com.fernandobarillas.albumparser.media.IMedia;
import com.fernandobarillas.albumparser.media.IMediaAlbum;
import com.fernandobarillas.albumparser.media.IMediaResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.URL;

import javax.annotation.Generated;

/**
 * Created by fb on 5/3/16.
 */

@Generated("org.jsonschema2pojo")
public class ImgurResponse implements IMediaResponse {
    @SerializedName("data")
    @Expose
    public Data    data;
    @SerializedName("success")
    @Expose
    public boolean success;
    @SerializedName("status")
    @Expose
    public int     status;

    private String mAlbumHash;
    private String mOriginalUrl;

    @Override
    public IMediaAlbum getAlbum() {
        return data;
    }

    @Override
    public String getApiDomain() {
        return ImgurApi.BASE_DOMAIN;
    }

    @Override
    public String getErrorMessage() {
        return null;
    }

    @Override
    public String getHash() {
        return mAlbumHash;
    }

    @Override
    public void setHash(String hash) {
        mAlbumHash = hash;
    }

    @Override
    public IMedia getMedia() {
        // Not supported by this API call
        return null;
    }

    @Override
    public String getOriginalUrlString() {
        return mOriginalUrl;
    }

    @Override
    public URL getPreviewUrl() {
        return (data != null) ? data.getPreviewUrl() : null;
    }

    @Override
    public boolean isAlbum() {
        // This API call is for albums only
        return true;
    }

    @Override
    public boolean isSuccessful() {
        // This API always returns true in the response, determine succes from non-empty album instead
        return (data != null && !data.isEmpty());
    }

    @Override
    public void setOriginalUrl(String originalUrl) {
        mOriginalUrl = originalUrl;
    }
}
