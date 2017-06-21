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

import com.fernandobarillas.albumparser.ApiKeys;
import com.fernandobarillas.albumparser.exception.InvalidApiResponseException;
import com.fernandobarillas.albumparser.giphy.GiphyParser;
import com.fernandobarillas.albumparser.model.ExpectedMedia;
import com.fernandobarillas.albumparser.model.ExpectedParserResponse;
import com.fernandobarillas.albumparser.util.ExpectedHash;
import com.fernandobarillas.albumparser.util.ParseUtils;
import com.fernandobarillas.albumparser.util.TestUtils;

import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.fernandobarillas.albumparser.util.TestUtils.API_CALL_TIMEOUT_MS;
import static com.fernandobarillas.albumparser.util.TestUtils.apiDomainValid;
import static com.fernandobarillas.albumparser.util.TestUtils.compareParserResponse;
import static com.fernandobarillas.albumparser.util.TestUtils.validateCanParseAndHashes;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for the Giphy API parser
 */
public class GiphyParserTest implements IParserTest {
    private GiphyParser mGiphyParser;
    private GiphyParser mGiphyParserNoApiKey;

    public GiphyParserTest() {
        mGiphyParser = new GiphyParser(TestUtils.getOkHttpClient(), ApiKeys.GIPHY_API_KEY);
        mGiphyParserNoApiKey = new GiphyParser(TestUtils.getOkHttpClient());
    }

    @Test(expected = InvalidApiResponseException.class, timeout = API_CALL_TIMEOUT_MS)
    @Override
    public void testApi404Error() throws IOException, RuntimeException {
        URL invalid404Url = ParseUtils.getUrlObject(
                "https://giphy.com/gifs/omaze-chris-pratt-dinosaurs-l0NhZ0aUSE8fXag13");
        assertNotNull(invalid404Url);
        mGiphyParser.parse(invalid404Url);
    }

    @Test
    @Override
    public void testApiUsesHttps() {
        apiDomainValid(mGiphyParser, "giphy.com", false);
    }

    @Test
    @Override
    public void testCanParseAndGetHash() {
        List<ExpectedHash> validHashes = new ArrayList<>();

        // /gifs/{hash}
        validHashes.add(new ExpectedHash("l0HlD7sTICR75rDHy",
                "http://giphy.com/gifs/l0HlD7sTICR75rDHy"));
        // /gifs/{hash}/suffix
        validHashes.add(new ExpectedHash("l0HlD7sTICR75rDHy",
                "http://giphy.com/gifs/l0HlD7sTICR75rDHy/html5"));
        // /gifs/prefix-{hash}
        validHashes.add(new ExpectedHash("l0HlD7sTICR75rDHy",
                "https://giphy.com/gifs/simpsons-l0HlD7sTICR75rDHy"));
        // direct GIF url, media domain
        validHashes.add(new ExpectedHash("l0HlD7sTICR75rDHy",
                "https://media.giphy.com/media/l0HlD7sTICR75rDHy/giphy.gif"));
        // direct MP4 url, media domain
        validHashes.add(new ExpectedHash("l0HlD7sTICR75rDHy",
                "https://media.giphy.com/media/l0HlD7sTICR75rDHy/giphy.mp4"));
        // direct GIF url, i domain
        validHashes.add(new ExpectedHash("l0HlD7sTICR75rDHy",
                "http://i.giphy.com/l0HlD7sTICR75rDHy.gif"));
        // direct GIFV url, i domain
        validHashes.add(new ExpectedHash("l0HlD7sTICR75rDHy",
                "http://i.giphy.com/l0HlD7sTICR75rDHy.gifv"));
        // direct gifOriginal url, i domain
        validHashes.add(new ExpectedHash("l0HlD7sTICR75rDHy",
                "http://i.giphy.com/l0HlD7sTICR75rDHy.gifOriginal"));
        // /embed/{hash} url
        validHashes.add(new ExpectedHash("l0HlD7sTICR75rDHy",
                "http://giphy.com/embed/l0HlD7sTICR75rDHy?html5=true"));

        validateCanParseAndHashes(mGiphyParser, validHashes, false);
    }

    @Override
    public void testInvalidUrls() {
        // TODO: Implement me
    }

    // Tests a standard gifphy URL
    @Test(timeout = API_CALL_TIMEOUT_MS)
    public void testParser() throws IOException, RuntimeException {
        URL giphyUrl = ParseUtils.getUrlObject(
                "https://giphy.com/gifs/omaze-chris-pratt-dinosaurs-l0NhZ0aUSE8fXag12");
        ExpectedMedia expectedMedia = new ExpectedMedia.Builder().setIsVideo(true)
                .setPreviewUrl("https://media0.giphy.com/media/l0NhZ0aUSE8fXag12/giphy_s.gif")
                .setHighQualityWidth(615)
                .setHighQualityHeight(346)
                .setHighQualityByteSize(81247)
                .setHighQualityUrl("https://media4.giphy.com/media/l0NhZ0aUSE8fXag12/giphy.mp4")
                .setLowQualityWidth(355)
                .setLowQualityHeight(200)
                .setLowQualityByteSize(51342)
                .setLowQualityUrl("https://media0.giphy.com/media/l0NhZ0aUSE8fXag12/200.mp4")
                .build();
        ExpectedParserResponse expectedParserResponse = new ExpectedParserResponse.Builder(giphyUrl)
                .setMedia(expectedMedia)
                .setIsSingleMedia(true)
                .build();
        ParserResponse parserResponse = mGiphyParser.parse(giphyUrl);
        compareParserResponse(giphyUrl, expectedParserResponse, parserResponse);
    }

    // Tests a direct GIF URL with no API call
    @Test
    public void testParserNoApiCall() throws IOException {
        URL giphyUrl = ParseUtils.getUrlObject(
                "https://media.giphy.com/media/l0HlD7sTICR75rDHy/giphy_s.gif");
        ExpectedMedia expectedMedia = new ExpectedMedia.Builder().setIsVideo(true)
                .setPreviewUrl("https://media.giphy.com/media/l0HlD7sTICR75rDHy/giphy_s.gif")
                .setHighQualityUrl("https://media.giphy.com/media/l0HlD7sTICR75rDHy/giphy.mp4")
                .build();
        ExpectedParserResponse expectedParserResponse = new ExpectedParserResponse.Builder(giphyUrl)
                .setMedia(expectedMedia)
                .setIsSingleMedia(true)
                .build();
        ParserResponse parserResponse = mGiphyParserNoApiKey.parse(giphyUrl);
        compareParserResponse(giphyUrl, expectedParserResponse, parserResponse);
    }

    // Tests an image URL that has no MP4 urls in the response
    @Test(timeout = API_CALL_TIMEOUT_MS)
    public void testParserWithNullMp4() throws IOException, RuntimeException {
        URL giphyUrl = ParseUtils.getUrlObject("https://giphy.com/gifs/simpsons-l0HlD7sTICR75rDHy");
        ExpectedMedia expectedMedia = new ExpectedMedia.Builder().setIsVideo(true)
                .setPreviewUrl("https://media0.giphy.com/media/l0HlD7sTICR75rDHy/giphy_s.gif")
                .setHighQualityWidth(480)
                .setHighQualityHeight(366)
                .setHighQualityByteSize(0)
                .setHighQualityUrl("https://media4.giphy.com/media/l0HlD7sTICR75rDHy/giphy.mp4")
                .setLowQualityWidth(262)
                .setLowQualityHeight(200)
                .setLowQualityByteSize(0)
                .build();
        ExpectedParserResponse expectedParserResponse = new ExpectedParserResponse.Builder(giphyUrl)
                .setMedia(expectedMedia)
                .setIsSingleMedia(true)
                .build();
        ParserResponse parserResponse = mGiphyParser.parse(giphyUrl);
        compareParserResponse(giphyUrl, expectedParserResponse, parserResponse);
    }
}
