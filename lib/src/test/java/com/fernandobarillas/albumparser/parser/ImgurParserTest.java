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
import com.fernandobarillas.albumparser.imgur.ImgurParser;
import com.fernandobarillas.albumparser.imgur.model.Image;
import com.fernandobarillas.albumparser.imgur.model.v3.AlbumResponseV3;
import com.fernandobarillas.albumparser.media.IMedia;
import com.fernandobarillas.albumparser.model.ExpectedAlbum;
import com.fernandobarillas.albumparser.model.ExpectedMedia;
import com.fernandobarillas.albumparser.model.ExpectedParserResponse;
import com.fernandobarillas.albumparser.util.ExpectedHash;
import com.fernandobarillas.albumparser.util.TestUtils;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.fernandobarillas.albumparser.util.ParseUtils.getUrlObject;
import static com.fernandobarillas.albumparser.util.TestUtils.API_CALL_TIMEOUT_MS;
import static com.fernandobarillas.albumparser.util.TestUtils.apiDomainValid;
import static com.fernandobarillas.albumparser.util.TestUtils.assertInvalidUrlsThrowException;
import static com.fernandobarillas.albumparser.util.TestUtils.compareParserResponse;
import static com.fernandobarillas.albumparser.util.TestUtils.validateCanParseAndHashes;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for the Imgur API parser
 */
public class ImgurParserTest implements IParserTest {
    private ImgurParser mImgurParser;
    private ImgurParser mImgurParserNoApiKey;

    private String mPreviewQuality = Image.SMALL_SQUARE;
    private String mLowQuality     = Image.MEDIUM_THUMBNAIL;

    public ImgurParserTest() {
        mImgurParser = new ImgurParser(TestUtils.getOkHttpClient(), ApiKeys.IMGUR_API_KEY);
        mImgurParser.setLowQualitySize(mLowQuality);
        mImgurParser.setPreviewSize(mPreviewQuality);
        mImgurParserNoApiKey = new ImgurParser(TestUtils.getOkHttpClient(), null);
        mImgurParserNoApiKey.setLowQualitySize(mLowQuality);
        mImgurParserNoApiKey.setPreviewSize(mPreviewQuality);
    }

    @Test(expected = InvalidApiResponseException.class, timeout = API_CALL_TIMEOUT_MS)
    @Override
    public void testApi404Error() throws IOException, RuntimeException {
        URL url = getUrlObject("https://imgur.com/a/abcdef");
        assertNotNull(url);
        mImgurParser.parse(url);
    }

    @Test
    @Override
    public void testApiUsesHttps() {
        apiDomainValid(mImgurParser, "imgur.com", false);
    }

    @Test
    @Override
    public void testCanParseAndGetHash() {
        List<ExpectedHash> validHashes = new ArrayList<>();

        // Valid sub-domains
        validHashes.add(new ExpectedHash("Htlsv6N", "http://m.imgur.com/r/aww/Htlsv6N")); // subreddit mobile URL
        validHashes.add(new ExpectedHash("Htlsv6N", "http://m.imgur.com/Htlsv6N")); // mobile url
        validHashes.add(new ExpectedHash("Htlsv6N", "http://i.imgur.com/Htlsv6N.jpg")); // i subdomain
        validHashes.add(new ExpectedHash("1w2MFRq", "http://b.Bildgur.de/1w2MFRq.png")); // bildgur domain

        // Albums
        validHashes.add(new ExpectedHash("rROMo", "http://imgur.com/rROMo")); // Album with no prefix
        validHashes.add(new ExpectedHash("VhGBD", "http://imgur.com/r/motivation/VhGBD")); // Album with /r/ prefix
        validHashes.add(new ExpectedHash("VhGBD", "http://imgur.com/t/motivation/VhGBD")); // Album with /t/ prefix
        validHashes.add(new ExpectedHash("WKauF", "https://imgur.com/gallery/WKauF")); // Album with gallery URL
        validHashes.add(new ExpectedHash("cvehZ", "http://www.imgur.com/a/cvehZ")); // /a/ prefix album, www domain
        validHashes.add(new ExpectedHash("cvehZ", "http://imgur.com/a/cvehZ")); // /a/ prefix album
        validHashes.add(new ExpectedHash("rROMo", "https://bildgur.de/a/rROMo")); // /a/ prefix album, bildgur domain
        validHashes.add(new ExpectedHash("zis2t", "http://imgur.com/r/diy/zis2t")); // /r/ prefix album

        // Images
        validHashes.add(new ExpectedHash("0MlEZ", "http://i.imgur.com/0MlEZ.jpg")); // Pre-API direct URL
        validHashes.add(new ExpectedHash("awsGf9p", "http://imgur.com/awsGf9p")); // No prefix image URL
        validHashes.add(new ExpectedHash("sCjRLQG", "http://i.imgur.com/sCjRLQG.jpg?1")); // Direct url with param
        validHashes.add(new ExpectedHash("sCjRLQG", "http://i.imgur.com//sCjRLQG.jpg")); // Direct url with extra slash
        validHashes.add(new ExpectedHash("PBTrqAA", "https://imgur.com/gallery/PBTrqAA")); // gallery prefix
        validHashes.add(new ExpectedHash("mhcWa37", "http://imgur.com/gallery/mhcWa37/new")); // Extra path after hash
        validHashes.add(new ExpectedHash("SWSteYm", "http://imgur.com/r/google/SWSteYm")); // /r/ prefix

        validHashes.add(new ExpectedHash("FGfBEqu", "https://bildgur.de/FGfBEqu.png")); // Direct bildgur url
        validHashes.add(new ExpectedHash("xvn42E1", "http://b.bildgur.de/xvn42E1.jpg")); // Direct bildgur url
        validHashes.add(new ExpectedHash("FGfBEqu", "https://bildgur.de/FGfBEqu")); // bildgur url
        validHashes.add(new ExpectedHash("1234567", "http://i.imgur.com/1234567_d.jpg")); // _d suffix

        // Synthetic URLs
        validHashes.add(new ExpectedHash("12345", "http://imgur.com/12345.jpg"));
        validHashes.add(new ExpectedHash("12345", "http://i.imgur.com/12345.gifv"));
        validHashes.add(new ExpectedHash("abcde", "http://i.imgur.com/abcde.png"));
        validHashes.add(new ExpectedHash("1234567", "http://imgur.com/1234567.bmp"));
        validHashes.add(new ExpectedHash("1234567", "http://i.imgur.com/1234567.gif"));
        validHashes.add(new ExpectedHash("abcdefg", "http://i.imgur.com/abcdefg.jpg"));
        validHashes.add(new ExpectedHash("abcdefg", "http://i.imgur.com/abcdefg.jpg/"));

        // Valid image variation suffixes
        validHashes.add(new ExpectedHash("1234567", "http://i.imgur.com/1234567s.jpg"));
        validHashes.add(new ExpectedHash("1234567", "http://i.imgur.com/1234567b.jpg"));
        validHashes.add(new ExpectedHash("1234567", "http://i.imgur.com/1234567t.jpg"));
        validHashes.add(new ExpectedHash("1234567", "http://i.imgur.com/1234567m.jpg"));
        validHashes.add(new ExpectedHash("1234567", "http://i.imgur.com/1234567l.jpg"));
        validHashes.add(new ExpectedHash("1234567", "http://i.imgur.com/1234567g.jpg"));
        validHashes.add(new ExpectedHash("1234567", "http://i.imgur.com/1234567h.jpg"));
        validHashes.add(new ExpectedHash("1234567", "http://i.imgur.com/1234567r.jpg"));

        validHashes.add(new ExpectedHash("12345", "http://imgur.com/12345"));
        validHashes.add(new ExpectedHash("abcde", "http://imgur.com/abcde"));
        validHashes.add(new ExpectedHash("1234567", "http://imgur.com/1234567"));
        validHashes.add(new ExpectedHash("abcdefg", "http://imgur.com/abcdefg"));

        validHashes.add(new ExpectedHash("12345", "http://imgur.com/gallery/12345"));
        validHashes.add(new ExpectedHash("abcde", "http://imgur.com/gallery/abcde"));
        validHashes.add(new ExpectedHash("1234567", "http://imgur.com/1234567"));
        validHashes.add(new ExpectedHash("abcdefg", "http://imgur.com/abcdefg"));
        validHashes.add(new ExpectedHash("1234567", "http://imgur.com/gallery/1234567"));
        validHashes.add(new ExpectedHash("abcdefg", "http://imgur.com/gallery/abcdefg"));
        validHashes.add(new ExpectedHash("1234567", "http://imgur.com/a/1234567"));
        validHashes.add(new ExpectedHash("abcdefg", "http://imgur.com/a/abcdefg"));

        validHashes.add(new ExpectedHash("12345", "http://imgur.com/a/test/12345"));
        validHashes.add(new ExpectedHash("abcde", "http://imgur.com/a/test/abcde"));

        validHashes.add(new ExpectedHash("12345", "http://imgur.com/r/test/12345"));
        validHashes.add(new ExpectedHash("abcde", "http://imgur.com/r/test/abcde"));
        validHashes.add(new ExpectedHash("1234567", "http://imgur.com/r/test/1234567"));
        validHashes.add(new ExpectedHash("abcdefg", "http://imgur.com/r/test/abcdefg"));
        validHashes.add(new ExpectedHash("wi3Sl", "http://i.stack.imgur.com/wi3Sl.jpg"));

        // Real urls
        validHashes.add(new ExpectedHash("wspUqCv", "https://imgur.com/wspUqCv")); // Single image
        validHashes.add(new ExpectedHash("3OMFeBx", "https://imgur.com/gallery/3OMFeBx")); // Album
        validHashes.add(new ExpectedHash("3OMFeBx", "https://imgur.com/a/3OMFeBx")); // Album
        validHashes.add(new ExpectedHash("mPqzVMZ", "https://imgur.com/gallery/mPqzVMZ")); // Single image

        validateCanParseAndHashes(mImgurParser, validHashes, false);
    }

    @Test
    @Override
    public void testInvalidUrls() {
        Set<String> invalidUrls = new HashSet<>();

        // Invalid domains
        invalidUrls.add("https://username.imgur.com");

        invalidUrls.add("https://imgur.com/a/1234");
        invalidUrls.add("https://imgur.com/a/1234_");
        invalidUrls.add("https://imgur.com/a/12345678");

        invalidUrls.add("https://imgur.com/gallery/1234");
        invalidUrls.add("https://imgur.com/gallery/1234_");
        invalidUrls.add("https://imgur.com/gallery/123456");
        invalidUrls.add("https://imgur.com/gallery/123456_");
        invalidUrls.add("https://imgur.com/gallery/12345678");

        invalidUrls.add("https://imgur.com/");
        invalidUrls.add("https://imgur.com/1234");
        invalidUrls.add("https://imgur.com/1234_");
        invalidUrls.add("https://imgur.com/123456");
        invalidUrls.add("https://imgur.com/123456_");
        invalidUrls.add("https://imgur.com/12345678");
        invalidUrls.add("https://imgur.com/signin");
        invalidUrls.add("https://imgur.com/register");

        invalidUrls.add("https://imgur.com/r/gifs");
        invalidUrls.add("https://imgur.com/r/gifs/1234");
        invalidUrls.add("https://imgur.com/r/gifs/1234_");
        invalidUrls.add("https://imgur.com/r/gifs/123456");
        invalidUrls.add("https://imgur.com/r/gifs/123456_");
        invalidUrls.add("https://imgur.com/r/gifs/12345678");

        invalidUrls.add("http://i.imgur.com/1234.gifv");
        invalidUrls.add("http://i.imgur.com/1234_.gifv");
        invalidUrls.add("http://i.imgur.com/123456.gifv");
        invalidUrls.add("http://i.imgur.com/123456_.gifv");
        invalidUrls.add("http://i.imgur.com/1234567_.gifv");
        invalidUrls.add("http://i.imgur.com/12345678.gifv");
        invalidUrls.add("http://i.imgur.com/1234567a.gifv");
        invalidUrls.add("http://i.imgur.com/12345678_.gifv");

        invalidUrls.add("http://store.imgur.com/1234567");
        invalidUrls.add("http://help.imgur.com/12345");

        invalidUrls.add("http://bildgur.de");
        invalidUrls.add("http://bildgur.de/");
        invalidUrls.add("http://b.bildgur.de");
        invalidUrls.add("http://b.bildgur.de/");
        invalidUrls.add("http://i.bildgur.de");
        invalidUrls.add("http://i.bildgur.de/");

        assertInvalidUrlsThrowException(mImgurParser, invalidUrls);
    }

    // Tests a standard album URL with the old API call
    @Test(timeout = API_CALL_TIMEOUT_MS)
    public void testAlbumWithGalleryUrl() throws IOException, RuntimeException {
        URL url = getUrlObject("https://imgur.com/gallery/3OMFeBx");
        String albumPreviewUrl = "https://i.imgur.com/wspUqCv" + mPreviewQuality + ".png";

        ExpectedMedia image1 = new ExpectedMedia.Builder()
                .setHighQualityUrl("https://i.imgur.com/wspUqCv.png")
                .setLowQualityUrl("https://i.imgur.com/wspUqCvm.png")
                .setPreviewUrl("https://i.imgur.com/wspUqCvs.png")
                .setHighQualityByteSize(89154)
                .setHighQualityWidth(268)
                .setHighQualityHeight(315)
                .setDescription("The Simpsons Family")
                .build();
        ExpectedMedia image2 = new ExpectedMedia.Builder()
                .setHighQualityUrl("https://i.imgur.com/grlYZVG.png")
                .setLowQualityUrl("https://i.imgur.com/grlYZVGm.png")
                .setPreviewUrl("https://i.imgur.com/grlYZVGs.png")
                .setHighQualityByteSize(193905)
                .setHighQualityWidth(533)
                .setHighQualityHeight(187)
                .setDescription("Simpsons Characters")
                .build();
        List<IMedia> expectedMediaList = new ArrayList<>();
        expectedMediaList.add(image1);
        expectedMediaList.add(image2);

        ExpectedAlbum expectedAlbum = new ExpectedAlbum.Builder()
                .setCount(2)
                .setPreviewUrl(albumPreviewUrl)
                .setAlbumMedia(expectedMediaList)
                .build();

        ExpectedParserResponse expectedParserResponse =
                new ExpectedParserResponse.Builder(url).setIsAlbum(true).setMediaAlbum(expectedAlbum).build();
        ParserResponse parserResponse = mImgurParser.parse(url);
        compareParserResponse(url, expectedParserResponse, parserResponse);
    }

    // Tests a direct GIF URL with no API call
    @Test
    public void testGifWithNoApiCall() throws IOException {
        URL url = getUrlObject("http://i.imgur.com/FJRVge0.gif");
        String hash = "FJRVge0";

        ExpectedMedia expectedMedia = getExpectedMediaBuilder(hash, true).build();
        ExpectedParserResponse expectedParserResponse =
                new ExpectedParserResponse.Builder(url).setIsSingleMedia(true).setMedia(expectedMedia).build();

        ParserResponse parserResponse = mImgurParserNoApiKey.parse(url);
        compareParserResponse(url, expectedParserResponse, parserResponse);
    }

    // Tests a direct GIF URL with an uppercase extension with no API call
    @Test
    public void testGifWithUppercaseExtension() throws IOException {
        URL url = getUrlObject("https://i.imgur.com/M1ZXzzn.GIF");
        String hash = "M1ZXzzn";
        ExpectedMedia expectedMedia = getExpectedMediaBuilder(hash, true).build();
        ExpectedParserResponse expectedParserResponse =
                new ExpectedParserResponse.Builder(url).setIsSingleMedia(true).setMedia(expectedMedia).build();

        ParserResponse parserResponse = mImgurParserNoApiKey.parse(url);
        compareParserResponse(url, expectedParserResponse, parserResponse);
    }

    // Tests a direct GIFV URL with no API call
    @Test
    public void testGifvWithNoApiCall() throws IOException {
        URL url = getUrlObject("http://i.imgur.com/aRadjBe.gifv");
        String hash = "aRadjBe";
        ExpectedMedia expectedMedia = getExpectedMediaBuilder(hash, true).build();
        ExpectedParserResponse expectedParserResponse =
                new ExpectedParserResponse.Builder(url).setIsSingleMedia(true).setMedia(expectedMedia).build();

        ParserResponse parserResponse = mImgurParserNoApiKey.parse(url);
        compareParserResponse(url, expectedParserResponse, parserResponse);
    }

    // Image URL returns 404 by API
    @Test(expected = InvalidApiResponseException.class, timeout = API_CALL_TIMEOUT_MS)
    public void testImageWith404Error() throws IOException, RuntimeException {
        URL url = getUrlObject("http://i.imgur.com/P3Z2Wfx.jpg");
        assertNotNull(url);
        mImgurParser.parse(url);
    }

    // Tests a direct JPG thumbnail URL with no API call
    @Test
    public void testJpgThumbnailWithNoApiCall() throws IOException {
        URL url = getUrlObject("https://i.imgur.com/jIg2N6qb.jpg");
        String hash = "jIg2N6q";
        ExpectedMedia expectedMedia = getExpectedMediaBuilder(hash, false).build();
        ExpectedParserResponse expectedParserResponse =
                new ExpectedParserResponse.Builder(url).setIsSingleMedia(true).setMedia(expectedMedia).build();

        ParserResponse parserResponse = mImgurParserNoApiKey.parse(url);
        compareParserResponse(url, expectedParserResponse, parserResponse);
    }

    // Tests a direct JPG URL with no API call
    @Test
    public void testJpgWithNoApiCall() throws IOException {
        URL url = getUrlObject("https://i.imgur.com/zzaVA8m.jpg");
        String hash = "zzaVA8m";
        ExpectedMedia expectedMedia = getExpectedMediaBuilder(hash, false).build();
        ExpectedParserResponse expectedParserResponse =
                new ExpectedParserResponse.Builder(url).setIsSingleMedia(true).setMedia(expectedMedia).build();

        ParserResponse parserResponse = mImgurParserNoApiKey.parse(url);
        compareParserResponse(url, expectedParserResponse, parserResponse);
    }

    @Test
    public void testNullJsonFields() throws IOException {
        String json =
                "{\"data\":{\"id\":null,\"title\":null,\"description\":null,\"datetime\":null,\"cover\":null,\"cover_width\":null,\"cover_height\":null,\"account_url\":null,\"account_id\":null,\"privacy\":null,\"layout\":null,\"views\":null,\"link\":null,\"favorite\":null,\"nsfw\":null,\"section\":null,\"images_count\":null,\"in_gallery\":null,\"is_ad\":null,\"images\":[{\"id\":null,\"title\":null,\"description\":null,\"datetime\":null,\"type\":null,\"animated\":null,\"width\":null,\"height\":null,\"size\":null,\"views\":null,\"bandwidth\":null,\"vote\":null,\"favorite\":null,\"nsfw\":null,\"section\":null,\"account_url\":null,\"account_id\":null,\"is_ad\":null,\"in_most_viral\":null,\"tags\":null,\"ad_type\":null,\"ad_url\":null,\"in_gallery\":null,\"link\":null},{\"id\":null,\"title\":null,\"description\":null,\"datetime\":null,\"type\":null,\"animated\":null,\"width\":null,\"height\":null,\"size\":null,\"views\":null,\"bandwidth\":null,\"vote\":null,\"favorite\":null,\"nsfw\":null,\"section\":null,\"account_url\":null,\"account_id\":null,\"is_ad\":null,\"in_most_viral\":null,\"tags\":null,\"ad_type\":null,\"ad_url\":null,\"in_gallery\":null,\"mp4\":null,\"gifv\":null,\"mp4_size\":null,\"link\":null,\"looping\":null}]},\"success\":null,\"status\":null}";
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<AlbumResponseV3> jsonAdapter = moshi.adapter(AlbumResponseV3.class);
        TestUtils.checkNullJson(moshi, jsonAdapter, json);
    }

    // Tests a standard album URL with the old API call
    @Test(timeout = API_CALL_TIMEOUT_MS)
    public void testOldApiAlbum() throws IOException, RuntimeException {
        URL url = getUrlObject("http://imgur.com/a/NzCBe");
        ExpectedParserResponse expectedParserResponse = getV3AlbumExpectedParserResponse(url, false);

        ParserResponse parserResponse = mImgurParserNoApiKey.parse(url);
        compareParserResponse(url, expectedParserResponse, parserResponse);
    }

    // Tests a direct JPG URL with 5 char hash with no API call
    @Test
    public void testOldFormatJpgWithNoApiCall() throws IOException {
        URL url = getUrlObject("http://i.imgur.com/0MlEZ.jpg");
        String hash = "0MlEZ";
        ExpectedMedia expectedMedia = getExpectedMediaBuilder(hash, false).build();
        ExpectedParserResponse expectedParserResponse =
                new ExpectedParserResponse.Builder(url).setIsSingleMedia(true).setMedia(expectedMedia).build();

        ParserResponse parserResponse = mImgurParserNoApiKey.parse(url);
        compareParserResponse(url, expectedParserResponse, parserResponse);
    }

    // Tests a standard album URL using the v3 API
    @Test(timeout = API_CALL_TIMEOUT_MS)
    public void testV3Album() throws IOException, RuntimeException {
        URL url = getUrlObject("http://imgur.com/a/NzCBe");
        ExpectedParserResponse expectedParserResponse = getV3AlbumExpectedParserResponse(url, true);
        ParserResponse parserResponse = mImgurParser.parse(url);
        compareParserResponse(url, expectedParserResponse, parserResponse);
    }

    // Tests a standard album URL with the old API call
    @Test(timeout = API_CALL_TIMEOUT_MS)
    public void testV3AlbumNew() throws IOException, RuntimeException {
        URL url = getUrlObject("http://imgur.com/a/NzCBe");
        ExpectedParserResponse expectedParserResponse = getV3AlbumExpectedParserResponse(url, false);
        ParserResponse parserResponse = mImgurParserNoApiKey.parse(url);
        compareParserResponse(url, expectedParserResponse, parserResponse);
    }

    // Tests an album with a /gallery URL using the v3 API
    @Test(timeout = API_CALL_TIMEOUT_MS)
    public void testV3AlbumWithGalleryUrl() throws IOException, RuntimeException {
        URL url = getUrlObject("https://imgur.com/gallery/NzCBe");
        ExpectedParserResponse expectedParserResponse = getV3AlbumExpectedParserResponse(url, true);
        ParserResponse parserResponse = mImgurParser.parse(url);
        compareParserResponse(url, expectedParserResponse, parserResponse);
    }

    // Tests an image URL using the v3 API
    @Test(timeout = API_CALL_TIMEOUT_MS)
    public void testV3Image() throws IOException, RuntimeException {
        URL url = getUrlObject("http://i.imgur.com/P3Z2WfX.jpg");
        ExpectedParserResponse expectedParserResponse = getV3ImageExpectedParserResponse(url);
        ParserResponse parserResponse = mImgurParser.parse(url);
        compareParserResponse(url, expectedParserResponse, parserResponse);
    }

    // Tests an image URL (with a short album-length hash) using the v3 API
    @Test(timeout = API_CALL_TIMEOUT_MS)
    public void testV3ImageWithShortHash() throws IOException, RuntimeException {
        URL url = getUrlObject("https://imgur.com/0MlEZ");
        ExpectedMedia expectedImage = getExpectedMediaBuilder("0MlEZ", false)
                .setHighQualityByteSize(126099)
                .setHighQualityWidth(553)
                .setHighQualityHeight(833)
                .build();

        ExpectedParserResponse expectedParserResponse =
                new ExpectedParserResponse.Builder(url).setIsSingleMedia(true).setMedia(expectedImage).build();

        ParserResponse parserResponse = mImgurParser.parse(url);
        compareParserResponse(url, expectedParserResponse, parserResponse);
    }

    private ExpectedMedia.Builder getExpectedMediaBuilder(final String hash, final boolean isAnimated) {
        String expectedExtension = isAnimated ? ".mp4" : ".jpg";
        return new ExpectedMedia.Builder()
                .setPreviewUrl("https://i.imgur.com/" + hash + mPreviewQuality + ".jpg")
                .setHighQualityUrl("https://i.imgur.com/" + hash + expectedExtension)
                // No low quality URLs for animated results
                .setLowQualityUrl(!isAnimated ? "https://i.imgur.com/" + hash + mLowQuality + ".jpg" : null)
                .setIsVideo(isAnimated);
    }

    private ExpectedMedia getFbLogoExpectedMedia() {
        return getExpectedMediaBuilder("P3Z2WfX", false)
                .setHighQualityByteSize(4753)
                .setHighQualityWidth(256)
                .setHighQualityHeight(256)
                .setTitle("FB Logo")
                .setDescription("FB logo description")
                .setHighQualityByteSize(4753)
                .build();
    }

    private ExpectedMedia getSpiralAnimationExpectedMedia(final boolean isV3ApiCall) {
        ExpectedMedia.Builder builder = getExpectedMediaBuilder("Siit59R", true)
                .setHighQualityWidth(347)
                .setHighQualityHeight(326)
                .setTitle("Spiral Animation")
                .setDescription("Spiral animation description");

        if (isV3ApiCall) {
            // Only the V3 API call supports MP4 file sizes
            builder = builder.setHighQualityByteSize(76557);
        }

        return builder.build();
    }

    private ExpectedParserResponse getV3AlbumExpectedParserResponse(final URL albumUrl, final boolean isV3ApiCall) {
        String albumPreviewUrl = "https://i.imgur.com/P3Z2WfX" + mPreviewQuality + ".jpg";
        List<IMedia> expectedMediaList = new ArrayList<>();
        expectedMediaList.add(getFbLogoExpectedMedia());
        expectedMediaList.add(getSpiralAnimationExpectedMedia(isV3ApiCall));

        ExpectedAlbum expectedAlbum = new ExpectedAlbum.Builder()
                .setCount(2)
                .setPreviewUrl(albumPreviewUrl)
                .setAlbumMedia(expectedMediaList)
                .build();
        return new ExpectedParserResponse.Builder(albumUrl).setIsAlbum(true).setMediaAlbum(expectedAlbum).build();
    }

    private ExpectedParserResponse getV3ImageExpectedParserResponse(final URL imageUrl) {
        return new ExpectedParserResponse.Builder(imageUrl)
                .setIsSingleMedia(true)
                .setMedia(getFbLogoExpectedMedia())
                .build();
    }
}
