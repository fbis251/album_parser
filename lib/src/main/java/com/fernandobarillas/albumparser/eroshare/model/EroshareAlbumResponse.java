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

package com.fernandobarillas.albumparser.eroshare.model;

import com.fernandobarillas.albumparser.media.BaseApiResponse;
import com.fernandobarillas.albumparser.media.IMediaAlbum;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.URL;
import java.util.List;

/**
 * Created by fb on 12/27/16.
 */
public class EroshareAlbumResponse extends BaseApiResponse {
    @SerializedName("id")
    @Expose
    public String  id;
    @SerializedName("title")
    @Expose
    public String  title;
    @SerializedName("slug")
    @Expose
    public String  slug;
    @SerializedName("created_at")
    @Expose
    public String  createdAt;
    @SerializedName("views")
    @Expose
    public int     views;
    @SerializedName("gender_male")
    @Expose
    public int     genderMale;
    @SerializedName("gender_female")
    @Expose
    public int     genderFemale;
    @SerializedName("secret")
    @Expose
    public boolean secret;
    @SerializedName("url")
    @Expose
    public String  url;
    @SerializedName("items")
    @Expose
    public List<EroshareItem> items = null;

    private EroshareAlbum mAlbum;

    @Override
    public IMediaAlbum getAlbum() {
        if (mAlbum == null) {
            mAlbum = new EroshareAlbum(items);
        }
        return mAlbum;
    }

    @Override
    public URL getPreviewUrl() {
        return getAlbum() != null ? getAlbum().getPreviewUrl() : null;
    }

    @Override
    public boolean isAlbum() {
        // This is an album only API response
        return true;
    }

    @Override
    public boolean isSuccessful() {
        return items != null && items.size() > 0;
    }
}
