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

package com.fernandobarillas.albumparser.imgur;

import com.fernandobarillas.albumparser.exception.InvalidApiKeyException;
import com.fernandobarillas.albumparser.exception.InvalidMediaUrlException;
import com.fernandobarillas.albumparser.imgur.api.ImgurApi;
import com.fernandobarillas.albumparser.imgur.model.AlbumResponse;
import com.fernandobarillas.albumparser.imgur.model.Image;
import com.fernandobarillas.albumparser.imgur.model.v3.AlbumResponseV3;
import com.fernandobarillas.albumparser.imgur.model.v3.ImageResponseV3;
import com.fernandobarillas.albumparser.media.IMedia;
import com.fernandobarillas.albumparser.parser.AbstractApiParser;
import com.fernandobarillas.albumparser.parser.ParserResponse;
import com.fernandobarillas.albumparser.util.ParseUtils;

import java.io.IOException;
import java.net.URL;

import okhttp3.OkHttpClient;
import retrofit2.Response;

import static com.fernandobarillas.albumparser.vidble.VidbleUtils.isAlbum;

/**
 * Parser for the Imgur API
 */
public class ImgurParser extends AbstractApiParser {

    private static final int ALBUM_HASH_LENGTH = 5; // Most recent album hashes are exactly 5 chars
    private static final int IMAGE_HASH_LENGTH = 7; // Most recent image hashes are exactly 7 chars

    private String mImgurClientId = null;
    private String mPreviewSize;
    private String mLowQualitySize;

    public ImgurParser() {
    }

    public ImgurParser(OkHttpClient client) {
        super(client);
    }

    public ImgurParser(OkHttpClient client, String imgurClientId) {
        this(client);
        mImgurClientId = imgurClientId;
    }

    @Override
    public String getApiUrl() {
        return ImgurApi.API_URL;
    }

    @Override
    public String getBaseDomain() {
        return ImgurApi.BASE_DOMAIN;
    }

    @Override
    public String getHash(URL mediaUrl) throws InvalidMediaUrlException {
        if (!isValidDomain(mediaUrl)) {
            throw new InvalidMediaUrlException(
                    mediaUrl); // TODO: Add this instead of media == null check to other parsers
        }
        String path = mediaUrl.getPath();
        String hash;
        if (path.startsWith("/gallery/")) {
            // /gallery/{hash} URLs can contain both album and image hashes
            hash = ParseUtils.hashRegex(path, "^/gallery/([^\\W_]{5}|[^\\W_]{7})(?:/.*?|)$");
        } else if (path.startsWith("/a/")) {
            hash = ParseUtils.hashRegex(path, "^/a/([^\\W_]{5})(?:/.*?|)$");
        } else if (path.startsWith("/r/")) {
            hash = ParseUtils.hashRegex(path, "^/r/\\w+/([^\\W_]{5}|[^\\W_]{7})(?:/.*?|)$");
        } else {
            // Probably a gallery URL with no prefix
            hash = ParseUtils.hashRegex(path, "^/([^\\W_]{5}|[^\\W_]{7})(?:/.*?|)$");
        }

        // Check if this is a direct media URL
        if (hash == null && ParseUtils.isDirectUrl(mediaUrl)) {
            hash = ParseUtils.hashRegex(path,
                    "^/([^\\W_]{5}|[^\\W_]{7})[sbtmlghr]?\\.[^\\W_]{3,4}$");
        }

        if (hash == null) throw new InvalidMediaUrlException(mediaUrl);
        return hash;
    }

    @Override
    protected boolean isValidDomain(URL mediaUrl) {
        if (mediaUrl == null) return false;
        String domain = mediaUrl.getHost();
        String baseDomain = getBaseDomain();
        return baseDomain.equalsIgnoreCase(domain)
                || ("i." + baseDomain).equalsIgnoreCase(domain)
                || ("m." + baseDomain).equalsIgnoreCase(domain)
                || ("bildgur.de").equalsIgnoreCase(domain)
                || ("b.bildgur.de").equalsIgnoreCase(domain)
                || ("i.bildgur.de").equalsIgnoreCase(domain);
    }

    @Override
    public ParserResponse parse(URL mediaUrl) throws IOException, RuntimeException {
        String hash = getHash(mediaUrl);
        String clientIdHeader = null;
        if (mImgurClientId != null) {
            String apiKey = mImgurClientId.trim();
            if (apiKey.isEmpty()) {
                throw new InvalidApiKeyException(mediaUrl, apiKey,
                        "Imgur API key cannot be blank. Please set the key to null to not use the v3 API");
            }
            clientIdHeader = ImgurApi.CLIENT_ID_HEADER_PREFIX + " " + mImgurClientId;
        }

        boolean hasApiKey = clientIdHeader != null;
        boolean isAlbum = hash.length() == ALBUM_HASH_LENGTH;
        boolean isOldUrl = isAlbum && ParseUtils.isDirectUrl(mediaUrl); // Direct URL, 5 char hash

        // Not an album, no API key set or this is a pre-API direct URL
        if (!(isAlbum || hasApiKey) || isOldUrl) {
            // Generate a new image object for the hash we got without making an API call at all.
            // The extension is only guessed at if the original extension was null, so even though
            // you might make a request for {hash}.jpg the Imgur servers might still return a GIF
            // in the response
            Image image = new Image();
            String ext = ParseUtils.getExtension(mediaUrl);
            if (ext != null) {
                image.ext = "." + ext;
            }
            image.hash = hash;
            image.animated =
                    ParseUtils.isVideoExtension(mediaUrl) || ParseUtils.isGifExtension(mediaUrl);
            image.setLowQuality(mLowQualitySize);
            image.setPreviewQuality(mPreviewSize);
            ParserResponse parserResponse = new ParserResponse(image);
            parserResponse.setOriginalUrl(mediaUrl);
            return parserResponse;
        }

        ImgurApi service = getRetrofit().create(ImgurApi.class);
        if (isAlbum) {
            if (hasApiKey) {
                // Use API v3 if the client ID is set
                Response<AlbumResponseV3> serviceResponse =
                        service.getV3Album(clientIdHeader, hash).execute();
                AlbumResponseV3 apiResponse = serviceResponse.body();
                if (apiResponse != null) {
                    apiResponse.setLowQuality(mLowQualitySize);
                    apiResponse.setPreviewQuality(mPreviewSize);
                }
                return getParserResponse(mediaUrl, apiResponse);
            }

            // Make an API call to get the album images via the old API
            Response<AlbumResponse> serviceResponse = service.getAlbumData(hash).execute();
            AlbumResponse apiResponse = serviceResponse.body();
            if (apiResponse != null) {
                apiResponse.setLowQuality(mLowQualitySize);
                apiResponse.setPreviewQuality(mPreviewSize);
            }
            return getParserResponse(mediaUrl, apiResponse);
        } else {
            // Use API v3 if the client ID is set
            Response<ImageResponseV3> serviceResponse =
                    service.getV3Image(clientIdHeader, hash).execute();
            ImageResponseV3 apiResponse = serviceResponse.body();
            if (apiResponse != null) {
                apiResponse.setLowQuality(mLowQualitySize);
                apiResponse.setPreviewQuality(mPreviewSize);
            }
            return getParserResponse(mediaUrl, apiResponse);
        }
    }

    /**
     * Attempts to return a direct link to an image based on a hash alone, without doing an HTTP
     * call to the Imgur API. This method might return an invalid URL since it attempts to make an
     * educated guess at a URL. Some problematic hashes are for URLs that predate the Imgur API
     *
     * @param hash      The hash to get an image URL for
     * @param extension The extension to use for the URL. This should not have a prefixed period,
     *                  Example: jpg not .jpg
     * @return A URL to an image if the passed in hash was a valid non-album hash, null otherwise;
     */
    public static String getImageUrl(String hash, String quality, String extension) {
        String newExt = (extension == null) ? IMedia.EXT_JPG : extension;
        if (hash == null) return null;
        if (hash.length() <= ALBUM_HASH_LENGTH) return null;
        if (isAlbum(hash)) return null;
        return String.format("%s/%s%s.%s", ImgurApi.IMAGE_URL, hash, quality, newExt);
    }


    /**
     * Attempts to return a direct link to an image based on a hash alone, without doing an HTTP
     * call to the Imgur API. This method might return an invalid URL since it attempts to make an
     * educated guess at a URL. Some problematic hashes are for URLs that predate the Imgur API
     *
     * @param hash The hash to get an image URL for
     * @return A URL to an image if the passed in hash was a valid non-album hash, null otherwise;
     */
    public static String getImageUrl(String hash) {
        return getImageUrl(hash, Image.ORIGINAL, null);
    }

    /**
     * Sets the default size of the low quality URL imgur returns
     *
     * @param lowQualitySize The default size of the low quality URL Imgur returns. Look at {@link
     *                       Image} for available sizes. Examples: {@link Image#HUGE_THUMBNAIL},
     *                       {@link Image#GIANT_THUMBNAIL}. Notice, setting this to {@link
     *                       Image#ORIGINAL} can return URLs that weren't intended, for example, an
     *                       mp4 instead of a .jpg
     */
    public void setLowQualitySize(String lowQualitySize) {
        mLowQualitySize = lowQualitySize;
    }

    /**
     * Sets the default size of the preview URL imgur returns
     *
     * @param previewSize The default size of the preview URL Imgur returns. Look at {@link Image}
     *                    for available sizes. Examples: {@link Image#BIG_SQUARE}, {@link
     *                    Image#MEDIUM_THUMBNAIL}. Notice, setting this to {@link Image#ORIGINAL}
     *                    can return URLs that weren't intended, for example, an mp4 instead of a
     *                    .jpg
     */
    public void setPreviewSize(String previewSize) {
        mPreviewSize = previewSize;
    }
}
