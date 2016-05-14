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

package com.fernandobarillas.albumparser.streamable;

import com.fernandobarillas.albumparser.media.IMedia;
import com.fernandobarillas.albumparser.streamable.api.StreamableApi;
import com.fernandobarillas.albumparser.util.ParseUtils;

import java.net.URL;

/**
 * Created by fb on 5/10/16.
 */
public class StreamableUtils {
    private static final int    VIDEO_HASH_LENGTH = 4;
    private static final String MP4_URL           = "/mp4/";
    private static final String MP4_MOBILE_URL    = "/mp4-mobile/";

    /**
     * Attempts to get a Streamable hash for a passed in URL String
     *
     * @param streamableUrl The String to attempt to get a hash from
     * @return An Imgur album or URL hash if the passed in URL was a valid Imgur URL, null otherwise
     */
    public static String getHash(String streamableUrl) {
        URL url = ParseUtils.getUrlObject(streamableUrl, StreamableApi.BASE_DOMAIN);
        if (url == null) return null; // Passed in String wasn't a valid URL
        String path = url.getPath();

        if (path.contains(MP4_URL)) {
            return ParseUtils.hashRegex(path, MP4_URL + "(\\w{" + VIDEO_HASH_LENGTH + "})");
        }

        if (path.contains(MP4_MOBILE_URL)) {
            return ParseUtils.hashRegex(path, MP4_MOBILE_URL + "(\\w{" + VIDEO_HASH_LENGTH + "})");
        }

        return ParseUtils.hashRegex(path, "/(\\w+)");
    }

    /**
     * @param hash The hash to get the URL for
     * @return A URL to a mobile quality vid.me MP4
     */
    public static String getMp4MobileUrl(String hash) {
        return getMp4Url(hash, false);
    }

    /**
     * @param hash The hash to get the URL for
     * @return A URL to a high quality vid.me MP4
     */
    public static String getMp4Url(String hash) {
        return getMp4Url(hash, true);
    }

    /**
     * @param hash        The hash to get the URL for
     * @param highQuality True to get a high quality MP4 URL, false to get a mobile quality MP4 URL
     * @return A URL to a high or mobile quality MP4, null if the hash is null
     */
    public static String getMp4Url(String hash, boolean highQuality) {
        if (hash == null) return null;
        String quality = (highQuality) ? MP4_URL : MP4_MOBILE_URL;
        return String.format("%s%s%s.%s", StreamableApi.CDN_URL, quality, hash, IMedia.EXT_MP4);
    }
}
