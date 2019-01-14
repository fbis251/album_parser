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

public class GalleryDataV3 extends BaseMediaAlbum<ImageDataV3> {

    @Json(name = "id")
    public String            id;
    @Json(name = "title")
    public String            title;
    @Json(name = "description")
    public String            description;
    @Json(name = "is_album")
    public Boolean isAlbum;
    @Json(name = "images_count")
    public Integer           imagesCount;
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
                + ", imagesCount="
                + imagesCount
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
