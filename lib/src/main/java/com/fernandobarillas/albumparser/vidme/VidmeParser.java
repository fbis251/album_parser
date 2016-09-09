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

package com.fernandobarillas.albumparser.vidme;

import com.fernandobarillas.albumparser.parser.AbstractApiParser;
import com.fernandobarillas.albumparser.parser.ParserResponse;
import com.fernandobarillas.albumparser.exception.InvalidMediaUrlException;
import com.fernandobarillas.albumparser.vidme.api.VidmeApi;
import com.fernandobarillas.albumparser.vidme.model.VidmeResponse;

import java.io.IOException;
import java.net.URL;

import okhttp3.OkHttpClient;
import retrofit2.Response;

/**
 * Parser for vid.me API responses
 */
public class VidmeParser extends AbstractApiParser {
    public VidmeParser(OkHttpClient client) {
        super(client);
    }

    @Override
    public ParserResponse parse(URL mediaUrl) throws InvalidMediaUrlException, IOException {
        if(mediaUrl == null) {
            throw new InvalidMediaUrlException(mediaUrl);
        }
        String hash = VidmeUtils.getHash(mediaUrl.toString());
        if (hash == null) {
            throw new InvalidMediaUrlException(mediaUrl);
        }

        VidmeApi service = getRetrofit(VidmeApi.API_URL).create(VidmeApi.class);
        Response<VidmeResponse> response = service.getVideoData(hash).execute();
        VidmeResponse apiResponse = response.body();
        apiResponse.setOriginalUrl(mediaUrl.toString());
        return new ParserResponse(apiResponse);
    }
}
