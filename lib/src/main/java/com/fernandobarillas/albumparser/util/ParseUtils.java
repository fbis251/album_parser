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

package com.fernandobarillas.albumparser.util;

import com.fernandobarillas.albumparser.media.IMedia;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilities that help with parsing, validating and matching in URL Strings
 */
public class ParseUtils {

    /**
     * Tries to get the extension of the passed-in URL
     *
     * @param url The URL to get an extension from
     * @return A String containing only the extension with no . prefix if an extension could be found, null otherwise
     */
    public static String getExtension(URL url) {
        if (url == null) return null;
        String path = url.getPath();
        if (!path.contains(".")) return null;
        int extensionIndex = path.lastIndexOf(".") + 1;
        if (extensionIndex > path.length()) return null;
        String extension = path.substring(extensionIndex)
                .toLowerCase();
        return (!extension.isEmpty()) ? extension : null;
    }

    /**
     * Converts the input byte size to megabytes
     *
     * @param byteSize The number of bytes to convert to megabytes
     * @return The byteSize in megabytes
     */
    public static double getSizeInMb(int byteSize) {
        return byteSize / 1024.0 / 1024.0;
    }

    /**
     * Converts the input byte size to megabytes
     *
     * @param byteSize The number of bytes to convert to megabytes
     * @return A String with the size in megabytes
     */
    public static String getSizeInMbString(int byteSize) {
        return String.format("%.1f", getSizeInMb(byteSize));
    }

    /**
     * Verifies that a URL contains a certain TLD or a subdomain of the passed in TLD. Additionally the passed-in
     * urlString will be converted to a valid URL object
     *
     * @param urlString  A url to attempt to convert to a URL object
     * @param baseDomain The TLD to match with no subdomain prefix or trailing slash, example: imgur.com
     * @return A URL Object representation of the passed in urlString
     */
    public static URL getUrlObject(String urlString, String baseDomain) {
        URL url = getUrlObject(urlString);
        if (url == null) return null;

        String domain = url.getHost();
        if (domain.equals(baseDomain) || domain.endsWith("." + baseDomain)) {
            return url;
        }

        return null;
    }

    /**
     * Get a URL Object from a passed in String
     *
     * @param urlString The String to attempt to parse as a URL
     * @return A URL representation if the passed in String is a valid URL, null otherwise
     */
    public static URL getUrlObject(String urlString) {
        try {
            return new URL(urlString);
        } catch (MalformedURLException ignored) {
        }
        return null;
    }

    /**
     * Tries to find the regex in the haystack
     *
     * @param haystack    A String target for the regex
     * @param needleRegex A regex String with exactly 1 capturing group
     * @return The text captured by the needledRegex capturing group if the regex matched, null otherwise
     */
    public static String hashRegex(String haystack, String needleRegex) {
        Pattern pattern = Pattern.compile(needleRegex);
        Matcher matcher = pattern.matcher(haystack);
        if (matcher.find() && matcher.groupCount() == 1) {
            return matcher.group(1);
        }
        return null;
    }

    /**
     * @param mediaUrl The URL to check
     * @return True when the URL likely links to an image or video, false otherwise
     */
    public static boolean isDirectUrl(URL mediaUrl) {
        return ParseUtils.isVideoExtension(mediaUrl) || ParseUtils.isImageExtension(mediaUrl);
    }

    /**
     * @param url The URL to check for an image extension
     * @return True if the extension of the file in the URL appears to be for an image, false otherwise
     */
    public static boolean isImageExtension(URL url) {
        String extension = getExtension(url);
        if (extension == null) return false;
        switch (extension) {
            case IMedia.EXT_BMP:
            case IMedia.EXT_JPG:
            case IMedia.EXT_JPEG:
            case IMedia.EXT_PNG:
            case IMedia.EXT_WEBP:
                return true;
        }

        return false;
    }

    /**
     * @param url The URL to check for a video extension
     * @return True if the extension of the file in the URL appears to be for a video or GIF, false otherwise
     */
    public static boolean isVideoExtension(URL url) {
        String extension = getExtension(url);
        if (extension == null) return false;
        switch (extension) {
            case IMedia.EXT_3GP:
            case IMedia.EXT_GIF:
            case IMedia.EXT_GIFV:
            case IMedia.EXT_MKV:
            case IMedia.EXT_MP4:
            case IMedia.EXT_WEBM:
                return true;
        }

        return false;
    }
}
