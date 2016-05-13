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

package com.fernandobarillas.albumparser.vidble;

import com.fernandobarillas.albumparser.media.IMedia;
import com.fernandobarillas.albumparser.util.ParseUtils;
import com.fernandobarillas.albumparser.vidble.api.VidbleApi;

import java.net.URL;

/**
 * Utilities for working with Vidble URLs and hashes
 */
public class VidbleUtils {

    /**
     * Attempts to get Vidble hash for a passed in URL String
     *
     * @param vidbleUrl The String to attempt to get a hash from
     * @return A Vidble album or URL hash if the passed in URL was a valid Vdible URL, null otherwise
     */
    public static String getHash(String vidbleUrl) {
        return getHash(ParseUtils.getUrlObject(vidbleUrl, VidbleApi.BASE_DOMAIN));
    }

    /**
     * Gets the Vidble hash for a URL
     *
     * @param url The URL to get a hash from
     * @return A hash that can be used to get a Vidble image/album, null if the Vidble URL was invalid
     */
    public static String getHash(URL url) {
        if (url == null) return null; // Passed in String wasn't a valid URL
        String path = url.getPath();

        if (path.startsWith("/album/")) {
            return ParseUtils.hashRegex(path, "/album/(\\w{" + VidbleApi.ALBUM_HASH_LENGTH + "})");
        }

        if (path.startsWith("/show/")) {
            return ParseUtils.hashRegex(path, "/show/(\\w{" + VidbleApi.IMAGE_HASH_LENGTH + "})");
        }

        // Probably a gallery URL with no prefix
        return ParseUtils.hashRegex(path, "/(\\w{" + VidbleApi.IMAGE_HASH_LENGTH + "})");
    }

    /**
     * Attempts to return a direct link to an image based on a hash alone, without doing an HTTP call to the Imgur API.
     * This means that this method might fail since it attempts to guess at a URL based on the passed in hash.
     *
     * @param hash The hash to get an image URL for
     * @return A URL to an image if the passed in hash was a valid non-album hash, null otherwise;
     */
    public static String getImageUrl(String hash) {
        if (hash == null) return null;
        if (hash.length() != VidbleApi.IMAGE_HASH_LENGTH) return null;
        return String.format("%s/%s.%s", VidbleApi.IMAGE_URL, hash, IMedia.EXT_JPG);
    }

    /**
     * Attempts to tell whether the passed in hash is from an album or a gallery. This is most useful when the hash was
     * gotten from {@link #getHash(String)}
     *
     * @param hash The hash to check
     * @return True if the hash appears to be for a Vidble album, false otherwise
     */
    public static boolean isAlbum(String hash) {
        return hash != null && hash.length() == VidbleApi.ALBUM_HASH_LENGTH;
    }

    /**
     * Checks whether the passed in urlString is a valid Vidble URL
     *
     * @param urlString The String to check for validity
     * @return True when urlString is a valid Vidble URL, false otherwise
     */
    public static boolean isVidbleUrl(String urlString) {
        return ParseUtils.getUrlObject(urlString, VidbleApi.BASE_DOMAIN) != null;
    }
}
