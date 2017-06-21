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

package com.fernandobarillas.albumparser.vidme.model;

import com.fernandobarillas.albumparser.media.BaseApiResponse;
import com.squareup.moshi.Json;

import java.net.URL;

public class VidmeResponse extends BaseApiResponse<Video> {
    @Json(name = "status")
    public Boolean status;
    @Json(name = "error")
    public String  error;
    @Json(name = "code")
    public String  code;
    @Json(name = "video")
    public Video   video;

    @Override
    public String getErrorMessage() {
        return error;
    }

    @Override
    public Video getMedia() {
        return video;
    }

    @Override
    public URL getPreviewUrl() {
        return (video != null) ? video.getPreviewUrl() : null;
    }

    @Override
    public boolean isSuccessful() {
        return status != null ? status : false;
    }

    @Override
    public String toString() {
        return "VidmeResponse{"
                + "status="
                + status
                + ", error='"
                + error
                + '\''
                + ", code='"
                + code
                + '\''
                + ", video="
                + video
                + '}';
    }
}
