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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fb on 7/18/16.
 */
public class CheckLinkResponse {

    @SerializedName("urlKnown")
    @Expose
    private boolean urlKnown;
    @SerializedName("gfyUrl")
    @Expose
    private String  gfyUrl;
    @SerializedName("webmUrl")
    @Expose
    private String  webmUrl;
    @SerializedName("mp4Url")
    @Expose
    private String  mp4Url;
    @SerializedName("gifUrl")
    @Expose
    private String  gifUrl;
    @SerializedName("frameRate")
    @Expose
    private String  frameRate;
    @SerializedName("gfyName")
    @Expose
    private String  gfyName;

    @Override
    public String toString() {
        return "CajaxCheckResponse{"
                + "urlKnown="
                + urlKnown
                + ", gfyUrl='"
                + gfyUrl
                + '\''
                + ", webmUrl='"
                + webmUrl
                + '\''
                + ", mp4Url='"
                + mp4Url
                + '\''
                + ", gifUrl='"
                + gifUrl
                + '\''
                + ", frameRate='"
                + frameRate
                + '\''
                + ", gfyName='"
                + gfyName
                + '\''
                + '}';
    }

    public String getFrameRate() {
        return frameRate;
    }

    public String getGfyName() {
        return gfyName;
    }

    public String getGfyUrl() {
        return gfyUrl;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public String getMp4Url() {
        return mp4Url;
    }

    public String getWebmUrl() {
        return webmUrl;
    }

    public boolean isUrlKnown() {
        return urlKnown;
    }
}
