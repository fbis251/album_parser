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

package com.fernandobarillas.albumparser.imgur.model;

import com.fernandobarillas.albumparser.media.BaseMediaAlbum;
import com.squareup.moshi.Json;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AlbumData extends BaseMediaAlbum<Image> {

    @Json(name = "count")
    public Integer     count;
    @Json(name = "images")
    public List<Image> images;

    private List<Image> mMediaList;

    private String mLowQuality;
    private String mPreviewQuality;

    @Override
    public List<Image> getAlbumMedia() {
        if (mMediaList == null) {
            mMediaList = new ArrayList<>();
            for (Image image : images) {
                if (mLowQuality != null) image.setLowQuality(mLowQuality);
                if (mPreviewQuality != null) image.setPreviewQuality(mPreviewQuality);
                mMediaList.add(image);
            }
        }
        return mMediaList;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public URL getPreviewUrl() {
        if (images != null && images.size() > 0) {
            // Return the first image as the preview
            return getAlbumMedia().get(0).getPreviewUrl();
        }

        return null;
    }

    protected void setLowQuality(String lowQualitySize) {
        mLowQuality = lowQualitySize;
    }

    protected void setPreviewQuality(String previewSize) {
        mPreviewQuality = previewSize;
    }
}
