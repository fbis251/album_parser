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

package com.fernandobarillas.albumparser.eroshare.model;

import com.fernandobarillas.albumparser.media.BaseMedia;
import com.fernandobarillas.albumparser.util.ParseUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.URL;

/**
 * Created by fb on 12/27/16.
 */
public class EroshareItem extends BaseMedia {

    public static final String TYPE_IMAGE = "Image";
    public static final String TYPE_VIDEO = "Video";

    private static final String HTTPS_PREFIX = "https:";

    @SerializedName("type")
    @Expose
    public String  type;
    @SerializedName("id")
    @Expose
    public String  id;
    @SerializedName("description")
    @Expose
    public String  description;
    @SerializedName("slug")
    @Expose
    public String  slug;
    @SerializedName("state")
    @Expose
    public String  state;
    @SerializedName("width")
    @Expose
    public int     width;
    @SerializedName("height")
    @Expose
    public int     height;
    @SerializedName("url_full_protocol_encoded")
    @Expose
    public String  urlFullProtocolEncoded;
    @SerializedName("url_full_protocol")
    @Expose
    public String  urlFullProtocol;
    @SerializedName("url_full")
    @Expose
    public String  urlFull;
    @SerializedName("url_thumb")
    @Expose
    public String  urlThumb;
    @SerializedName("url_orig")
    @Expose
    public String  urlOrig;
    @SerializedName("position")
    @Expose
    public int     position;
    @SerializedName("is_portrait")
    @Expose
    public boolean isPortrait;
    @SerializedName("conversion_progress")
    @Expose
    public int     conversionProgress;
    @SerializedName("video_duration")
    @Expose
    public int     videoDuration;
    @SerializedName("url_mp4")
    @Expose
    public String  urlMp4;
    @SerializedName("url_mp4_lowres")
    @Expose
    public String  urlMp4Lowres;
    @SerializedName("lowres")
    @Expose
    public boolean lowres;

    private URL previewUrl;

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getDuration() {
        return isVideo() ? videoDuration : DURATION_UNAVAILABLE;
    }

    @Override
    public int getHeight(boolean highQuality) {
        // Eroshare doesn't support low quality height
        return highQuality ? height : SIZE_UNAVAILABLE;
    }

    @Override
    public URL getPreviewUrl() {
        if (urlThumb == null) return null;
        if (previewUrl == null) {
            previewUrl = ParseUtils.getUrlObject(HTTPS_PREFIX + urlThumb.replace("_play", ""));
        }
        return previewUrl;
    }

    @Override
    public URL getUrl(boolean highQuality) {
        String resultUrl;
        if (isVideo()) {
            resultUrl = highQuality ? urlMp4 : urlMp4Lowres;
        } else {
            resultUrl = highQuality ? urlOrig : urlFull;
            resultUrl = HTTPS_PREFIX + resultUrl; // Image URLs don't have a protocol prefix
        }

        return ParseUtils.getUrlObject(resultUrl);
    }

    @Override
    public int getWidth(boolean highQuality) {
        // Eroshare doesn't support low quality width
        return highQuality ? width : SIZE_UNAVAILABLE;
    }

    @Override
    public boolean isVideo() {
        return TYPE_VIDEO.equals(type);
    }
}
