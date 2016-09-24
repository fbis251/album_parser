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

import com.fernandobarillas.albumparser.exception.InvalidMediaUrlException;
import com.fernandobarillas.albumparser.imgur.api.ImgurApi;
import com.fernandobarillas.albumparser.imgur.model.Image;
import com.fernandobarillas.albumparser.imgur.model.ImgurResponse;
import com.fernandobarillas.albumparser.imgur.model.v3.AlbumResponse;
import com.fernandobarillas.albumparser.imgur.model.v3.ImageResponse;
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

    public ImgurParser(OkHttpClient client, String imgurClientId) {
        this(client);
        mImgurClientId = imgurClientId;
    }

    public ImgurParser(OkHttpClient client) {
        super(client);
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
            hash = ParseUtils.hashRegex(path, "/gallery/(\\w{5,7})");
        } else if (path.startsWith("/a/")) {
            hash = ParseUtils.hashRegex(path, "/a/(\\w{5})");
        } else if (path.startsWith("/r/")) {
            hash = ParseUtils.hashRegex(path, "/r/\\w+/(\\w{5,7})");
        } else {
            // Probably a gallery URL with no prefix
            hash = ParseUtils.hashRegex(path, "/(\\w{7})");
        }
        if (hash == null) throw new InvalidMediaUrlException(mediaUrl);
        return hash;
    }

    @Override
    public ParserResponse parse(URL mediaUrl) throws IOException, RuntimeException {
        String hash = getHash(mediaUrl);
        String clientIdHeader = null;
        if (mImgurClientId != null) {
            clientIdHeader = ImgurApi.CLIENT_ID_HEADER_PREFIX + " " + mImgurClientId;
        }

        ImgurApi service = getRetrofit().create(ImgurApi.class);
        if (ImgurUtils.isAlbum(hash)) {
            if (clientIdHeader != null) {
                // Use API v3 if the client ID is set
                Response<AlbumResponse> serviceResponse =
                        service.getV3Album(clientIdHeader, hash).execute();
                AlbumResponse apiResponse = serviceResponse.body();
                return getParserResponse(mediaUrl, apiResponse);
            }

            // Make an API call to get the album images via the old API
            Response<ImgurResponse> serviceResponse = service.getAlbumData(hash).execute();
            ImgurResponse apiResponse = serviceResponse.body();
            return getParserResponse(mediaUrl, apiResponse);
        } else {
            if (clientIdHeader != null) {
                // Use API v3 if the client ID is set
                Response<ImageResponse> serviceResponse =
                        service.getV3Image(clientIdHeader, hash).execute();
                ImageResponse apiResponse = serviceResponse.body();
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
            ParserResponse parserResponse = new ParserResponse(image);
            parserResponse.setOriginalUrl(mediaUrl);
            return parserResponse;
        }
    }
}
