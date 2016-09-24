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

import com.fernandobarillas.albumparser.AllTests;
import com.fernandobarillas.albumparser.ApiKeys;
import com.fernandobarillas.albumparser.exception.InvalidApiKeyException;
import com.fernandobarillas.albumparser.media.IMedia;
import com.fernandobarillas.albumparser.media.IMediaAlbum;
import com.fernandobarillas.albumparser.model.ExpectedAlbum;
import com.fernandobarillas.albumparser.model.ExpectedMedia;
import com.fernandobarillas.albumparser.model.ExpectedParserResponse;
import com.fernandobarillas.albumparser.tumblr.TumblrParser;
import com.fernandobarillas.albumparser.util.ParseUtils;

import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;

import static com.fernandobarillas.albumparser.AllTests.API_CALL_TIMEOUT_MS;
import static com.fernandobarillas.albumparser.AllTests.compareParserResponse;

/**
 * Tests for the Imgur API parser
 */
public class TumblrParserTest {
    private TumblrParser mTumblrParser;
    private String mTumblrApiKey = ApiKeys.TUMBLR_API_KEY;

    public TumblrParserTest() {
        mTumblrParser = new TumblrParser(new OkHttpClient(), mTumblrApiKey);
    }

    // Tests a standard album URL, contains 2 photos
    @Test(timeout = API_CALL_TIMEOUT_MS)
    public void testAlbumWithTwoPhotos() throws IOException, RuntimeException {

        URL albumUrl = ParseUtils.getUrlObject("http://fbis251.tumblr.com/post/150135750508/");
        String albumPreviewUrl =
                "https://67.media.tumblr.com/dbd5c1852e25468bd2e715cb88085178/tumblr_od7ef98S291r8k7mao1_400.jpg";
        List<IMedia> expectedMediaList = new ArrayList<>();
        expectedMediaList.add(new ExpectedMedia.Builder().setDescription("Photo 1 caption")
                .setPreviewUrl(
                        "https://67.media.tumblr.com/dbd5c1852e25468bd2e715cb88085178/tumblr_od7ef98S291r8k7mao1_400.jpg")
                .setHighQualityHeight(853)
                .setHighQualityWidth(1280)
                .setHighQualityUrl(
                        "https://67.media.tumblr.com/dbd5c1852e25468bd2e715cb88085178/tumblr_od7ef98S291r8k7mao1_1280.jpg")
                .setLowQualityUrl(
                        "https://66.media.tumblr.com/dbd5c1852e25468bd2e715cb88085178/tumblr_od7ef98S291r8k7mao1_500.jpg")
                .setLowQualityHeight(333)
                .setLowQualityWidth(500)
                .build());
        expectedMediaList.add(new ExpectedMedia.Builder().setDescription("Photo 2 caption")
                .setPreviewUrl(
                        "https://65.media.tumblr.com/b2c3d84a5d6f009b1886d56ac27cd8f6/tumblr_od7ef98S291r8k7mao2_400.jpg")
                .setHighQualityHeight(876)
                .setHighQualityWidth(1280)
                .setHighQualityUrl(
                        "https://66.media.tumblr.com/b2c3d84a5d6f009b1886d56ac27cd8f6/tumblr_od7ef98S291r8k7mao2_1280.jpg")
                .setLowQualityUrl(
                        "https://66.media.tumblr.com/b2c3d84a5d6f009b1886d56ac27cd8f6/tumblr_od7ef98S291r8k7mao2_500.jpg")
                .setLowQualityHeight(342)
                .setLowQualityWidth(500)
                .build());

        ExpectedAlbum expectedAlbum = new ExpectedAlbum.Builder().setCount(2)
                .setPreviewUrl(albumPreviewUrl)
                .setAlbumMedia(expectedMediaList)
                .build();

        ExpectedParserResponse expectedParserResponse = new ExpectedParserResponse.Builder(albumUrl)
                .setIsAlbum(true)
                .setMediaAlbum(expectedAlbum)
                .build();
        ParserResponse parserResponse = mTumblrParser.parse(albumUrl);
        compareParserResponse(albumUrl, expectedParserResponse, parserResponse);
    }

    @Test
    public void testCanParseAndGetHash() throws Exception {
        Map<String, String> validHashes = new HashMap<>();

        // /image/ prefix
        validHashes.put("150175958904", "http://bossrushstudio.tumblr.com/image/150175958904");
        // /post/ prefix with suffix
        validHashes.put("150140358556", "http://aatkaw.tumblr.com/post/150140358556/hana");
        // /post/ prefix
        validHashes.put("150135750508", "http://fbis251.tumblr.com/post/150135750508/");

        AllTests.validateCanParseAndHashes(mTumblrParser, validHashes, false);

        Map<String, String> validDirectUrls = new HashMap<>();
        // Direct media URL
        validDirectUrls.put(null,
                "https://67.media.tumblr.com/dbd5c1852e25468bd2e715cb88085178/tumblr_od7ef98S291r8k7mao1_540.jpg");
        AllTests.validateCanParseAndHashes(mTumblrParser, validDirectUrls, true);
    }

    @Test(expected = InvalidApiKeyException.class)
    public void testTumblrApiKeyNotSet() throws IOException {
        TumblrParser tumblrParser = new TumblrParser(null, null);
        tumblrParser.parse(null);
    }

    // Tests a video post
    @Test(timeout = API_CALL_TIMEOUT_MS)
    public void testVideoPost() throws IOException, RuntimeException {
        URL videoPostUrl = ParseUtils.getUrlObject("http://fbis251.tumblr.com/post/150800442120/");

        ArrayList<IMedia> expectedMedia = new ArrayList<>();
        expectedMedia.add(new ExpectedMedia.Builder().setDescription("<p>Up and Atom!</p>")
                .setPreviewUrl("https://31.media.tumblr.com/tumblr_odxrj1AuHO1r8k7ma_frame1.jpg")
                .setDuration(11)
                .setHighQualityUrl("https://vt.tumblr.com/tumblr_odxrj1AuHO1r8k7ma.mp4")
                .setIsVideo(true)
                .build());
        IMediaAlbum expectedAlbum = new ExpectedAlbum.Builder().setPreviewUrl(
                "https://31.media.tumblr.com/tumblr_odxrj1AuHO1r8k7ma_frame1.jpg")
                .setCount(1)
                .setAlbumMedia(expectedMedia)
                .build();

        ExpectedParserResponse expectedParserResponse =
                new ExpectedParserResponse.Builder(videoPostUrl).setIsAlbum(true)
                        .setMediaAlbum(expectedAlbum)
                        .build();

        ParserResponse parserResponse = mTumblrParser.parse(videoPostUrl);
        compareParserResponse(videoPostUrl, expectedParserResponse, parserResponse);
    }
}
