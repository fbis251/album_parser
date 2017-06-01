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

import com.fernandobarillas.albumparser.eroshare.EroshareParser;
import com.fernandobarillas.albumparser.media.IMedia;
import com.fernandobarillas.albumparser.model.ExpectedAlbum;
import com.fernandobarillas.albumparser.model.ExpectedMedia;
import com.fernandobarillas.albumparser.model.ExpectedParserResponse;
import com.fernandobarillas.albumparser.util.TestUtils;

import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fernandobarillas.albumparser.util.ParseUtils.getUrlObject;
import static com.fernandobarillas.albumparser.util.TestUtils.API_CALL_TIMEOUT_MS;
import static com.fernandobarillas.albumparser.util.TestUtils.compareParserResponse;
import static com.fernandobarillas.albumparser.util.TestUtils.validateCanParseAndHashes;

/**
 * Tests for Eroshare API Parser
 */
public class EroshareParserTest {

    private static final ExpectedMedia EXPECTED_IMAGE =
            new ExpectedMedia.Builder().setDescription("Homer Simpson Eating a Donut")
                    .setPreviewUrl("https://i.eroshare.com/wja22gm7_thumb.jpg")
                    .setHighQualityUrl("https://i.eroshare.com/wja22gm7_orig.png")
                    .setLowQualityUrl("https://i.eroshare.com/wja22gm7.jpg")
                    .setHighQualityHeight(391)
                    .setHighQualityWidth(239)
                    .build();
    private static final ExpectedMedia EXPECTED_VIDEO =
            new ExpectedMedia.Builder().setDescription("Up and Atom")
                    .setPreviewUrl("https://i.eroshare.com/gvensf3w_thumb.jpg")
                    .setHighQualityUrl("https://v.eroshare.com/gvensf3w.mp4")
                    .setLowQualityUrl("https://v.eroshare.com/gvensf3w_lowres.mp4")
                    .setHighQualityHeight(464)
                    .setHighQualityWidth(608)
                    .setDuration(11)
                    .setIsVideo(true)
                    .build();

    private EroshareParser mEroshareParser;

    public EroshareParserTest() {
        mEroshareParser = new EroshareParser(TestUtils.getOkHttpClient());
    }

    // Tests a standard album URL
    @Test(timeout = API_CALL_TIMEOUT_MS)
    public void testAlbum() throws IOException, RuntimeException {
        URL albumUrl = getUrlObject("https://eroshare.com/3fdr6v4a");
        String albumPreviewUrl = "https://i.eroshare.com/wja22gm7_thumb.jpg";
        List<IMedia> expectedMediaList = new ArrayList<>();
        expectedMediaList.add(EXPECTED_IMAGE);
        expectedMediaList.add(EXPECTED_VIDEO);
        expectedMediaList.add(new ExpectedMedia.Builder().setDescription("You're a Winner!")
                .setPreviewUrl("https://i.eroshare.com/0mvd0dp7_thumb.jpg")
                .setHighQualityUrl("https://v.eroshare.com/0mvd0dp7.mp4")
                .setLowQualityUrl("https://v.eroshare.com/0mvd0dp7_lowres.mp4")
                .setHighQualityHeight(384)
                .setHighQualityWidth(512)
                .setDuration(4)
                .setIsVideo(true)
                .build());
        ExpectedAlbum expectedAlbum = new ExpectedAlbum.Builder().setCount(3)
                .setPreviewUrl(albumPreviewUrl)
                .setAlbumMedia(expectedMediaList)
                .build();
        ExpectedParserResponse expectedParserResponse = new ExpectedParserResponse.Builder(albumUrl)
                .setIsAlbum(true)
                .setMediaAlbum(expectedAlbum)
                .build();
        ParserResponse parserResponse = mEroshareParser.parse(albumUrl);
        compareParserResponse(albumUrl, expectedParserResponse, parserResponse);
    }

    @Test
    public void testCanParse() {
        Map<String, String> validHashes = new HashMap<>();

        // Valid sub-domains
        validHashes.put("3fdr6v4a", "https://eroshare.com/3fdr6v4a"); // album url
        validHashes.put("3fdr6v4a", "https://eroshare.com/3fdr6v4a/"); // album url
        validHashes.put("wja22gm7", "https://eroshare.com/i/wja22gm7"); // item url
        validHashes.put("wja22gm7", "https://eroshare.com/i/wja22gm7/"); // item url
        validHashes.put("0mvd0dp7", "https://v.eroshare.com/0mvd0dp7.mp4"); // mobile url
        validHashes.put("wja22gm7", "https://i.eroshare.com/wja22gm7.jpg"); // i subdomain

        validateCanParseAndHashes(mEroshareParser, validHashes, false);
    }

    // Tests a single image URL
    @Test(timeout = API_CALL_TIMEOUT_MS)
    public void testImageItem() throws IOException, RuntimeException {
        URL imageUrl = getUrlObject("https://eroshare.com/i/wja22gm7/");
        ExpectedParserResponse expectedParserResponse = new ExpectedParserResponse.Builder(imageUrl)
                .setIsSingleMedia(true)
                .setMedia(EXPECTED_IMAGE)
                .build();
        ParserResponse parserResponse = mEroshareParser.parse(imageUrl);
        compareParserResponse(imageUrl, expectedParserResponse, parserResponse);
    }

    // Tests a single image URL
    @Test(timeout = API_CALL_TIMEOUT_MS)
    public void testImageItemDirectUrl() throws IOException, RuntimeException {
        URL imageUrl = getUrlObject("https://i.eroshare.com/wja22gm7.jpg");
        ExpectedParserResponse expectedParserResponse = new ExpectedParserResponse.Builder(imageUrl)
                .setIsSingleMedia(true)
                .setMedia(EXPECTED_IMAGE)
                .build();
        ParserResponse parserResponse = mEroshareParser.parse(imageUrl);
        compareParserResponse(imageUrl, expectedParserResponse, parserResponse);
    }

    // Tests a single video URL
    @Test(timeout = API_CALL_TIMEOUT_MS)
    public void testVideoItem() throws IOException, RuntimeException {
        URL videoUrl = getUrlObject("https://v.eroshare.com/gvensf3w.mp4");
        ExpectedParserResponse expectedParserResponse = new ExpectedParserResponse.Builder(videoUrl)
                .setIsSingleMedia(true)
                .setMedia(EXPECTED_VIDEO)
                .build();
        ParserResponse parserResponse = mEroshareParser.parse(videoUrl);
        compareParserResponse(videoUrl, expectedParserResponse, parserResponse);
    }
}
