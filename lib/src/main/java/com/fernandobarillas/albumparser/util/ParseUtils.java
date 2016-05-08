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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilities that help with parsing, validating and matching in URL Strings
 */
public class ParseUtils {

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
     * Verifies that a URL contains a certain TLD or a subdomain of the passed in TLD. Additionally the passed-in
     * urlString will be converted to a valid URL object
     *
     * @param urlString  A url to attempt to convert to a URL object
     * @param baseDomain The TLD to match with no subdomain prefix or trailing slash, example: imgur.com
     * @return
     */
    public static URL getUrlObject(String urlString, String baseDomain) {
        try {
            URL url = new URL(urlString);
            String domain = url.getHost();
            if (domain.equals(baseDomain) || domain.endsWith("." + baseDomain)) {
                return url;
            }
        } catch (MalformedURLException ignored) {
        }

        return null;
    }
}
