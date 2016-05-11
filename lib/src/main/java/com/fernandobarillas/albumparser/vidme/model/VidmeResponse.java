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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * Created by fb on 5/10/16.
 */
@Generated("org.jsonschema2pojo")
public class VidmeResponse {

    @SerializedName("status")
    @Expose
    public boolean status;
    @SerializedName("error")
    @Expose
    public String  error;
    @SerializedName("code")
    @Expose
    public String  code;
    @SerializedName("video")
    @Expose
    public Video   video;

    @Override
    public String toString() {
        return "VidmeResponse{" +
                "status=" + status +
                ", error='" + error + '\'' +
                ", code='" + code + '\'' +
                ", video=" + video +
                '}';
    }

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getPreviewUrl() {
        return (video == null) ? video.getThumbnailUrl() : null;
    }

    public Video getVideo() {
        return video;
    }

    public String getVideoUrl() {
        return (video == null) ? video.getCompleteUrl() : null;
    }

    public boolean isSuccessful() {
        return status;
    }
}
