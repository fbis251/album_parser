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
public class Video {

    @SerializedName("video_id")
    @Expose
    public String  videoId;
    @SerializedName("url")
    @Expose
    public String  url;
    @SerializedName("full_url")
    @Expose
    public String  fullUrl;
    @SerializedName("embed_url")
    @Expose
    public String  embedUrl;
    @SerializedName("user_id")
    @Expose
    public Object  userId;
    @SerializedName("complete")
    @Expose
    public String  complete;
    @SerializedName("complete_url")
    @Expose
    public String  completeUrl;
    @SerializedName("state")
    @Expose
    public String  state;
    @SerializedName("title")
    @Expose
    public String  title;
    @SerializedName("description")
    @Expose
    public Object  description;
    @SerializedName("duration")
    @Expose
    public float   duration;
    @SerializedName("height")
    @Expose
    public int     height;
    @SerializedName("width")
    @Expose
    public int     width;
    @SerializedName("date_created")
    @Expose
    public String  dateCreated;
    @SerializedName("date_stored")
    @Expose
    public String  dateStored;
    @SerializedName("date_completed")
    @Expose
    public String  dateCompleted;
    @SerializedName("comment_count")
    @Expose
    public int     commentCount;
    @SerializedName("view_count")
    @Expose
    public int     viewCount;
    @SerializedName("share_count")
    @Expose
    public int     shareCount;
    @SerializedName("version")
    @Expose
    public int     version;
    @SerializedName("nsfw")
    @Expose
    public boolean nsfw;
    @SerializedName("thumbnail")
    @Expose
    public String  thumbnail;
    @SerializedName("thumbnail_url")
    @Expose
    public String  thumbnailUrl;
    @SerializedName("thumbnail_gif")
    @Expose
    public String  thumbnailGif;
    @SerializedName("thumbnail_gif_url")
    @Expose
    public String  thumbnailGifUrl;
    @SerializedName("storyboard")
    @Expose
    public String  storyboard;
    @SerializedName("score")
    @Expose
    public int     score;
    @SerializedName("likes_count")
    @Expose
    public int     likesCount;
    @SerializedName("channel_id")
    @Expose
    public String  channelId;
    @SerializedName("source")
    @Expose
    public String  source;
    @SerializedName("private")
    @Expose
    public boolean _private;
    @SerializedName("latitude")
    @Expose
    public double  latitude;
    @SerializedName("longitude")
    @Expose
    public double  longitude;
    @SerializedName("place_id")
    @Expose
    public String  placeId;
    @SerializedName("place_name")
    @Expose
    public String  placeName;
    @SerializedName("colors")
    @Expose
    public String  colors;
    @SerializedName("reddit_link")
    @Expose
    public String  redditLink;

    @Override
    public String toString() {
        return "Video{" +
                "url='" + url + '\'' +
                ", completeUrl='" + completeUrl + '\'' +
                ", height=" + height +
                ", width=" + width +
                ", duration=" + duration +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                '}';
    }

    public String getCompleteUrl() {
        return completeUrl;
    }

    public float getDuration() {
        return duration;
    }

    public int getHeight() {
        return height;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public int getWidth() {
        return width;
    }
}
