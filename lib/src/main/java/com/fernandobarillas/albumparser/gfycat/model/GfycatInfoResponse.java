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

package com.fernandobarillas.albumparser.gfycat.model;

import com.fernandobarillas.albumparser.media.BaseApiResponse;
import com.squareup.moshi.Json;

import java.net.URL;

public class GfycatInfoResponse extends BaseApiResponse<GfyItem> {

    @Json(name = "gfyItem")
    public GfyItem gfyItem;
    @Json(name = "error")
    public String  error;

    @Override
    public String getErrorMessage() {
        return error;
    }

    @Override
    public GfyItem getMedia() {
        return gfyItem;
    }

    @Override
    public URL getPreviewUrl() {
        return (gfyItem != null) ? gfyItem.getPreviewUrl() : null;
    }

    @Override
    public boolean isSuccessful() {
        return gfyItem != null;
    }

    @Override
    public String toString() {
        return "GfycatInfoResponse{" + "gfyItem=" + gfyItem + ", error='" + error + '\'' + '}';
    }
}
