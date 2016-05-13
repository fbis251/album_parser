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

package com.fernandobarillas.albumparser.media;

import java.net.URL;

/**
 * Created by fb on 5/11/16.
 */
public interface IMedia {
    int SIZE_UNAVAILABLE     = -1;
    int DURATION_UNAVAILABLE = -1;

    // Only protocol, no : or /
    String PROTOCOL_HTTP  = "http";
    String PROTOCOL_HTTPS = "https";

    // https://developer.android.com/guide/appendix/media-formats.html
    String EXT_3GP  = "3gp";
    String EXT_BMP  = "bmp";
    String EXT_GIF  = "gif";
    String EXT_GIFV = "gifv";
    String EXT_JPG  = "jpg";
    String EXT_JPEG = "jpeg";
    String EXT_MKV  = "mkv";
    String EXT_PNG  = "png";
    String EXT_MP4  = "mp4";
    String EXT_WEBM = "webm";
    String EXT_WEBP = "webp";

    /**
     * @param highQuality True to get the size of the high quality media, false to get the size of the low quality
     *                    media
     * @return The size in bytes of this media, {@link #SIZE_UNAVAILABLE} if no size returned by the API
     */
    int getByteSize(boolean highQuality);

    /**
     * @return The description of this media, null if no description available
     */
    String getDescription();

    /**
     * @return The duration in seconds of the media, useful if the media is an animation
     */
    double getDuration();

    /**
     * @param highQuality True to get the height of the high quality media, false to get the height of the low quality
     *                    media
     * @return The height of the media, {@link #SIZE_UNAVAILABLE} if no size returned by the API
     */
    int getHeight(boolean highQuality);

    /**
     * @return A URL to the preview image for this media, null if unavailable
     */
    URL getPreviewUrl();

    /**
     * @return The title of this media as returned by the API, null if not available
     */
    String getTitle();

    /**
     * @param highQuality True to get high quality media, false to attempt to get the low quality version
     * @return The URL to the media, null if no URL is available
     */
    URL getUrl(boolean highQuality);

    /**
     * @param highQuality True to get the width of the high quality media, false to get the width of the low quality
     *                    media
     * @return The height of the media, {@link #SIZE_UNAVAILABLE} if no size returned by the API
     */
    int getWidth(boolean highQuality);

    /**
     * @return True if this media is a video (mp4, webm, etc.) or a GIF, false otherwise
     */
    boolean isVideo();
}
