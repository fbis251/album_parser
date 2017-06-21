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
import com.fernandobarillas.albumparser.util.ExpectedHash;
import com.fernandobarillas.albumparser.util.ParseUtils;
import com.fernandobarillas.albumparser.util.TestUtils;
import com.fernandobarillas.albumparser.xkcd.XkcdParser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.fernandobarillas.albumparser.util.TestUtils.API_CALL_TIMEOUT_MS;
import static com.fernandobarillas.albumparser.util.TestUtils.apiDomainValid;
import static com.fernandobarillas.albumparser.util.TestUtils.assertInvalidUrlsThrowException;
import static com.fernandobarillas.albumparser.util.TestUtils.compareParserResponse;
import static com.fernandobarillas.albumparser.util.TestUtils.validateCanParseAndHashes;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for the Giphy API parser
 */
public class XkcdParserTest implements IParserTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();
    private XkcdParser mXkcdParser;

    public XkcdParserTest() {
        mXkcdParser = new XkcdParser(TestUtils.getOkHttpClient());
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
        List<ExpectedHash> validHashes = new ArrayList<>();
        // standard URL
        validHashes.add(new ExpectedHash("1", "http://xkcd.com/1"));
        validHashes.add(new ExpectedHash("695", "http://m.xkcd.com/695/"));
        validHashes.add(new ExpectedHash("1722", "http://www.xkcd.com/1722/#"));
        validHashes.add(new ExpectedHash("9223372036854775807",
                "http://xkcd.com/9223372036854775807"));
        validHashes.add(new ExpectedHash("/comics/infinite_scrolling.png",
                "http://imgs.xkcd.com/comics/infinite_scrolling.png"));
        validHashes.add(new ExpectedHash("/comics/goldbach_conjectures.png",
                "http://imgs.xkcd.com/comics/goldbach_conjectures.png"));

        validateCanParseAndHashes(mXkcdParser, validHashes, false);
    }

    @Test
    @Override
    public void testInvalidUrls() {
        Set<String> invalidUrls = new HashSet<>();
        invalidUrls.add("http://imgs.xkcd.com/comics/spirit.mp4");
        invalidUrls.add("http://xkcd.com/9223372036854775808");
        invalidUrls.add("http://xkcd.com/");
        invalidUrls.add("http://xkcd.com/-1");
        invalidUrls.add("http://xkcd.com/0");
        invalidUrls.add("https://xkcd.com/radiation/");
        invalidUrls.add("https://what-if.xkcd.com/151/");
        invalidUrls.add("https://blog.xkcd.com/");
        invalidUrls.add("http://xkcd.com/comics");
        invalidUrls.add("http://xkcd.com/111aaa111");
        assertInvalidUrlsThrowException(mXkcdParser, invalidUrls);
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
