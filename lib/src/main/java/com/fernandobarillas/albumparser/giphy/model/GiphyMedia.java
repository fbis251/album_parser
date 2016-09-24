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

package com.fernandobarillas.albumparser.giphy.model;

import com.fernandobarillas.albumparser.giphy.api.GiphyApi;
import com.fernandobarillas.albumparser.media.BaseMedia;
import com.fernandobarillas.albumparser.util.ParseUtils;

import java.net.URL;

/**
 * Model for Giphy media URLs
 */
public class GiphyMedia extends BaseMedia {
    private URL    mHighQualityUrl;
    private URL    mPreviewUrl;
    private String mHash;

    public GiphyMedia(String hash) {
        mHash = hash;
    }

    @Override
    public URL getPreviewUrl() {
        if (mPreviewUrl != null) return mPreviewUrl;
        mPreviewUrl = getMediaUrl(true);
        return mPreviewUrl;
    }

    @Override
    public URL getUrl(boolean highQuality) {
        if (mHash == null || !highQuality) return null; // There's no guaranteed URL for low quality
        if (mHighQualityUrl != null) return mHighQualityUrl;
        mHighQualityUrl = getMediaUrl(false);
        return mHighQualityUrl;
    }

    @Override
    public boolean isVideo() {
        return true;
    }

    /**
     * @param isPreview True to get the preview URL, false to get the MP4 URL
     * @return A URL to a preview or MP4 file
     */
    private URL getMediaUrl(boolean isPreview) {
        String filename = "giphy";
        String ext = EXT_MP4;
        if (isPreview) {
            filename = "giphy_s";
            ext = EXT_GIF;
        }
        // https://media.giphy.com/media/{hash}/giphy_s.gif -- preview
        // https://media.giphy.com/media/{hash}/giphy.mp4   -- mp4
        String resultUrl =
                String.format("%s/media/%s/%s.%s", GiphyApi.MEDIA_URL, mHash, filename, ext);
        return ParseUtils.getUrlObject(resultUrl);
    }
}
