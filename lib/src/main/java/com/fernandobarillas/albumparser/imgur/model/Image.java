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

package com.fernandobarillas.albumparser.imgur.model;

import com.fernandobarillas.albumparser.imgur.api.ImgurApi;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * Created by fb on 5/3/16.
 */

@Generated("org.jsonschema2pojo")
public class Image {
    // https://api.imgur.com/models/image
    public static final String ORIGINAL         = "";
    public static final String SMALL_SQUARE     = "s";
    public static final String BIG_SQUARE       = "b";
    public static final String SMALL_THUMBNAIL  = "t";
    public static final String MEDIUM_THUMBNAIL = "m";
    public static final String LARGE_THUMBNAIL  = "l";
    public static final String HUGE_THUMBNAIL   = "h";

    @SerializedName("hash")
    @Expose
    public String  hash;
    @SerializedName("title")
    @Expose
    public String  title;
    @SerializedName("description")
    @Expose
    public String  description;
    @SerializedName("width")
    @Expose
    public int     width;
    @SerializedName("height")
    @Expose
    public int     height;
    @SerializedName("size")
    @Expose
    public int     size;
    @SerializedName("ext")
    @Expose
    public String  ext;
    @SerializedName("animated")
    @Expose
    public boolean animated;
    @SerializedName("prefer_video")
    @Expose
    public boolean preferVideo;
    @SerializedName("looping")
    @Expose
    public boolean looping;
    @SerializedName("datetime")
    @Expose
    public String  datetime;

    /**
     * @return The datetime
     */
    public String getDatetime() {
        return datetime;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return The ext
     */
    public String getExt() {
        return ext;
    }

    /**
     * @return The hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * @return The height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the url to the image with either the original quality or a lower quality huge thumbnail
     *
     * @param originalQuality True to get the original quality link, false for huge thumbnail quality
     * @return The URL to the image
     */
    public String getImageUrl(boolean originalQuality) {
        String quality = (originalQuality) ? ORIGINAL : HUGE_THUMBNAIL;
        return ImgurApi.IMAGE_URL + hash + quality + ext;
    }

    /**
     * Gets the url to the image with the passed in quality.
     *
     * @param quality The quality to use in the returned URL, for example to return the URL for the small thumbnail
     *                image you can pass in {@link #SMALL_THUMBNAIL}
     * @return The URL to the image with the selected quality
     */
    public String getImageUrl(String quality) {
        return ImgurApi.IMAGE_URL + hash + quality + ext;
    }

    /**
     * @return The size
     */
    public int getSize() {
        return size;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return The width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return The animated
     */
    public boolean isAnimated() {
        return animated;
    }

    /**
     * @return The looping
     */
    public boolean isLooping() {
        return looping;
    }

    /**
     * @return The preferVideo
     */
    public boolean isPreferVideo() {
        return preferVideo;
    }
}
