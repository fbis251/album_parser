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

    @SerializedName("hash")
    @Expose
    private String  hash;
    @SerializedName("title")
    @Expose
    private String  title;
    @SerializedName("description")
    @Expose
    private String  description;
    @SerializedName("width")
    @Expose
    private int     width;
    @SerializedName("height")
    @Expose
    private int     height;
    @SerializedName("size")
    @Expose
    private int     size;
    @SerializedName("ext")
    @Expose
    private String  ext;
    @SerializedName("animated")
    @Expose
    private boolean animated;
    @SerializedName("prefer_video")
    @Expose
    private boolean preferVideo;
    @SerializedName("looping")
    @Expose
    private boolean looping;
    @SerializedName("datetime")
    @Expose
    private String  datetime;

    /**
     * @return The hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return The width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return The height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return The size
     */
    public int getSize() {
        return size;
    }

    /**
     * @return The ext
     */
    public String getExt() {
        return ext;
    }

    /**
     * @return The animated
     */
    public boolean isAnimated() {
        return animated;
    }

    /**
     * @return The preferVideo
     */
    public boolean isPreferVideo() {
        return preferVideo;
    }

    /**
     * @return The looping
     */
    public boolean isLooping() {
        return looping;
    }

    /**
     * @return The datetime
     */
    public String getDatetime() {
        return datetime;
    }

    public String getImageUrl() {
        return ImgurApi.IMAGE_URL + hash + ext;
    }
}
