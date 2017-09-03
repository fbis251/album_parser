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

package com.fernandobarillas.albumparser.gfycat.model;

import com.fernandobarillas.albumparser.media.BaseMedia;
import com.fernandobarillas.albumparser.util.ParseUtils;
import com.squareup.moshi.Json;

import java.net.URL;
import java.util.List;

public class GfyItem extends BaseMedia {

    @Json(name = "gfyId")
    public String       gfyId;
    @Json(name = "gfyName")
    public String       gfyName;
    @Json(name = "gfyNumber")
    public String       gfyNumber;
    @Json(name = "userName")
    public String       userName;
    @Json(name = "width")
    public Integer      width;
    @Json(name = "height")
    public Integer      height;
    @Json(name = "frameRate")
    public Double       frameRate;
    @Json(name = "numFrames")
    public Integer      numFrames;
    @Json(name = "mp4Url")
    public String       mp4Url;
    @Json(name = "webmUrl")
    public String       webmUrl;
    @Json(name = "webpUrl")
    public String       webpUrl;
    @Json(name = "mobileUrl")
    public String       mobileUrl;
    @Json(name = "mobilePosterUrl")
    public String       mobilePosterUrl;
    @Json(name = "posterUrl")
    public String       posterUrl;
    @Json(name = "thumb360Url")
    public String       thumb360Url;
    @Json(name = "thumb360PosterUrl")
    public String       thumb360PosterUrl;
    @Json(name = "thumb100PosterUrl")
    public String       thumb100PosterUrl;
    @Json(name = "max5mbGif")
    public String       max5mbGif;
    @Json(name = "max2mbGif")
    public String       max2mbGif;
    @Json(name = "mjpgUrl")
    public String       mjpgUrl;
    @Json(name = "gifUrl")
    public String       gifUrl;
    @Json(name = "gifSize")
    public Integer      gifSize;
    @Json(name = "mp4Size")
    public Integer      mp4Size;
    @Json(name = "webmSize")
    public Integer      webmSize;
    @Json(name = "createDate")
    public Integer      createDate;
    @Json(name = "views")
    public Integer      views;
    @Json(name = "title")
    public String       title;
    @Json(name = "extraLemmas")
    public List<String> extraLemmas;
    @Json(name = "md5")
    public String       md5;
    @Json(name = "tags")
    public List<String> tags;
    @Json(name = "nsfw")
    public String       nsfw;
    @Json(name = "sar")
    public String       sar;
    @Json(name = "url")
    public String       url;
    @Json(name = "source")
    public String       source;
    @Json(name = "dynamo")
    public Object       dynamo;
    @Json(name = "subreddit")
    public String       subreddit;
    @Json(name = "redditId")
    public String       redditId;
    @Json(name = "redditIdText")
    public String       redditIdText;
    @Json(name = "likes")
    public Integer      likes;
    @Json(name = "dislikes")
    public Integer      dislikes;
    @Json(name = "published")
    public String       published;
    @Json(name = "description")
    public String       description;
    @Json(name = "copyrightClaimaint")
    public String       copyrightClaimaint;
    @Json(name = "languageText")
    public String       languageText;

    @Override
    public int getByteSize(boolean highQuality) {
        // Gfycat mp4Size only available for high quality
        return (highQuality) ? defaultSizeIfNull(mp4Size) : SIZE_UNAVAILABLE;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getDuration() {
        if (frameRate == null || numFrames == null) return DURATION_UNAVAILABLE;
        if (frameRate > 0) {
            return (numFrames * 1.0) / frameRate;
        }
        return DURATION_UNAVAILABLE;
    }

    @Override
    public int getHeight(boolean highQuality) {
        // Gfycat height only available for high quality
        return (highQuality) ? defaultSizeIfNull(height) : SIZE_UNAVAILABLE;
    }

    @Override
    public URL getPreviewUrl() {
        if (mobilePosterUrl != null) return ParseUtils.getUrlObject(mobilePosterUrl);
        return ParseUtils.getUrlObject(posterUrl);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public URL getUrl(boolean highQuality) {
        String resultUrl = mp4Url;
        if (!highQuality && mobileUrl != null) {
            resultUrl = mobileUrl;
        }
        return ParseUtils.getUrlObject(resultUrl);
    }

    @Override
    public int getWidth(boolean highQuality) {
        // Gfycat width only available for high quality
        return (highQuality) ? defaultSizeIfNull(width) : SIZE_UNAVAILABLE;
    }

    @Override
    public boolean isVideo() {
        return true;
    }
}
