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
 * Parser for Imgur API responses
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
    public ParserResponse parse(URL mediaUrl) throws InvalidMediaUrlException, IOException {
        String hash = ImgurUtils.getHash(mediaUrl.toString());
        if (hash == null) {
            throw new InvalidMediaUrlException(mediaUrl);
        }

        String clientIdHeader = null;
        if (mImgurClientId != null) {
            clientIdHeader = ImgurApi.CLIENT_ID_HEADER_PREFIX + " " + mImgurClientId;
        }

        ImgurApi service = getRetrofit(ImgurApi.API_URL).create(ImgurApi.class);
        if (ImgurUtils.isAlbum(hash)) {
            if (clientIdHeader != null) {
                // Use API v3 if the client ID is set
                Response<AlbumResponse> response =
                        service.getV3Album(clientIdHeader, hash).execute();
                AlbumResponse albumResponse = response.body();
                albumResponse.setHash(hash);
                albumResponse.setOriginalUrl(mediaUrl.toString());
                return new ParserResponse(albumResponse);
            }

            // Make an API call to get the album images via the old API
            Response<ImgurResponse> response = service.getAlbumData(hash).execute();
            ImgurResponse imgurResponse = response.body();
            imgurResponse.setHash(hash);
            imgurResponse.setOriginalUrl(mediaUrl.toString());
            return new ParserResponse(imgurResponse);
        } else {
            if (clientIdHeader != null) {
                // Use API v3 if the client ID is set
                Response<ImageResponse> response =
                        service.getV3Image(clientIdHeader, hash).execute();
                ImageResponse imageResponse = response.body();
                imageResponse.setHash(hash);
                imageResponse.setOriginalUrl(mediaUrl.toString());
                return new ParserResponse(imageResponse);
            }

            // Generate a new image object for the hash we got
            Image image = new Image();
            String ext = ParseUtils.getExtension(mediaUrl);
            if (ext != null) {
                image.ext = "." + ext;
            }
            image.hash = hash;
            image.animated = ParseUtils.isVideoExtension(mediaUrl);
            return new ParserResponse(image);
        }
    }
}
