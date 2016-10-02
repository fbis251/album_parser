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
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.URL;

import javax.annotation.Generated;

import static com.fernandobarillas.albumparser.imgur.model.Image.HUGE_THUMBNAIL;
import static com.fernandobarillas.albumparser.imgur.model.Image.MEDIUM_THUMBNAIL;
import static com.fernandobarillas.albumparser.imgur.model.Image.ORIGINAL;

@Generated("org.jsonschema2pojo")
public class ImageDataV3 extends BaseMedia implements IMedia {

    @SerializedName("id")
    @Expose
    public String  id;
    @SerializedName("title")
    @Expose
    public String  title;
    @SerializedName("description")
    @Expose
    public String  description;
    @SerializedName("datetime")
    @Expose
    public int     datetime;
    @SerializedName("type")
    @Expose
    public String  type;
    @SerializedName("animated")
    @Expose
    public boolean animated;
    @SerializedName("width")
    @Expose
    public int     width;
    @SerializedName("height")
    @Expose
    public int     height;
    @SerializedName("size")
    @Expose
    public int     size;
    @SerializedName("views")
    @Expose
    public int     views;
    @SerializedName("bandwidth")
    @Expose
    public long    bandwidth;
    @SerializedName("vote")
    @Expose
    public String  vote;
    @SerializedName("favorite")
    @Expose
    public boolean favorite;
    @SerializedName("nsfw")
    @Expose
    public boolean nsfw;
    @SerializedName("section")
    @Expose
    public String  section;
    @SerializedName("account_url")
    @Expose
    public String  accountUrl;
    @SerializedName("account_id")
    @Expose
    public int     accountId;
    @SerializedName("in_gallery")
    @Expose
    public boolean inGallery;
    @SerializedName("gifv")
    @Expose
    public String  gifv;
    @SerializedName("mp4")
    @Expose
    public String  mp4;
    @SerializedName("mp4_size")
    @Expose
    public int     mp4Size;
    @SerializedName("link")
    @Expose
    public String  link;
    @SerializedName("looping")
    @Expose
    public boolean looping;
    @SerializedName("is_ad")
    @Expose
    public boolean isAd;

    private String mPreviewQuality = MEDIUM_THUMBNAIL;
    private String mLowQuality     = HUGE_THUMBNAIL;

    @Override
    public int getByteSize(boolean highQuality) {
        if (highQuality) {
            if (animated) return mp4Size;
            return size;
        }

        return SIZE_UNAVAILABLE;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getHeight(boolean highQuality) {
        // Imgur only returns height for original quality
        return (highQuality) ? height : SIZE_UNAVAILABLE;
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
        if (animated) return highQuality ? getImageUrl(ORIGINAL) : null;
        return (highQuality) ? getImageUrl(ORIGINAL) : getImageUrl(mLowQuality);
    }

    @Override
    public int getWidth(boolean highQuality) {
        // Imgur only returns width for original quality
        return (highQuality) ? width : SIZE_UNAVAILABLE;
    }

    @Override
    public boolean isVideo() {
        return animated;
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
        if (animated) {
            if (quality != ORIGINAL) {
                extension = EXT_JPG;
            } else {
                extension = EXT_MP4;
            }
        }
        String resultUrl = ImgurParser.getImageUrl(id, quality, extension);
        return ParseUtils.getUrlObject(resultUrl);
    }
}
