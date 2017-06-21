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

import com.squareup.moshi.Json;

/**
 * Created by fb on 7/18/16.
 */
public class TranscodeResponse {
    @Json(name = "isOk")
    public Boolean isOk;
    @Json(name = "error")
    public String  error;
    @Json(name = "gfyname")
    public String  gfyname;
    @Json(name = "gfyName")
    public String  gfyName;
    @Json(name = "gfysize")
    public Integer gfysize;
    @Json(name = "gifSize")
    public Integer gifSize;
    @Json(name = "gifWidth")
    public Integer gifWidth;
    @Json(name = "mp4Url")
    public String  mp4Url;
    @Json(name = "frameRate")
    public Integer frameRate;
    @Json(name = "webmUrl")
    public String  webmUrl;
    @Json(name = "gifUrl")
    public String  gifUrl;

    @Override
    public String toString() {
        return "TranscodeResponse{"
                + "isOk="
                + isOk
                + ", error='"
                + error
                + '\''
                + ", gfyName='"
                + gfyName
                + '\''
                + ", mp4Url='"
                + mp4Url
                + '\''
                + '}';
    }

    public String getError() {
        return error;
    }

    public String getGfyName() {
        return gfyName;
    }

    public String getMp4Url() {
        return mp4Url;
    }

    public boolean isOk() {
        return isOk;
    }
}
