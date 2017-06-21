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

package com.fernandobarillas.albumparser.streamable.model;

import com.fernandobarillas.albumparser.media.BaseApiResponse;
import com.squareup.moshi.Json;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static com.fernandobarillas.albumparser.media.IMedia.PROTOCOL_HTTPS;

public class StreamableResponse extends BaseApiResponse<StreamableMedia> {

    // Video schema status responses according to: https://streamable.com/documentation
    public static final int STATUS_UPLOADING  = 0;
    public static final int STATUS_PROCESSING = 1;
    public static final int STATUS_READY      = 2;
    public static final int STATUS_ERROR      = 3;

    @Json(name = "status")
    public Integer      status;
    @Json(name = "files")
    public Files        files;
    @Json(name = "url_root")
    public String       urlRoot;
    @Json(name = "thumbnail_url")
    public String       thumbnailUrl;
    @Json(name = "formats")
    public List<String> formats;
    @Json(name = "url")
    public String       url;
    @Json(name = "message")
    public String       message;
    @Json(name = "title")
    public String       title;
    @Json(name = "percent")
    public Integer      percent;

    private StreamableMedia mStreamableMedia;

    @Override
    public StreamableMedia getMedia() {
        if (files == null) return null;

        if (mStreamableMedia == null) {
            mStreamableMedia = new StreamableMedia(files.mp4, files.mp4Mobile, getPreviewUrl());
        }
        return mStreamableMedia;
    }

    @Override
    public URL getPreviewUrl() {
        try {
            return new URL(PROTOCOL_HTTPS + ":" + thumbnailUrl);
        } catch (MalformedURLException ignored) {
        }
        return null;
    }

    @Override
    public boolean isSuccessful() {
        return status != null && status == STATUS_READY;
    }
}
