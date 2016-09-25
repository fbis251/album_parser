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
import com.fernandobarillas.albumparser.exception.InvalidApiResponseException;
import com.fernandobarillas.albumparser.imgur.ImgurParser;
import com.fernandobarillas.albumparser.imgur.model.Image;
import com.fernandobarillas.albumparser.media.IMedia;
import com.fernandobarillas.albumparser.model.ExpectedAlbum;
import com.fernandobarillas.albumparser.model.ExpectedMedia;
import com.fernandobarillas.albumparser.model.ExpectedParserResponse;
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
import static com.fernandobarillas.albumparser.AllTests.apiDomainValid;
import static com.fernandobarillas.albumparser.AllTests.compareParserResponse;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for the Imgur API parser
 */
public class ImgurParserTest implements IParserTest {
    private OkHttpClient mOkHttpClient;
    private ImgurParser  mImgurParser;
    private ImgurParser  mImgurParserNoApiKey;

    private String mImgurApiKey    = ApiKeys.IMGUR_API_KEY;
    private String mPreviewQuality = Image.SMALL_SQUARE;
    private String mLowQuality     = Image.MEDIUM_THUMBNAIL;

    public ImgurParserTest() {
        mOkHttpClient = new OkHttpClient();
        mImgurParser = new ImgurParser(mOkHttpClient, mImgurApiKey);
        mImgurParser.setLowQualitySize(mLowQuality);
        mImgurParser.setPreviewSize(mPreviewQuality);
        mImgurParserNoApiKey = new ImgurParser(mOkHttpClient, null);
        mImgurParserNoApiKey.setLowQualitySize(mLowQuality);
        mImgurParserNoApiKey.setPreviewSize(mPreviewQuality);
    }

    @Test(expected = InvalidApiResponseException.class, timeout = API_CALL_TIMEOUT_MS)
    @Override
    public void testApi404Error() throws IOException, RuntimeException {
        URL invalid404Url = ParseUtils.getUrlObject("https://imgur.com/a/a8sxH");
        assertNotNull(invalid404Url);
        mImgurParser.parse(invalid404Url);
    }

    @Test
    @Override
    public void testApiUsesHttps() {
        apiDomainValid(mImgurParser, "imgur.com", false);
    }

    @Test
    @Override
    public void testCanParseAndGetHash() {
        Map<String, String> validHashes = new HashMap<>();

        // Albums
        validHashes.put("VhGBD", "http://imgur.com/r/motivation/VhGBD"); // Album with /r/ prefix
        validHashes.put("WKauF", "https://imgur.com/gallery/WKauF"); // Album with gallery URL
        validHashes.put("cvehZ", "http://imgur.com/a/cvehZ"); // /a/ prefix album

        // Images
        validHashes.put("awsGf9p", "http://imgur.com/awsGf9p"); // No prefix image URL
        validHashes.put("sCjRLQG", "http://i.imgur.com/sCjRLQG.jpg?1"); // Direct url with param
        validHashes.put("PBTrqAA", "https://imgur.com/gallery/PBTrqAA"); // gallery prefix
        validHashes.put("mhcWa37", "http://imgur.com/gallery/mhcWa37/new"); // Extra path after hash
        validHashes.put("SWSteYm", "http://imgur.com/r/google/SWSteYm"); // /r/ prefix


        AllTests.validateCanParseAndHashes(mImgurParser, validHashes, false);
    }

    // Tests a direct GIF URL with no API call
    @Test
    public void testGifWithNoApiCall() throws IOException {
        URL imgurUrl = ParseUtils.getUrlObject("http://i.imgur.com/FJRVge0.gif");
        String hash = "FJRVge0";

        ExpectedMedia expectedMedia = getExpectedMediaBuilder(hash, true).build();
        ExpectedParserResponse expectedParserResponse = new ExpectedParserResponse.Builder(imgurUrl)
                .setIsSingleMedia(true)
                .setMedia(expectedMedia)
                .build();

        ParserResponse parserResponse = mImgurParserNoApiKey.parse(imgurUrl);
        compareParserResponse(imgurUrl, expectedParserResponse, parserResponse);
    }

    // Tests a direct GIF URL with an uppercase extension with no API call
    @Test
    public void testGifWithUppercaseExtension() throws IOException {
        URL imgurUrl = ParseUtils.getUrlObject("https://i.imgur.com/M1ZXzzn.GIF");
        String hash = "M1ZXzzn";
        ExpectedMedia expectedMedia = getExpectedMediaBuilder(hash, true).build();
        ExpectedParserResponse expectedParserResponse = new ExpectedParserResponse.Builder(imgurUrl)
                .setIsSingleMedia(true)
                .setMedia(expectedMedia)
                .build();

        ParserResponse parserResponse = mImgurParserNoApiKey.parse(imgurUrl);
        compareParserResponse(imgurUrl, expectedParserResponse, parserResponse);
    }

    // Tests a direct GIFV URL with no API call
    @Test
    public void testGifvWithNoApiCall() throws IOException {
        URL imgurUrl = ParseUtils.getUrlObject("http://i.imgur.com/aRadjBe.gifv");
        String hash = "aRadjBe";
        ExpectedMedia expectedMedia = getExpectedMediaBuilder(hash, true).build();
        ExpectedParserResponse expectedParserResponse = new ExpectedParserResponse.Builder(imgurUrl)
                .setIsSingleMedia(true)
                .setMedia(expectedMedia)
                .build();

        ParserResponse parserResponse = mImgurParserNoApiKey.parse(imgurUrl);
        compareParserResponse(imgurUrl, expectedParserResponse, parserResponse);
    }

    // Image URL returns 404 by API
    @Test(expected = InvalidApiResponseException.class, timeout = API_CALL_TIMEOUT_MS)
    public void testImageWith404Error() throws IOException, RuntimeException {
        URL albumUrl = ParseUtils.getUrlObject("http://i.imgur.com/P3Z2Wfx.jpg");
        assertNotNull(albumUrl);
        mImgurParser.parse(albumUrl);
    }

    // Tests a direct JPG thumbnail URL with no API call
    @Test
    public void testJpgThumbnailWithNoApiCall() throws IOException {
        URL imgurUrl = ParseUtils.getUrlObject("https://i.imgur.com/jIg2N6qb.jpg");
        String hash = "jIg2N6q";
        ExpectedMedia expectedMedia = getExpectedMediaBuilder(hash, false).build();
        ExpectedParserResponse expectedParserResponse = new ExpectedParserResponse.Builder(imgurUrl)
                .setIsSingleMedia(true)
                .setMedia(expectedMedia)
                .build();

        ParserResponse parserResponse = mImgurParserNoApiKey.parse(imgurUrl);
        compareParserResponse(imgurUrl, expectedParserResponse, parserResponse);
    }

    // Tests a direct JPG URL with no API call
    @Test
    public void testJpgWithNoApiCall() throws IOException {
        URL imgurUrl = ParseUtils.getUrlObject("https://i.imgur.com/zzaVA8m.jpg");
        String hash = "zzaVA8m";
        ExpectedMedia expectedMedia = getExpectedMediaBuilder(hash, false).build();
        ExpectedParserResponse expectedParserResponse = new ExpectedParserResponse.Builder(imgurUrl)
                .setIsSingleMedia(true)
                .setMedia(expectedMedia)
                .build();

        ParserResponse parserResponse = mImgurParserNoApiKey.parse(imgurUrl);
        compareParserResponse(imgurUrl, expectedParserResponse, parserResponse);
    }

    // Tests a standard album URL with the old API call
    @Test(timeout = API_CALL_TIMEOUT_MS)
    public void testOldApiAlbum() throws IOException, RuntimeException {
        URL albumUrl = ParseUtils.getUrlObject("http://imgur.com/a/NzCBe");
        ExpectedParserResponse expectedParserResponse =
                getV3AlbumExpectedParserResponse(albumUrl, false);
        ParserResponse parserResponse = mImgurParserNoApiKey.parse(albumUrl);
        compareParserResponse(albumUrl, expectedParserResponse, parserResponse);
    }

    // Tests a standard album URL using the v3 API
    @Test(timeout = API_CALL_TIMEOUT_MS)
    public void testV3Album() throws IOException, RuntimeException {
        URL albumUrl = ParseUtils.getUrlObject("http://imgur.com/a/NzCBe");
        ExpectedParserResponse expectedParserResponse =
                getV3AlbumExpectedParserResponse(albumUrl, true);
        ParserResponse parserResponse = mImgurParser.parse(albumUrl);
        compareParserResponse(albumUrl, expectedParserResponse, parserResponse);
    }

    // Tests an album with a /gallery URL using the v3 API
    @Test(timeout = API_CALL_TIMEOUT_MS)
    public void testV3AlbumWithGalleryUrl() throws IOException, RuntimeException {
        URL albumUrl = ParseUtils.getUrlObject("https://imgur.com/gallery/NzCBe");
        ExpectedParserResponse expectedParserResponse =
                getV3AlbumExpectedParserResponse(albumUrl, true);
        ParserResponse parserResponse = mImgurParser.parse(albumUrl);
        compareParserResponse(albumUrl, expectedParserResponse, parserResponse);
    }

    // Tests an image URL using the v3 API
    @Test(timeout = API_CALL_TIMEOUT_MS)
    public void testV3Image() throws IOException, RuntimeException {
        URL imageUrl = ParseUtils.getUrlObject("http://i.imgur.com/P3Z2WfX.jpg");
        ExpectedParserResponse expectedParserResponse = getV3ImageExpectedParserResponse(imageUrl);
        ParserResponse parserResponse = mImgurParser.parse(imageUrl);
        compareParserResponse(imageUrl, expectedParserResponse, parserResponse);
    }

    private ExpectedMedia.Builder getExpectedMediaBuilder(final String hash,
            final boolean isAnimated) {
        String expectedExtension = isAnimated ? ".mp4" : ".jpg";
        return new ExpectedMedia.Builder().setPreviewUrl(
                "https://i.imgur.com/" + hash + mPreviewQuality + ".jpg")
                .setHighQualityUrl("https://i.imgur.com/" + hash + expectedExtension)
                // No low quality URLs for animated results
                .setLowQualityUrl(
                        !isAnimated ? "https://i.imgur.com/" + hash + mLowQuality + ".jpg" : null)
                .setIsVideo(isAnimated);
    }

    private ExpectedMedia getFbLogoExpectedMedia() {
        return getExpectedMediaBuilder("P3Z2WfX", false).setHighQualityByteSize(4753)
                .setHighQualityWidth(256)
                .setHighQualityHeight(256)
                .setTitle("FB Logo")
                .setDescription("FB logo description")
                .build();
    }

    private ExpectedMedia getSpiralAnimationExpectedMedia(final boolean isV3ApiCall) {
        ExpectedMedia.Builder builder =
                getExpectedMediaBuilder("Siit59R", true).setHighQualityWidth(347)
                        .setHighQualityHeight(326)
                        .setTitle("Spiral Animation")
                        .setDescription("Spiral animation description");

        if (isV3ApiCall) {
            // Only the V3 API call supports MP4 file sizes
            builder = builder.setHighQualityByteSize(76557);
        }

        return builder.build();
    }

    private ExpectedParserResponse getV3AlbumExpectedParserResponse(final URL albumUrl,
            final boolean isV3ApiCall) {
        String albumPreviewUrl = "https://i.imgur.com/P3Z2WfX" + mPreviewQuality + ".jpg";
        List<IMedia> expectedMediaList = new ArrayList<>();
        expectedMediaList.add(getFbLogoExpectedMedia());
        expectedMediaList.add(getSpiralAnimationExpectedMedia(isV3ApiCall));

        ExpectedAlbum expectedAlbum = new ExpectedAlbum.Builder().setCount(2)
                .setPreviewUrl(albumPreviewUrl)
                .setAlbumMedia(expectedMediaList)
                .build();
        return new ExpectedParserResponse.Builder(albumUrl).setIsAlbum(true)
                .setMediaAlbum(expectedAlbum)
                .build();
    }

    private ExpectedParserResponse getV3ImageExpectedParserResponse(final URL imageUrl) {
        return new ExpectedParserResponse.Builder(imageUrl).setIsSingleMedia(true)
                .setMedia(getFbLogoExpectedMedia())
                .build();
    }
}
