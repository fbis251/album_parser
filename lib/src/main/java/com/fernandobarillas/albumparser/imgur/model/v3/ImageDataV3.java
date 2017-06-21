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

package com.fernandobarillas.albumparser.imgur.model.v3;

import com.fernandobarillas.albumparser.imgur.ImgurParser;
import com.fernandobarillas.albumparser.media.BaseMedia;
import com.fernandobarillas.albumparser.media.IMedia;
import com.fernandobarillas.albumparser.util.ParseUtils;
import com.squareup.moshi.Json;

import java.net.URL;

import static com.fernandobarillas.albumparser.imgur.model.Image.HUGE_THUMBNAIL;
import static com.fernandobarillas.albumparser.imgur.model.Image.MEDIUM_THUMBNAIL;
import static com.fernandobarillas.albumparser.imgur.model.Image.ORIGINAL;

public class ImageDataV3 extends BaseMedia implements IMedia {

    @Json(name = "id")
    public String  id;
    @Json(name = "title")
    public String  title;
    @Json(name = "description")
    public String  description;
    @Json(name = "datetime")
    public Integer datetime;
    @Json(name = "type")
    public String  type;
    @Json(name = "animated")
    public Boolean animated;
    @Json(name = "width")
    public Integer width;
    @Json(name = "height")
    public Integer height;
    @Json(name = "size")
    public Integer size;
    @Json(name = "views")
    public Integer views;
    @Json(name = "bandwidth")
    public Long    bandwidth;
    @Json(name = "vote")
    public String  vote;
    @Json(name = "favorite")
    public Boolean favorite;
    @Json(name = "nsfw")
    public Boolean nsfw;
    @Json(name = "section")
    public String  section;
    @Json(name = "account_url")
    public String  accountUrl;
    @Json(name = "account_id")
    public Integer accountId;
    @Json(name = "in_gallery")
    public Boolean inGallery;
    @Json(name = "gifv")
    public String  gifv;
    @Json(name = "mp4")
    public String  mp4;
    @Json(name = "mp4_size")
    public Integer mp4Size;
    @Json(name = "link")
    public String  link;
    @Json(name = "looping")
    public Boolean looping;
    @Json(name = "is_ad")
    public Boolean isAd;

    private String mPreviewQuality = MEDIUM_THUMBNAIL;
    private String mLowQuality     = HUGE_THUMBNAIL;

    @Override
    public int getByteSize(boolean highQuality) {
        // Byte size is only available for high quality
        if (highQuality) return defaultSizeIfNull(isVideo() ? mp4Size : size);
        return SIZE_UNAVAILABLE;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getHeight(boolean highQuality) {
        // Imgur only returns height for original quality
        return (highQuality) ? defaultSizeIfNull(height) : SIZE_UNAVAILABLE;
    }

    @Override
    public URL getPreviewUrl() {
        return getImageUrl(mPreviewQuality);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public URL getUrl(boolean highQuality) {
        // Imgur doesn't support low quality animations/video
        if (isVideo()) return highQuality ? getImageUrl(ORIGINAL) : null;
        return (highQuality) ? getImageUrl(ORIGINAL) : getImageUrl(mLowQuality);
    }

    @Override
    public int getWidth(boolean highQuality) {
        // Imgur only returns width for original quality
        return (highQuality) ? defaultSizeIfNull(width) : SIZE_UNAVAILABLE;
    }

    @Override
    public boolean isVideo() {
        return animated != null ? animated : false;
    }

    @Override
    public String toString() {
        return "ImageDataV3{"
                + "id='"
                + id
                + '\''
                + ", title='"
                + title
                + '\''
                + ", description="
                + description
                + ", datetime="
                + datetime
                + ", type='"
                + type
                + '\''
                + ", animated="
                + animated
                + ", width="
                + width
                + ", height="
                + height
                + ", size="
                + size
                + ", views="
                + views
                + ", bandwidth="
                + bandwidth
                + ", vote="
                + vote
                + ", favorite="
                + favorite
                + ", nsfw="
                + nsfw
                + ", section='"
                + section
                + '\''
                + ", accountUrl="
                + accountUrl
                + ", accountId="
                + accountId
                + ", inGallery="
                + inGallery
                + ", gifv='"
                + gifv
                + '\''
                + ", mp4='"
                + mp4
                + '\''
                + ", mp4Size="
                + mp4Size
                + ", link='"
                + link
                + '\''
                + ", looping="
                + looping
                + ", isAd="
                + isAd
                + '}';
    }

    protected void setLowQuality(String lowQualitySize) {
        if (lowQualitySize != null) mLowQuality = lowQualitySize;
    }

    protected void setPreviewQuality(String previewSize) {
        if (previewSize != null) mPreviewQuality = previewSize;
    }

    private URL getImageUrl(String quality) {
        String extension = ParseUtils.getExtension(link);
        if (isVideo() || EXT_GIF.equalsIgnoreCase(extension)) {
            if (!ORIGINAL.equals(quality)) {
                extension = EXT_JPG;
            } else {
                extension = EXT_MP4;
            }
        }
        String resultUrl = ImgurParser.getImageUrl(id, quality, extension);
        return ParseUtils.getUrlObject(resultUrl);
    }
}
