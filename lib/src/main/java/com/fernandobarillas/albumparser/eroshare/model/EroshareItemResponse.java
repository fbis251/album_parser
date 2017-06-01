/*
 * The MIT License (MIT)
 * Copyright (c) 2017 Fernando Barillas (FBis251)
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

import com.fernandobarillas.albumparser.media.IApiResponse;
import com.fernandobarillas.albumparser.media.IMedia;
import com.fernandobarillas.albumparser.media.IMediaAlbum;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * A single media response from the Eroshare API
 */
public class EroshareItemResponse extends EroshareItem implements IApiResponse {

    @SerializedName("message")
    @Expose
    public String message;

    @Override
    public IMediaAlbum getAlbum() {
        // This is a single item, not an album
        return null;
    }

    @Override
    public String getErrorMessage() {
        return message;
    }

    @Override
    public String getJson() {
        return new Gson().toJson(this);
    }

    @Override
    public IMedia getMedia() {
        return this;
    }

    @Override
    public boolean isAlbum() {
        return false;
    }

    @Override
    public boolean isSuccessful() {
        return message == null;
    }
}
