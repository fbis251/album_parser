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


import com.fernandobarillas.albumparser.media.BaseMediaAlbum;
import com.squareup.moshi.Json;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AlbumDataV3 extends BaseMediaAlbum<ImageDataV3> {

    @Json(name = "id")
    public String            id;
    @Json(name = "title")
    public String            title;
    @Json(name = "description")
    public String            description;
    @Json(name = "datetime")
    public Integer           datetime;
    @Json(name = "cover")
    public String            cover;
    @Json(name = "cover_width")
    public Integer           coverWidth;
    @Json(name = "cover_height")
    public Integer           coverHeight;
    @Json(name = "account_url")
    public String            accountUrl;
    @Json(name = "account_id")
    public Integer           accountId;
    @Json(name = "privacy")
    public String            privacy;
    @Json(name = "layout")
    public String            layout;
    @Json(name = "views")
    public Integer           views;
    @Json(name = "link")
    public String            link;
    @Json(name = "favorite")
    public Boolean           favorite;
    @Json(name = "nsfw")
    public Boolean           nsfw;
    @Json(name = "section")
    public String            section;
    @Json(name = "images_count")
    public Integer           imagesCount;
    @Json(name = "in_gallery")
    public Boolean           inGallery;
    @Json(name = "is_ad")
    public Boolean           isAd;
    @Json(name = "images")
    public List<ImageDataV3> images;

    private String            mLowQuality;
    private String            mPreviewQuality;
    private List<ImageDataV3> mMediaList;

    @Override
    public List<ImageDataV3> getAlbumMedia() {
        if (mMediaList == null) {
            mMediaList = new ArrayList<>();
            for (ImageDataV3 image : images) {
                if (mLowQuality != null) image.setLowQuality(mLowQuality);
                if (mPreviewQuality != null) image.setPreviewQuality(mPreviewQuality);
                mMediaList.add(image);
            }
        }
        return mMediaList;
    }

    @Override
    public int getCount() {
        return imagesCount;
    }

    @Override
    public URL getPreviewUrl() {
        if (images != null && images.size() > 0) {
            // Return the first image as the preview
            return getAlbumMedia().get(0).getPreviewUrl();
        }

        return null;
    }

    @Override
    public String toString() {
        return "AlbumDataV3{"
                + "id='"
                + id
                + '\''
                + ", title='"
                + title
                + '\''
                + ", description='"
                + description
                + '\''
                + ", datetime="
                + datetime
                + ", cover='"
                + cover
                + '\''
                + ", coverWidth="
                + coverWidth
                + ", coverHeight="
                + coverHeight
                + ", accountUrl='"
                + accountUrl
                + '\''
                + ", accountId="
                + accountId
                + ", privacy='"
                + privacy
                + '\''
                + ", layout='"
                + layout
                + '\''
                + ", views="
                + views
                + ", link='"
                + link
                + '\''
                + ", favorite="
                + favorite
                + ", nsfw="
                + nsfw
                + ", section="
                + section
                + ", imagesCount="
                + imagesCount
                + ", inGallery="
                + inGallery
                + ", isAd="
                + isAd
                + ", images="
                + images
                + '}';
    }

    protected void setLowQuality(String lowQualitySize) {
        mLowQuality = lowQualitySize;
    }

    protected void setPreviewQuality(String previewSize) {
        mPreviewQuality = previewSize;
    }
}
