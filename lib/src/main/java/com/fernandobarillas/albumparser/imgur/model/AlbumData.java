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
import com.fernandobarillas.albumparser.media.IMedia;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

/**
 * Created by fb on 5/3/16.
 */

@Generated("org.jsonschema2pojo")
public class AlbumData extends BaseMediaAlbum {

    @SerializedName("count")
    @Expose
    public int count;
    @SerializedName("images")
    @Expose
    public List<Image> images = new ArrayList<>();

    private List<IMedia> mMediaList;

    private String mLowQuality;
    private String mPreviewQuality;

    @Override
    public List<IMedia> getAlbumMedia() {
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

    protected void setLowQualitySize(String lowQualitySize) {
        mLowQuality = lowQualitySize;
    }

    protected void setPreviewSize(String previewSize) {
        mPreviewQuality = previewSize;
    }
}
