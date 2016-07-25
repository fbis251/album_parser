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
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.fernandobarillas.albumparser.streamable.model;

import com.fernandobarillas.albumparser.media.BaseMedia;
import com.fernandobarillas.albumparser.util.ParseUtils;

import java.net.URL;

/**
 * Created by fb on 5/11/16.
 */
public class StreamableMedia extends BaseMedia {
    Mp4       mMp4;
    Mp4Mobile mMp4Mobile;
    URL       mPreviewUrl;

    public StreamableMedia(Mp4 mp4, Mp4Mobile mp4Mobile, URL previewUrl) {
        mMp4 = mp4;
        mMp4Mobile = mp4Mobile;
        mPreviewUrl = previewUrl;
    }

    @Override
    public int getByteSize(boolean highQuality) {
        // By default always try to return the high quality size
        int resultSize = (mMp4 != null) ? mMp4.size : SIZE_UNAVAILABLE;
        if (!highQuality) {
            resultSize = (mMp4Mobile != null) ? mMp4Mobile.size : SIZE_UNAVAILABLE;
        }
        return resultSize;
    }

    @Override
    public int getHeight(boolean highQuality) {
        // By default always try to return the high quality height
        int resultHeight = (mMp4 != null) ? mMp4.height : SIZE_UNAVAILABLE;
        if (!highQuality) {
            resultHeight = (mMp4Mobile != null) ? mMp4Mobile.height : SIZE_UNAVAILABLE;
        }
        return resultHeight;
    }

    @Override
    public URL getPreviewUrl() {
        return mPreviewUrl;
    }

    @Override
    public URL getUrl(boolean highQuality) {
        // By default always try to return the high quality URL
        String mediaUrl = (mMp4 != null) ? mMp4.url : null;
        if (!highQuality && mMp4Mobile != null) {
            mediaUrl = mMp4Mobile.url;
        }
        if (mediaUrl == null) return null;
        String resultUrl = mediaUrl.startsWith("http") ? mediaUrl : PROTOCOL_HTTPS + ":" + mediaUrl;
        return ParseUtils.getUrlObject(resultUrl);
    }

    @Override
    public int getWidth(boolean highQuality) {
        // By default always try to return the high quality width
        int resultWidth = (mMp4 != null) ? mMp4.width : SIZE_UNAVAILABLE;
        if (!highQuality) {
            resultWidth = (mMp4Mobile != null) ? mMp4Mobile.width : SIZE_UNAVAILABLE;
        }
        return resultWidth;
    }

    @Override
    public boolean isVideo() {
        return true;
    }
}
