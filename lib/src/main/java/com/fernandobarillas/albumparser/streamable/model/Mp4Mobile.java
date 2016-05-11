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

import javax.annotation.Generated;

import static com.fernandobarillas.albumparser.streamable.model.StreamableResponse.PROTOCOL_HTTPS;

/**
 * Created by fb on 5/10/16.
 */
@Generated("org.jsonschema2pojo")
public class Mp4Mobile {

    @SerializedName("status")
    @Expose
    public int    status;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("framerate")
    @Expose
    public int    framerate;
    @SerializedName("height")
    @Expose
    public int    height;
    @SerializedName("width")
    @Expose
    public int    width;
    @SerializedName("bitrate")
    @Expose
    public int    bitrate;
    @SerializedName("size")
    @Expose
    public int    size;

    @Override
    public String toString() {
        return "Mp4Mobile{" +
                "status=" + status +
                ", url='" + url + '\'' +
                ", framerate=" + framerate +
                ", height=" + height +
                ", width=" + width +
                ", bitrate=" + bitrate +
                ", size=" + size +
                '}';
    }

    public int getBitrate() {
        return bitrate;
    }

    public int getFramerate() {
        return framerate;
    }

    public int getHeight() {
        return height;
    }

    public int getSize() {
        return size;
    }

    public int getStatus() {
        return status;
    }

    public String getUrl() {
        return PROTOCOL_HTTPS + url;

    }

    public int getWidth() {
        return width;
    }
}
