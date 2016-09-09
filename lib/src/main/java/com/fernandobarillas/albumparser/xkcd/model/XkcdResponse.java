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

package com.fernandobarillas.albumparser.xkcd.model;

import com.fernandobarillas.albumparser.media.BaseMedia;
import com.fernandobarillas.albumparser.util.ParseUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.URL;

import javax.annotation.Generated;

/**
 * XKCD API Response Java Object Model
 */
@Generated("org.jsonschema2pojo")
public class XkcdResponse extends BaseMedia {
    @SerializedName("month")
    @Expose
    public String month;
    @SerializedName("num")
    @Expose
    public int    num;
    @SerializedName("link")
    @Expose
    public String link;
    @SerializedName("year")
    @Expose
    public String year;
    @SerializedName("news")
    @Expose
    public String news;
    @SerializedName("safe_title")
    @Expose
    public String safeTitle;
    @SerializedName("transcript")
    @Expose
    public String transcript;
    @SerializedName("alt")
    @Expose
    public String alt;
    @SerializedName("img")
    @Expose
    public String img;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("day")
    @Expose
    public String day;

    @Override
    public String getDescription() {
        return alt;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public URL getUrl(boolean highQuality) {
        if (img == null) return null;
        // XKCD doesn't support high/low quality URLs
        String imageUrl = img.replace("http://", "https://"); // Always return https URLs
        return ParseUtils.getUrlObject(imageUrl);
    }
}