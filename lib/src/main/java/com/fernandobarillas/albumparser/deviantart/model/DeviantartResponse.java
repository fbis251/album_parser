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

package com.fernandobarillas.albumparser.deviantart.model;

/**
 * Created by fb on 5/26/16.
 */

import com.fernandobarillas.albumparser.media.BaseMedia;
import com.fernandobarillas.albumparser.util.ParseUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.URL;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class DeviantartResponse extends BaseMedia {

    @SerializedName("version")
    @Expose
    public String version;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("category")
    @Expose
    public String category;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("author_name")
    @Expose
    public String authorName;
    @SerializedName("author_url")
    @Expose
    public String authorUrl;
    @SerializedName("provider_name")
    @Expose
    public String providerName;
    @SerializedName("provider_url")
    @Expose
    public String providerUrl;
    @SerializedName("safety")
    @Expose
    public String safety;
    @SerializedName("pubdate")
    @Expose
    public String pubdate;
    @SerializedName("tags")
    @Expose
    public String tags;
    @SerializedName("width")
    @Expose
    public int    width;
    @SerializedName("height")
    @Expose
    public int    height;
    @SerializedName("imagetype")
    @Expose
    public String imagetype;
    @SerializedName("thumbnail_url")
    @Expose
    public String thumbnailUrl;
    @SerializedName("thumbnail_width")
    @Expose
    public int    thumbnailWidth;
    @SerializedName("thumbnail_height")
    @Expose
    public int    thumbnailHeight;
    @SerializedName("thumbnail_url_150")
    @Expose
    public String thumbnailUrl150;
    @SerializedName("thumbnail_url_200h")
    @Expose
    public String thumbnailUrl200h;
    @SerializedName("thumbnail_width_200h")
    @Expose
    public int    thumbnailWidth200h;
    @SerializedName("thumbnail_height_200h")
    @Expose
    public int    thumbnailHeight200h;

    @Override
    public int getHeight(boolean highQuality) {
        return (highQuality) ? height : thumbnailHeight;
    }

    @Override
    public URL getPreviewUrl() {
        return ParseUtils.getUrlObject(thumbnailUrl);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public URL getUrl(boolean highQuality) {
        if (!highQuality && getPreviewUrl() != null) {
            return getPreviewUrl();
        }
        return ParseUtils.getUrlObject(url);
    }

    @Override
    public int getWidth(boolean highQuality) {
        return (highQuality) ? width : thumbnailWidth;
    }

    @Override
    public boolean isGif() {
        return ParseUtils.isGifExtension(getUrl(true));
    }

    @Override
    public boolean isVideo() {
        return ParseUtils.isVideoExtension(getUrl(true));
    }
}
