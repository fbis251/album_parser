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

package com.fernandobarillas.albumparser.xkcd.model;

import com.fernandobarillas.albumparser.media.BaseMedia;
import com.fernandobarillas.albumparser.util.ParseUtils;

import java.net.URL;

/**
 * XKCD API Response Image
 */
public class XkcdImage extends BaseMedia {
    private String mTitle;
    private String mDescription;
    private URL    mHighQualityUrl;

    public XkcdImage(String title, String description, String url) {
        mTitle = title;
        mDescription = description;

        if (url != null) {
            String imageUrl = url.replace("http://", "https://"); // Always return https URLs
            mHighQualityUrl = ParseUtils.getUrlObject(imageUrl);
        }
    }

    @Override
    public String getDescription() {
        return mDescription;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public URL getUrl(boolean highQuality) {
        // XKCD doesn't support low quality URLs
        if (!highQuality) return null;
        return mHighQualityUrl;
    }
}
