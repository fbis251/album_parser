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

import com.fernandobarillas.albumparser.media.BaseMedia;
import com.fernandobarillas.albumparser.util.ParseUtils;
import com.squareup.moshi.Json;

import java.net.URL;

public class Video extends BaseMedia {

    @Json(name = "video_id")
    public String  videoId;
    @Json(name = "url")
    public String  url;
    @Json(name = "full_url")
    public String  fullUrl;
    @Json(name = "embed_url")
    public String  embedUrl;
    @Json(name = "user_id")
    public String  userId;
    @Json(name = "complete")
    public String  complete;
    @Json(name = "complete_url")
    public String  completeUrl;
    @Json(name = "state")
    public String  state;
    @Json(name = "title")
    public String  title;
    @Json(name = "description")
    public String  description;
    @Json(name = "duration")
    public Double  duration;
    @Json(name = "height")
    public Integer height;
    @Json(name = "width")
    public Integer width;
    @Json(name = "date_created")
    public String  dateCreated;
    @Json(name = "date_stored")
    public String  dateStored;
    @Json(name = "date_completed")
    public String  dateCompleted;
    @Json(name = "comment_count")
    public Integer commentCount;
    @Json(name = "view_count")
    public Integer viewCount;
    @Json(name = "share_count")
    public Integer shareCount;
    @Json(name = "version")
    public Integer version;
    @Json(name = "nsfw")
    public Boolean nsfw;
    @Json(name = "thumbnail")
    public String  thumbnail;
    @Json(name = "thumbnail_url")
    public String  thumbnailUrl;
    @Json(name = "thumbnail_gif")
    public String  thumbnailGif;
    @Json(name = "thumbnail_gif_url")
    public String  thumbnailGifUrl;
    @Json(name = "storyboard")
    public String  storyboard;
    @Json(name = "score")
    public Integer score;
    @Json(name = "likes_count")
    public Integer likesCount;
    @Json(name = "channel_id")
    public String  channelId;
    @Json(name = "source")
    public String  source;
    @Json(name = "private")
    public Boolean _private;
    @Json(name = "latitude")
    public Double  latitude;
    @Json(name = "longitude")
    public Double  longitude;
    @Json(name = "place_id")
    public String  placeId;
    @Json(name = "place_name")
    public String  placeName;
    @Json(name = "colors")
    public String  colors;
    @Json(name = "reddit_link")
    public String  redditLink;

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getDuration() {
        return defaultDurationIfNull(duration);
    }

    @Override
    public int getHeight(boolean highQuality) {
        return highQuality ? defaultSizeIfNull(height) : SIZE_UNAVAILABLE;
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
        // TODO: Get low quality URL. 480p? There are variants available under the "formats" obj
        return highQuality ? ParseUtils.getUrlObject(completeUrl) : null;
    }

    @Override
    public int getWidth(boolean highQuality) {
        return highQuality ? defaultSizeIfNull(width) : SIZE_UNAVAILABLE;
    }

    @Override
    public boolean isVideo() {
        return true;
    }

    @Override
    public String toString() {
        return "Video{"
                + "url='"
                + url
                + '\''
                + ", completeUrl='"
                + completeUrl
                + '\''
                + ", height="
                + height
                + ", width="
                + width
                + ", duration="
                + duration
                + ", thumbnailUrl='"
                + thumbnailUrl
                + '\''
                + '}';
    }
}
