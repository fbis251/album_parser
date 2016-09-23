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

package com.fernandobarillas.albumparser.deviantart;

import com.fernandobarillas.albumparser.deviantart.api.DeviantartApi;
import com.fernandobarillas.albumparser.deviantart.model.DeviantartResponse;
import com.fernandobarillas.albumparser.exception.InvalidMediaUrlException;
import com.fernandobarillas.albumparser.parser.AbstractApiParser;
import com.fernandobarillas.albumparser.parser.ParserResponse;

import java.io.IOException;
import java.net.URL;

import okhttp3.OkHttpClient;
import retrofit2.Response;

import static com.fernandobarillas.albumparser.util.ParseUtils.isDirectUrl;

/**
 * Parser for the DeviantArt API
 */
public class DeviantartParser extends AbstractApiParser {
    public DeviantartParser(OkHttpClient client) {
        super(client);
    }

    @Override
    public String getApiUrl() {
        return DeviantartApi.API_URL;
    }

    @Override
    public String getBaseDomain() {
        return DeviantartApi.BASE_DOMAIN;
    }

    @Override
    public String getHash(URL mediaUrl) throws InvalidMediaUrlException {
        if (mediaUrl == null) throw new InvalidMediaUrlException(mediaUrl);
        return mediaUrl.toString();
    }

    @Override
    public ParserResponse parse(URL mediaUrl) throws IOException, RuntimeException {
        String hash = getHash(mediaUrl);

        if (isDirectUrl(mediaUrl)) {
            DeviantartResponse response = new DeviantartResponse();
            // DeviantArt won't return OEmbed data for direct URLs
            response.url = mediaUrl.toString();
            return new ParserResponse(response);
        }

        DeviantartApi service = getRetrofit().create(DeviantartApi.class);
        Response<DeviantartResponse> response = service.getOembed(hash).execute();
        DeviantartResponse deviantartResponse = response.body();
        return new ParserResponse(deviantartResponse);
    }
}
