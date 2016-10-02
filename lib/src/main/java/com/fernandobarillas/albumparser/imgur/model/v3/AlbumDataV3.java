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
import com.fernandobarillas.albumparser.media.IMedia;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class AlbumDataV3 extends BaseMediaAlbum {

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
    @SerializedName("cover")
    @Expose
    public String  cover;
    @SerializedName("cover_width")
    @Expose
    public int     coverWidth;
    @SerializedName("cover_height")
    @Expose
    public int     coverHeight;
    @SerializedName("account_url")
    @Expose
    public String  accountUrl;
    @SerializedName("account_id")
    @Expose
    public int     accountId;
    @SerializedName("privacy")
    @Expose
    public String  privacy;
    @SerializedName("layout")
    @Expose
    public String  layout;
    @SerializedName("views")
    @Expose
    public int     views;
    @SerializedName("link")
    @Expose
    public String  link;
    @SerializedName("favorite")
    @Expose
    public boolean favorite;
    @SerializedName("nsfw")
    @Expose
    public Object  nsfw;
    @SerializedName("section")
    @Expose
    public Object  section;
    @SerializedName("images_count")
    @Expose
    public int     imagesCount;
    @SerializedName("in_gallery")
    @Expose
    public boolean inGallery;
    @SerializedName("is_ad")
    @Expose
    public boolean isAd;
    @SerializedName("images")
    @Expose
    public List<ImageDataV3> images = new ArrayList<>();

    private String       mLowQuality;
    private String       mPreviewQuality;
    private List<IMedia> mMediaList;

    @Override
    public List<IMedia> getAlbumMedia() {
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
