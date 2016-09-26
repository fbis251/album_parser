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

package com.fernandobarillas.albumparser.parser;

import com.fernandobarillas.albumparser.exception.InvalidApiResponseException;
import com.fernandobarillas.albumparser.model.ExpectedMedia;
import com.fernandobarillas.albumparser.model.ExpectedParserResponse;
import com.fernandobarillas.albumparser.util.ParseUtils;
import com.fernandobarillas.albumparser.xkcd.XkcdParser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;

import static com.fernandobarillas.albumparser.AllTests.API_CALL_TIMEOUT_MS;
import static com.fernandobarillas.albumparser.AllTests.apiDomainValid;
import static com.fernandobarillas.albumparser.AllTests.assertInvalidHashesThrowException;
import static com.fernandobarillas.albumparser.AllTests.compareParserResponse;
import static com.fernandobarillas.albumparser.AllTests.validateCanParseAndHashes;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for the Giphy API parser
 */
public class XkcdParserTest implements IParserTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();
    private OkHttpClient mOkHttpClient;
    private XkcdParser   mXkcdParser;

    public XkcdParserTest() {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.1.2", 8080));
        mOkHttpClient = new OkHttpClient.Builder().proxy(proxy).build();
        mXkcdParser = new XkcdParser(mOkHttpClient);
    }

    @Test(expected = InvalidApiResponseException.class, timeout = API_CALL_TIMEOUT_MS)
    @Override
    public void testApi404Error() throws IOException, RuntimeException {
        URL invalid404Url = ParseUtils.getUrlObject("http://xkcd.com/100000000");
        assertNotNull(invalid404Url);
        mXkcdParser.parse(invalid404Url);
    }

    @Test
    @Override
    public void testApiUsesHttps() {
        apiDomainValid(mXkcdParser, "xkcd.com", false);
    }

    @Test
    @Override
    public void testCanParseAndGetHash() {
        System.out.println("Long.MAX_VALUE = [" + Long.MAX_VALUE + ']');
        Map<String, String> validHashes = new HashMap<>();
        // standard URL
        validHashes.put("1", "http://xkcd.com/1");
        validHashes.put("9223372036854775807", "http://xkcd.com/9223372036854775807");
        validHashes.put("/comics/infinite_scrolling",
                "http://imgs.xkcd.com/comics/infinite_scrolling.png");
        validHashes.put("/comics/goldbach_conjectures",
                "http://imgs.xkcd.com/comics/goldbach_conjectures.png");

        validateCanParseAndHashes(mXkcdParser, validHashes, false);
    }

    // Tests a direct image link
    @Test()
    public void testDirectImage() throws IOException, RuntimeException {
        URL xkcdUrl = ParseUtils.getUrlObject("http://imgs.xkcd.com/comics/spirit.png");
        ExpectedMedia expectedMedia = new ExpectedMedia.Builder().setHighQualityUrl(
                "https://imgs.xkcd.com/comics/spirit.png").build();
        ExpectedParserResponse expectedParserResponse =
                new ExpectedParserResponse.Builder(xkcdUrl).setMedia(expectedMedia)
                        .setIsSingleMedia(true)
                        .build();
        ParserResponse parserResponse = mXkcdParser.parse(xkcdUrl);
        compareParserResponse(xkcdUrl, expectedParserResponse, parserResponse);
    }

    @Test
    public void testInvalidUrls() {
        Map<String, String> invalidHashes = new HashMap<>();
        invalidHashes.put("http://xkcd.com/9223372036854775808",
                "http://xkcd.com/9223372036854775808");
        invalidHashes.put("http://xkcd.com/", "http://xkcd.com/");
        invalidHashes.put("http://xkcd.com/-1", "http://xkcd.com/-1");
//        invalidHashes.put("http://xkcd.com/0", "http://xkcd.com/0");
        invalidHashes.put("https://what-if.xkcd.com/151/", "https://what-if.xkcd.com/151/");
        invalidHashes.put("https://blog.xkcd.com/", "https://blog.xkcd.com/");
        invalidHashes.put("http://xkcd.com/comics", "http://xkcd.com/comics");
        invalidHashes.put("http://xkcd.com/111aaa111", "http://xkcd.com/111aaa111");
        invalidHashes.put(null, "");
        invalidHashes.put(null, null);

        assertInvalidHashesThrowException(mXkcdParser, invalidHashes);
    }

    // Tests an xkcd standard URL
    @Test(timeout = API_CALL_TIMEOUT_MS)
    public void testParser() throws IOException, RuntimeException {
        URL xkcdUrl = ParseUtils.getUrlObject("http://xkcd.com/695/");
        ExpectedMedia expectedMedia = new ExpectedMedia.Builder().setHighQualityUrl(
                "https://imgs.xkcd.com/comics/spirit.png")
                .setTitle("Spirit")
                .setDescription(
                        "On January 26th, 2274 Mars days into the mission, NASA declared Spirit a 'stationary research station', expected to stay operational for several more months until the dust buildup on its solar panels forces a final shutdown.")
                .build();
        ExpectedParserResponse expectedParserResponse =
                new ExpectedParserResponse.Builder(xkcdUrl).setMedia(expectedMedia)
                        .setIsSingleMedia(true)
                        .build();
        ParserResponse parserResponse = mXkcdParser.parse(xkcdUrl);
        compareParserResponse(xkcdUrl, expectedParserResponse, parserResponse);
    }


}
