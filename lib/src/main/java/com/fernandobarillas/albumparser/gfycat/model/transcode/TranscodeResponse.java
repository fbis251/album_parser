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

package com.fernandobarillas.albumparser.gfycat.model.transcode;

import com.fernandobarillas.albumparser.util.ParseUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

/**
 * Created by fb on 5/9/16.
 */
@Generated("org.jsonschema2pojo")
public class TranscodeResponse {

    @SerializedName("gfyId")
    @Expose
    public String gfyId;
    @SerializedName("gfyName")
    @Expose
    public String gfyName;
    @SerializedName("gfyNumber")
    @Expose
    public int    gfyNumber;
    @SerializedName("userName")
    @Expose
    public String userName;
    @SerializedName("width")
    @Expose
    public int    width;
    @SerializedName("height")
    @Expose
    public int    height;
    @SerializedName("frameRate")
    @Expose
    public int    frameRate;
    @SerializedName("numFrames")
    @Expose
    public int    numFrames;
    @SerializedName("mp4Url")
    @Expose
    public String mp4Url;
    @SerializedName("webmUrl")
    @Expose
    public String webmUrl;
    @SerializedName("gifUrl")
    @Expose
    public String gifUrl;
    @SerializedName("gifSize")
    @Expose
    public int    gifSize;
    @SerializedName("mp4Size")
    @Expose
    public int    mp4Size;
    @SerializedName("webmSize")
    @Expose
    public int    webmSize;
    @SerializedName("createDate")
    @Expose
    public int    createDate;
    @SerializedName("views")
    @Expose
    public int    views;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("extraLemmas")
    @Expose
    public List<String> extraLemmas = new ArrayList<String>();
    @SerializedName("md5")
    @Expose
    public String md5;
    @SerializedName("sar")
    @Expose
    public int    sar;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("source")
    @Expose
    public int    source;
    @SerializedName("subreddit")
    @Expose
    public String subreddit;
    @SerializedName("redditId")
    @Expose
    public String redditId;
    @SerializedName("redditIdText")
    @Expose
    public String redditIdText;
    @SerializedName("uploadGifName")
    @Expose
    public String uploadGifName;
    @SerializedName("avgColor")
    @Expose
    public String avgColor;
    @SerializedName("likes")
    @Expose
    public int    likes;
    @SerializedName("dislikes")
    @Expose
    public int    dislikes;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("extraLemmaText")
    @Expose
    public String extraLemmaText;
    @SerializedName("urlMd5")
    @Expose
    public String urlMd5;
    @SerializedName("task")
    @Expose
    public String task;
    @SerializedName("gfyname")
    @Expose
    public String gfyname;
    @SerializedName("gfysize")
    @Expose
    public int    gfysize;

    @Override
    public String toString() {
        return gfyName + " " + ParseUtils.getSizeInMbString(gifSize) + "MB -> " + ParseUtils.getSizeInMbString(gfysize) + "MB " + mp4Url;
    }

    public String getMp4Url() {
        return mp4Url;
    }

    public String getUrl() {
        return url;
    }
}
