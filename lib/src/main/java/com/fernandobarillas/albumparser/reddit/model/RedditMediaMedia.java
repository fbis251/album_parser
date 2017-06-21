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

package com.fernandobarillas.albumparser.reddit.model;

import com.fernandobarillas.albumparser.media.BaseMedia;
import com.fernandobarillas.albumparser.util.ParseUtils;

import java.net.URL;
import java.util.Map;

/**
 * Model for redditmedia.com media URLs
 */
public class RedditMediaMedia extends BaseMedia {
    /** reddit URLs sometimes contain the fm={some_extension} param in the URL */
    private static final String FORMAT_PARAM = "fm";
    private static final String WIDTH_PARAM  = "w";
    private URL     mUrl;
    private boolean mIsVideo;
    private int mWidth = SIZE_UNAVAILABLE;

    public RedditMediaMedia(URL url) {
        this.mUrl = url;
        Map<String, String> queryMap = ParseUtils.getQueryMap(mUrl);
        if (queryMap.containsKey(FORMAT_PARAM)) {
            mIsVideo = EXT_MP4.equalsIgnoreCase(queryMap.get(FORMAT_PARAM));
        }
        if (queryMap.containsKey(WIDTH_PARAM)) {
            mWidth = SIZE_UNAVAILABLE;
            try {
                mWidth = Integer.parseInt(queryMap.get(WIDTH_PARAM));
            } catch (NumberFormatException ignored) {
            }
        }
    }

    @Override
    public URL getUrl(boolean highQuality) {
        return highQuality ? mUrl : null;
    }

    @Override
    public int getWidth(boolean highQuality) {
        return highQuality ? mWidth : SIZE_UNAVAILABLE;
    }

    @Override
    public boolean isVideo() {
        return mIsVideo;
    }
}
