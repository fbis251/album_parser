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
import com.fernandobarillas.albumparser.media.IMedia;
import com.fernandobarillas.albumparser.streamable.api.StreamableApi;
import com.fernandobarillas.albumparser.util.ParseUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import static com.fernandobarillas.albumparser.media.IMedia.PROTOCOL_HTTPS;

/**
 * Created by fb on 5/10/16.
 */
@Generated("org.jsonschema2pojo")
public class StreamableResponse extends BaseApiResponse {
    // Video schema status responses according to: https://streamable.com/documentation
    public static final int STATUS_UPLOADING  = 0;
    public static final int STATUS_PROCESSING = 1;
    public static final int STATUS_READY      = 2;
    public static final int STATUS_ERROR      = 3;
    @SerializedName("status")
    @Expose
    public int    status;
    @SerializedName("files")
    @Expose
    public Files  files;
    @SerializedName("url_root")
    @Expose
    public String urlRoot;
    @SerializedName("thumbnail_url")
    @Expose
    public String thumbnailUrl;
    @SerializedName("formats")
    @Expose
    public List<String> formats = new ArrayList<String>();
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("percent")
    @Expose
    public int    percent;

    private StreamableMedia mStreamableMedia;

    @Override
    public String getApiDomain() {
        return StreamableApi.BASE_DOMAIN;
    }

    @Override
    public String getHash() {
        return ParseUtils.hashRegex(url, StreamableApi.BASE_DOMAIN + "/(\\w+)");
    }

    @Override
    public IMedia getMedia() {
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
        return status == STATUS_READY;
    }
}
