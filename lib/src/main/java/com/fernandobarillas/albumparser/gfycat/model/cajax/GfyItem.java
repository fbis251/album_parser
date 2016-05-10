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

package com.fernandobarillas.albumparser.gfycat.model.cajax;

import com.fernandobarillas.albumparser.util.ParseUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fb on 5/9/16.
 */
@Generated("org.jsonschema2pojo")
public class GfyItem {

    @SerializedName("gfyId")
    @Expose
    public String gfyId;
    @SerializedName("gfyName")
    @Expose
    public String gfyName;
    @SerializedName("gfyNumber")
    @Expose
    public String gfyNumber;
    @SerializedName("userName")
    @Expose
    public String userName;
    @SerializedName("width")
    @Expose
    public int width;
    @SerializedName("height")
    @Expose
    public int height;
    @SerializedName("frameRate")
    @Expose
    public int frameRate;
    @SerializedName("numFrames")
    @Expose
    public int numFrames;
    @SerializedName("mp4Url")
    @Expose
    public String mp4Url;
    @SerializedName("webmUrl")
    @Expose
    public String webmUrl;
    @SerializedName("webpUrl")
    @Expose
    public String webpUrl;
    @SerializedName("mobileUrl")
    @Expose
    public String mobileUrl;
    @SerializedName("mobilePosterUrl")
    @Expose
    public String mobilePosterUrl;
    @SerializedName("posterUrl")
    @Expose
    public String posterUrl;
    @SerializedName("thumb360Url")
    @Expose
    public String thumb360Url;
    @SerializedName("thumb360PosterUrl")
    @Expose
    public String thumb360PosterUrl;
    @SerializedName("thumb100PosterUrl")
    @Expose
    public String thumb100PosterUrl;
    @SerializedName("max5mbGif")
    @Expose
    public String max5mbGif;
    @SerializedName("max2mbGif")
    @Expose
    public String max2mbGif;
    @SerializedName("mjpgUrl")
    @Expose
    public String mjpgUrl;
    @SerializedName("gifUrl")
    @Expose
    public String gifUrl;
    @SerializedName("gifSize")
    @Expose
    public int gifSize;
    @SerializedName("mp4Size")
    @Expose
    public int mp4Size;
    @SerializedName("webmSize")
    @Expose
    public int webmSize;
    @SerializedName("createDate")
    @Expose
    public int createDate;
    @SerializedName("views")
    @Expose
    public int views;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("extraLemmas")
    @Expose
    public List<String> extraLemmas = new ArrayList<String>();
    @SerializedName("md5")
    @Expose
    public String md5;
    @SerializedName("tags")
    @Expose
    public List<String> tags = new ArrayList<String>();
    @SerializedName("nsfw")
    @Expose
    public String nsfw;
    @SerializedName("sar")
    @Expose
    public String sar;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("source")
    @Expose
    public String source;
    @SerializedName("dynamo")
    @Expose
    public Object dynamo;
    @SerializedName("subreddit")
    @Expose
    public String subreddit;
    @SerializedName("redditId")
    @Expose
    public String redditId;
    @SerializedName("redditIdText")
    @Expose
    public String redditIdText;
    @SerializedName("likes")
    @Expose
    public int likes;
    @SerializedName("dislikes")
    @Expose
    public int dislikes;
    @SerializedName("published")
    @Expose
    public String published;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("copyrightClaimaint")
    @Expose
    public String copyrightClaimaint;
    @SerializedName("languageText")
    @Expose
    public String languageText;

    public String getMobileUrl() {
        return mobileUrl;
    }

    public String getMp4Url() {
        return mp4Url;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMp4Size() {
        return mp4Size;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return gfyName + " " + ParseUtils.getSizeInMb(gifSize) + "MB -> " + ParseUtils.getSizeInMb(mp4Size) + "MB " + mp4Url;
    }
}
