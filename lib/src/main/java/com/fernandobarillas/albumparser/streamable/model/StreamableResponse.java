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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

/**
 * Created by fb on 5/10/16.
 */
@Generated("org.jsonschema2pojo")
public class StreamableResponse {
    protected static final String PROTOCOL_HTTPS = "https:";

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

    @Override
    public String toString() {
        return "StreamableResponse{" +
                "files=" + files +
                ", highQuality=" + getVideoUrl(true) +
                ", lowQuality=" + getVideoUrl(false) +
                ", thumbnailUrl='" + getThumbnailUrl() + '\'' +
                '}';
    }

    public Files getFiles() {
        return files;
    }

    public int getStatus() {
        return status;
    }

    public String getThumbnailUrl() {
        return PROTOCOL_HTTPS + thumbnailUrl;
    }

    String getVideoUrl(boolean highQuality) {
        if (files == null) return null;
        String    resultUrl = null;
        Mp4Mobile mp4Mobile = files.mp4Mobile;
        if (mp4Mobile != null) {
            resultUrl = mp4Mobile.getUrl();
        }

        if (highQuality) {
            Mp4 mp4 = files.mp4;
            if (mp4 != null) {
                resultUrl = mp4.getUrl();
            }
        }

        return resultUrl;
    }

}
