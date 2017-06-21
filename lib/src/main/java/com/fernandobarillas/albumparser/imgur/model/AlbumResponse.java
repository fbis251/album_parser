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

import com.fernandobarillas.albumparser.media.BaseApiResponse;
import com.fernandobarillas.albumparser.media.IMediaAlbum;
import com.squareup.moshi.Json;

import java.net.URL;

public class AlbumResponse extends BaseApiResponse<Image> {
    @Json(name = "data")
    public AlbumData data;
    @Json(name = "success")
    public Boolean   success;
    @Json(name = "status")
    public Integer   status;

    @Override
    public IMediaAlbum<Image> getAlbum() {
        return data;
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
        // This API always returns true in the response, determine success from non-empty album instead
        return (data != null && !data.isEmpty());
    }

    public void setLowQuality(String lowQualitySize) {
        if (data == null) return;
        data.setLowQuality(lowQualitySize);
    }

    public void setPreviewQuality(String previewSize) {
        if (data == null) return;
        data.setPreviewQuality(previewSize);
    }
}
