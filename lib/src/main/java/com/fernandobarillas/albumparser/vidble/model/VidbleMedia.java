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

package com.fernandobarillas.albumparser.vidble.model;

import com.fernandobarillas.albumparser.media.BaseMedia;
import com.fernandobarillas.albumparser.util.ParseUtils;
import com.fernandobarillas.albumparser.vidble.VidbleUtils;
import com.fernandobarillas.albumparser.vidble.api.VidbleApi;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class is a wrapper by url Strings returned via the Vidble API. These URLs tend to look like:
 * //www.vidble.com/[HASH].[EXTENSION]
 * <p>
 * Note that the URLs have no protocol specification according to RFC 1808 https://www.ietf.org/rfc/rfc1808.txt
 * <p>
 * The constructor will also accept URLs with an http/https prefix [http/https]://www.vidble.com/[HASH].[EXTENSION]
 */
public class VidbleMedia extends BaseMedia {
    private final static String MEDIUM_QUALITY   = "_med";
    private final static String ORIGINAL_QUALITY = "";
    private String mExtension;
    private String mHash;

    /**
     * Attempts to parse a URL String and validates it as a Vidble URL
     *
     * @param urlString The String to validate and parse as a Vidble URL
     */
    public VidbleMedia(String urlString) {
        if (urlString == null) return;

        if (!urlString.startsWith("http")) {
            urlString = "http:" + urlString;
        }
        urlString = urlString.replaceFirst("http:", "https:"); // Force HTTPS
        URL url = ParseUtils.getUrlObject(urlString, VidbleApi.BASE_DOMAIN);

        mHash = VidbleUtils.getHash(url);
        mExtension = ParseUtils.getExtension(url);
        if (mExtension == null) {
            mExtension = EXT_JPG; // Try to guess at it, even with a jpg extension the Vidble returns the image/gif
        }

        mExtension = mExtension.toLowerCase();
    }

    @Override
    public URL getPreviewUrl() {
        // Get JPG preview if this is a GIF
        String extension = (mExtension.equals(EXT_GIF)) ? EXT_JPG : mExtension;
        try {
            return new URL(VidbleApi.IMAGE_URL + "/" + mHash + MEDIUM_QUALITY + "." + extension);
        } catch (MalformedURLException ignored) {
        }
        return null;
    }

    @Override
    public URL getUrl(boolean highQuality) {
        String quality = ORIGINAL_QUALITY;
        if (!highQuality && !mExtension.equals(EXT_GIF)) {
            // Vidble doesn't provide lower quality GIFs, don't use medium quality at all for GIFs
            quality = MEDIUM_QUALITY;
        }

        try {
            return new URL(VidbleApi.IMAGE_URL + "/" + mHash + quality + "." + mExtension);
        } catch (MalformedURLException ignored) {
        }
        return null;
    }

    @Override
    public boolean isVideo() {
        return mExtension.equals(EXT_GIF);
    }
}
