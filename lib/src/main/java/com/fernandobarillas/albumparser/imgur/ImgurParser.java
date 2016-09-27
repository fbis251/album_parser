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
import com.fernandobarillas.albumparser.parser.AbstractApiParser;
import com.fernandobarillas.albumparser.parser.ParserResponse;
import com.fernandobarillas.albumparser.util.ParseUtils;

import java.io.IOException;
import java.net.URL;

import okhttp3.OkHttpClient;
import retrofit2.Response;

/**
 * Parser for the Imgur API
 */
public class ImgurParser extends AbstractApiParser {
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

        ImgurApi service = getRetrofit().create(ImgurApi.class);
        if (ImgurUtils.isAlbum(hash)) {
            if (clientIdHeader != null) {
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
            if (clientIdHeader != null) {
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

            // Last ditch effort, generate a new image object for the hash we got without making
            // an API call at all. The extension is only guessed at if the original extension was
            // null, so even though you might make a request for {hash}.jpg the Imgur servers might
            // still return a GIF in the response
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
