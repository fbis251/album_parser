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
import com.squareup.moshi.Json;

import java.net.URL;

public class DeviantartResponse extends BaseMedia {

    @Json(name = "version")
    public String  version;
    @Json(name = "type")
    public String  type;
    @Json(name = "title")
    public String  title;
    @Json(name = "category")
    public String  category;
    @Json(name = "url")
    public String  url;
    @Json(name = "author_name")
    public String  authorName;
    @Json(name = "author_url")
    public String  authorUrl;
    @Json(name = "provider_name")
    public String  providerName;
    @Json(name = "provider_url")
    public String  providerUrl;
    @Json(name = "safety")
    public String  safety;
    @Json(name = "pubdate")
    public String  pubdate;
    @Json(name = "tags")
    public String  tags;
    @Json(name = "width")
    public Integer width;
    @Json(name = "height")
    public Integer height;
    @Json(name = "imagetype")
    public String  imagetype;
    @Json(name = "thumbnail_url")
    public String  thumbnailUrl;
    @Json(name = "thumbnail_width")
    public Integer thumbnailWidth;
    @Json(name = "thumbnail_height")
    public Integer thumbnailHeight;
    @Json(name = "thumbnail_url_150")
    public String  thumbnailUrl150;
    @Json(name = "thumbnail_url_200h")
    public String  thumbnailUrl200h;
    @Json(name = "thumbnail_width_200h")
    public Integer thumbnailWidth200h;
    @Json(name = "thumbnail_height_200h")
    public Integer thumbnailHeight200h;

    @Override
    public int getHeight(boolean highQuality) {
        return defaultSizeIfNull((highQuality) ? height : thumbnailHeight);
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
        return defaultSizeIfNull((highQuality) ? width : thumbnailWidth);
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
