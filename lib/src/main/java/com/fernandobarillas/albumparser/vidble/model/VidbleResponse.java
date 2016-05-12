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

package com.fernandobarillas.albumparser.vidble.model;

import com.fernandobarillas.albumparser.media.IMedia;
import com.fernandobarillas.albumparser.media.IMediaAlbum;
import com.fernandobarillas.albumparser.media.IMediaResponse;
import com.fernandobarillas.albumparser.vidble.api.VidbleApi;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

/**
 * Java representation of the Vidble album JSON response
 */
@Generated("org.jsonschema2pojo")
public class VidbleResponse implements IMediaResponse {

    String mOriginalUrl;

    @SerializedName("pics")
    @Expose
    public List<String> pics = new ArrayList<String>();
    VidbleAlbum mAlbum;
    private String mHash;

    @Override
    public IMediaAlbum getAlbum() {
        if (mAlbum == null) {
            mAlbum = new VidbleAlbum(pics);
        }
        return mAlbum;
    }

    @Override
    public String getApiDomain() {
        return VidbleApi.BASE_DOMAIN;
    }

    @Override
    public String getErrorMessage() {
        return null;
    }

    @Override
    public String getHash() {
        return mHash;
    }

    @Override
    public void setHash(String hash) {
        mHash = hash;
    }

    @Override
    public IMedia getMedia() {
        // No direct media, this is an album
        return null;
    }

    @Override
    public String getOriginalUrlString() {
        return mOriginalUrl;
    }

    @Override
    public URL getPreviewUrl() {
        return (getAlbum() != null) ? getAlbum().getPreviewUrl() : null;
    }

    @Override
    public boolean isAlbum() {
        return true;
    }

    @Override
    public boolean isSuccessful() {
        return getAlbum().isEmpty();
    }

    @Override
    public void setOriginalUrl(String originalUrl) {
        mOriginalUrl = originalUrl;
    }
}
