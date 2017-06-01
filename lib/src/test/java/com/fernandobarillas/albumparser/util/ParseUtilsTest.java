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

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static com.fernandobarillas.albumparser.util.ParseUtils.getSizeInMb;
import static com.fernandobarillas.albumparser.util.ParseUtils.getSizeInMbString;
import static com.fernandobarillas.albumparser.util.ParseUtils.getUrlObject;
import static com.fernandobarillas.albumparser.util.ParseUtils.hashRegex;
import static com.fernandobarillas.albumparser.util.ParseUtils.isDirectUrl;
import static com.fernandobarillas.albumparser.util.ParseUtils.isDomainMatch;
import static com.fernandobarillas.albumparser.util.ParseUtils.isGifExtension;
import static com.fernandobarillas.albumparser.util.ParseUtils.isImageExtension;
import static com.fernandobarillas.albumparser.util.ParseUtils.isVideoExtension;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by fb on 9/24/16.
 */
public class ParseUtilsTest {
    private static final String[] VALID_VIDEO_URLS = {
            // @formatter:off
            "http://example.com/file.3gp",
            "http://example.com/file.3GP",
            "http://example.com/file.gifv",
            "http://example.com/file.GIFV",
            "http://example.com/file.mkv",
            "http://example.com/file.MKV",
            "http://example.com/file.mp4",
            "http://example.com/file.MP4",
            "http://example.com/file.webm",
            "http://example.com/file.WEBM",
            // @formatter:on
    };

    private static final String[] VALID_IMAGE_URLS = {
            // @formatter:off
            "http://example.com/file.bmp",
            "http://example.com/file.BMP",
            "http://example.com/file.jpg",
            "http://example.com/file.JPG",
            "http://example.com/file.jpeg",
            "http://example.com/file.JPEG",
            "http://example.com/file.png",
            "http://example.com/file.PNG",
            "http://example.com/file.webp",
            "http://example.com/file.WEBP",
            // @formatter:on
    };

    private static final String[] VALID_GIF_URLS = {
            // @formatter:off
            "http://example.com/file.gif",
            "http://example.com/file.GIF",
            // @formatter:on
    };

    private static final String[] INVALID_MEDIA_URLs = {
            // @formatter:off
            null,
            "",
            "example.com",
            "http://example.com/file.html",
            "http://example.com/file.HTML",
            "http://example.com/file.txt",
            "http://example.com/file.txt",
            "http://example.com/file",
            "http://example.com/",
            // @formatter:on
    };

    @Test
    public void getExtensionTest() {
        Map<String, String> extensionMap = new HashMap<>();

        // Invalid extensions
        extensionMap.put(null, null);
        extensionMap.put(null, "example.com");
        extensionMap.put(null, "http://example.com/");
        extensionMap.put(null, "http://example.com/file");
        extensionMap.put(null, "http://example.com/path.with.dots/file");

        // Valid extensions
        extensionMap.put("3gp", "http://example.com/file.3gp");
        extensionMap.put("bmp", "http://example.com/file.bmp");
        extensionMap.put("gif", "http://example.com/file.gif");
        extensionMap.put("gifv", "http://example.com/file.gifv");
        extensionMap.put("jpg", "http://example.com/file.jpg");
        extensionMap.put("jpeg", "http://example.com/file.jpeg");
        extensionMap.put("mkv", "http://example.com/file.mkv");
        extensionMap.put("png", "http://example.com/file.png");
        extensionMap.put("mp4", "http://example.com/file.mp4");
        extensionMap.put("webm", "http://example.com/file.webm");
        extensionMap.put("webp", "http://example.com/file.webp");

        for (Map.Entry<String, String> entry : extensionMap.entrySet()) {
            String extension = entry.getKey();
            String url = entry.getValue();
            assertEquals(extension + " extension", extension, ParseUtils.getExtension(url));
        }
    }

    @Test
    public void getQueryMapTest() {
        String nullString = null;
        assertEquals("null URL String", null, ParseUtils.getQueryMap(nullString));
        URL nullUrl = null;
        assertEquals("null URL object", null, ParseUtils.getQueryMap(nullUrl));

        String urlWithoutParams = "http://example.com/";
        assertEquals("URL without params", null, ParseUtils.getQueryMap(urlWithoutParams));

        String urlWithParams = "http://example.com/?key1=value1&key2=value2";
        testQueryMapResult(ParseUtils.getQueryMap(urlWithParams));
        URI UriWithParams = URI.create("http://example.com/?key1=value1&key2=value2");
        testQueryMapResult(ParseUtils.getQueryMap(UriWithParams));
    }

    @Test
    public void testGetSizeInMb() {
        class SizeInput {
            private long   inputValue;
            private long   expectedValue;
            private String expectedString;

            private SizeInput(long inputValue, long expectedValue, String expectedString) {
                this.inputValue = inputValue;
                this.expectedValue = expectedValue;
                this.expectedString = expectedString;
            }
        }

        SizeInput[] results = {
                new SizeInput(0, 0, "0.0"),
                new SizeInput(-1, 0, "0.0"),
                new SizeInput(1000 * 1000, 1, "1.0"),
                new SizeInput((long) (10.5 * 1000 * 1000), 10, "10.5"),
                new SizeInput((long) (10.99 * 1000 * 1000), 10, "10.9"),
                new SizeInput(4227457, 4, "4.2"),
                new SizeInput(Long.MIN_VALUE, 0, "0.0"),
                new SizeInput(Long.MAX_VALUE, 9223372036854L, "9223372036854.0")
        };

        for (SizeInput test : results) {
            assertEquals(String.format("getSizeInMb(%d)", test.inputValue),
                    test.expectedValue,
                    getSizeInMb(test.inputValue));
            assertEquals(String.format("getSizeInMbString(%d)", test.inputValue),
                    test.expectedString,
                    getSizeInMbString(test.inputValue));
        }
    }

    @Test
    public void testGetUrlObject() throws MalformedURLException {
        class TestInput {
            private String input;
            private URL    expected;

            public TestInput(String input, String expected) {
                this.input = input;
                try {
                    this.expected = new URL(expected);
                } catch (MalformedURLException ignored) {
                }
            }
        }

        TestInput[] testInputs = {
                // @formatter:off
                new TestInput(null, null),
                new TestInput("example.com", null),
                new TestInput("http://example.com", "http://example.com"),
                new TestInput("http://example.com?key=", "http://example.com?key=")
                // @formatter:on
        };
        for (TestInput input : testInputs) {
            assertEquals("input = [" + input.input + ']',
                    input.expected,
                    getUrlObject(input.input));
        }

        class TestInput2 {
            private String input;
            private String domain;
            private URL    expected;

            public TestInput2(String input, String domain, String expected) {
                this.input = input;
                this.domain = domain;
                try {
                    this.expected = new URL(expected);
                } catch (MalformedURLException ignored) {
                }
            }
        }

        TestInput2[] testInput2s = {
                // @formatter:off
                new TestInput2(null, null, null),
                new TestInput2(null, "example.com", null),
                new TestInput2("example.com", "example.com", null),
                new TestInput2("http://", "example.com", null),
                new TestInput2("http://notexample.com", "example.com", null),
                new TestInput2("http://example.com", "example.com", "http://example.com"),
                new TestInput2("http://example.com?key=", "example.com", "http://example.com?key="),
                new TestInput2("http://subdomain.example.com", "example.com", "http://subdomain.example.com"),
                // @formatter:on
        };

        for (TestInput2 input2 : testInput2s) {
            assertEquals("input = [" + input2.input + ']',
                    input2.expected,
                    getUrlObject(input2.input, input2.domain));
        }
    }

    @Test
    public void testHashRegex() {
        String nullString = null;
        Pattern nullPattern = null;
        // Null checks
        assertEquals(null, hashRegex(null, nullString));
        assertEquals(null, hashRegex(null, nullPattern));
        assertEquals(null, hashRegex(null, "test"));

        // No capturing groups
        assertEquals(null, hashRegex("test", "test"));

        // Invalid regex
        assertEquals(null, hashRegex("test", "(TEST)"));

        // 2 capturing groups
        assertEquals(null, hashRegex("test", "()()"));
        assertEquals(null, hashRegex("test123", "(.*?)(\\d+)"));

        // Valid inputs
        assertEquals("", hashRegex("test", "()"));
        assertEquals("test", hashRegex("test", "(test)"));
        assertEquals("123", hashRegex("test123", ".*?([\\d]+).*?"));
    }

    @Test
    public void testIsDirectUrl() {
        for (String url : VALID_IMAGE_URLS) {
            assertTrue("url = [" + url + ']', isDirectUrl(url));
        }

        for (String url : VALID_VIDEO_URLS) {
            assertTrue("url = [" + url + ']', isDirectUrl(url));
        }

        for (String url : VALID_GIF_URLS) {
            assertTrue("url = [" + url + ']', isDirectUrl(url));
        }

        for (String url : INVALID_MEDIA_URLs) {
            assertFalse("url = [" + url + ']', isDirectUrl(url));
        }
    }

    @Test
    public void testIsDomainStringMatch() {
        // Invalid inputs
        String nullString = null;
        assertFalse(isDomainMatch(null, nullString));
        assertFalse(isDomainMatch("example.com", nullString));
        assertFalse(isDomainMatch("example", "example.com"));
        assertFalse(isDomainMatch("test-example.com", "example.com"));

        // Valid inputs
        assertTrue(isDomainMatch("example.com", "example.com"));
        assertTrue(isDomainMatch("www.example.com", "example.com"));
        assertTrue(isDomainMatch("sub.domain.example.com", "example.com"));
    }

    @Test
    public void testIsGifExtension() {
        for (String url : VALID_IMAGE_URLS) {
            assertFalse("url = [" + url + ']', isGifExtension(url));
        }

        for (String url : VALID_VIDEO_URLS) {
            assertFalse("url = [" + url + ']', isGifExtension(url));
        }

        for (String url : VALID_GIF_URLS) {
            assertTrue("url = [" + url + ']', isGifExtension(url));
        }

        for (String url : INVALID_MEDIA_URLs) {
            assertFalse("url = [" + url + ']', isGifExtension(url));
        }

        for (String url : INVALID_MEDIA_URLs) {
            assertFalse("url = [" + url + ']', isGifExtension(url));
        }
    }

    @Test
    public void testIsImageExtension() {
        for (String url : VALID_IMAGE_URLS) {
            assertTrue("url = [" + url + ']', isImageExtension(url));
        }

        for (String url : VALID_VIDEO_URLS) {
            assertFalse("url = [" + url + ']', isImageExtension(url));
        }

        for (String url : VALID_GIF_URLS) {
            assertFalse("url = [" + url + ']', isImageExtension(url));
        }

        for (String url : INVALID_MEDIA_URLs) {
            assertFalse("url = [" + url + ']', isImageExtension(url));
        }

        for (String url : INVALID_MEDIA_URLs) {
            assertFalse("url = [" + url + ']', isImageExtension(url));
        }
    }

    @Test
    public void testIsVideoExtension() {
        for (String url : VALID_IMAGE_URLS) {
            assertFalse("url = [" + url + ']', isVideoExtension(url));
        }

        for (String url : VALID_VIDEO_URLS) {
            assertTrue("url = [" + url + ']', isVideoExtension(url));
        }

        for (String url : VALID_GIF_URLS) {
            assertFalse("url = [" + url + ']', isVideoExtension(url));
        }

        for (String url : INVALID_MEDIA_URLs) {
            assertFalse("url = [" + url + ']', isVideoExtension(url));
        }

        for (String url : INVALID_MEDIA_URLs) {
            assertFalse("url = [" + url + ']', isVideoExtension(url));
        }
    }

    private void testQueryMapResult(final Map<String, String> queryMap) {
        Map<String, String> expectedQueryMap = new HashMap<>();
        expectedQueryMap.put("key1", "value1");
        expectedQueryMap.put("key2", "value2");
        for (Map.Entry<String, String> queryParam : expectedQueryMap.entrySet()) {
            String queryKey = queryParam.getKey();
            String queryValue = queryParam.getValue();
            assertTrue(queryKey + " contains key", queryMap.containsKey(queryKey));
            assertEquals(queryKey + " value equal", queryValue, queryMap.get(queryKey));
        }
    }
}
