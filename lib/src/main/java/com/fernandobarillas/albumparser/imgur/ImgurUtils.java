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

package com.fernandobarillas.albumparser.imgur;

import com.fernandobarillas.albumparser.imgur.api.ImgurApi;
import com.fernandobarillas.albumparser.media.IMedia;
import com.fernandobarillas.albumparser.util.ParseUtils;

import java.net.URL;

/**
 * Created by fb on 5/3/16.
 */
public class ImgurUtils {
    private static final int ALBUM_HASH_LENGTH = 5; // Most recent album hashes are exactly 5 characters long
    private static final int IMAGE_HASH_LENGTH = 7; // Most recent image hashes are exactly 7 characters long

    /**
     * Attempts to get an Imgur hash for a passed in URL String
     *
     * @param imgurUrl The String to attempt to get a hash from
     * @return An Imgur album or URL hash if the passed in URL was a valid Imgur URL, null otherwise
     */
    public static String getHash(String imgurUrl) {
        URL url = ParseUtils.getUrlObject(imgurUrl, ImgurApi.BASE_DOMAIN);
        if (url == null) return null; // Passed in String wasn't a valid URL
        String path = url.getPath();

        if (path.startsWith("/gallery/")) {
            // /gallery URLs can contain both album and image hashes, try to regex for an image hash, if not return
            // the album hash result instead
            String result = ParseUtils.hashRegex(path, "/gallery/(\\w{" + IMAGE_HASH_LENGTH + "})");
            if (result == null) {
                return ParseUtils.hashRegex(path, "/gallery/(\\w{" + ALBUM_HASH_LENGTH + "})");
            }
            return result;
        }

        if (path.startsWith("/a/")) {
            return ParseUtils.hashRegex(path, "/a/(\\w{" + ALBUM_HASH_LENGTH + "})");
        }

        // Probably a gallery URL with no prefix
        return ParseUtils.hashRegex(path, "/(\\w{" + IMAGE_HASH_LENGTH + "})");
    }

    /**
     * Attempts to return a direct link to an image based on a hash alone, without doing an HTTP call to the Imgur API.
     * This means that this method might fail since it attempts to guess at a URL based on the passed in hash.
     *
     * @param hash      The hash to get an image URL for
     * @param extension The extension to use for the URL. This should not have a prefixed period, Example: jpg not .jpg
     * @return A URL to an image if the passed in hash was a valid non-album hash, null otherwise;
     */
    public static String getImageUrl(String hash, String extension) {
        String newExt = (extension == null) ? IMedia.EXT_JPG : extension;
        if (hash == null) return null;
        if (hash.length() <= ALBUM_HASH_LENGTH) return null;
        if (isAlbum(hash)) return null;
        return String.format("%s/%s.%s", ImgurApi.IMAGE_URL, hash, newExt);
    }

    public static String getImageUrl(String hash) {
        return getImageUrl(hash, null);
    }

    /**
     * Attempts to tell whether the passed in hash is from an album or a gallery. This is most useful when the hash was
     * gotten from {@link #getHash(String)}
     *
     * @param hash The hash to check
     * @return True if the hash appears to be for an Imgur album, false otherwise
     */
    public static boolean isAlbum(String hash) {
        return hash != null && hash.length() == ALBUM_HASH_LENGTH;
    }

    /**
     * Checks whether the passed in urlString is a valid Imgur URL
     *
     * @param urlString The String to check for validity
     * @return True when urlString is a valid Imgur URL, false otherwise
     */
    public static boolean isImgurUrl(String urlString) {
        return ParseUtils.getUrlObject(urlString, ImgurApi.BASE_DOMAIN) != null;
    }
}
