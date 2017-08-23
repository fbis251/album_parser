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

package com.fernandobarillas.albumparser.vidble;

import com.fernandobarillas.albumparser.exception.InvalidMediaUrlException;
import com.fernandobarillas.albumparser.parser.AbstractApiParser;
import com.fernandobarillas.albumparser.parser.ParserResponse;
import com.fernandobarillas.albumparser.vidble.api.VidbleApi;
import com.fernandobarillas.albumparser.vidble.model.VidbleMedia;
import com.fernandobarillas.albumparser.vidble.model.VidbleResponse;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

import okhttp3.OkHttpClient;
import retrofit2.Response;

/**
 * Parser for the Vidble API
 */
public class VidbleParser extends AbstractApiParser {

    public VidbleParser() {
    }

    public VidbleParser(OkHttpClient client) {
        super(client);
    }

    @Override
    public String getApiUrl() {
        return VidbleApi.API_URL;
    }

    @Override
    public String getBaseDomain() {
        return VidbleApi.BASE_DOMAIN;
    }

    @Override
    public String getHash(URL mediaUrl) {
        if (mediaUrl == null) throw new InvalidMediaUrlException(mediaUrl);
        String hash = VidbleUtils.getHash(mediaUrl.toString()); // TODO: Implement me in this method
        if (hash == null) throw new InvalidMediaUrlException(mediaUrl);
        return hash;
    }

    @Override
    public Set<String> getValidDomains() {
        return VidbleApi.VALID_DOMAINS_SET;
    }

    @Override
    public ParserResponse parse(URL mediaUrl) throws IOException, RuntimeException {
        String hash = getHash(mediaUrl);

        if (VidbleUtils.isAlbum(hash)) {
            VidbleApi service = getRetrofit().create(VidbleApi.class);
            Response<VidbleResponse> serviceResponse = service.getAlbumData(hash).execute();
            VidbleResponse apiResponse = serviceResponse.body();
            return getParserResponse(mediaUrl, apiResponse, serviceResponse);
        } else {
            ParserResponse parserResponse =
                    new ParserResponse(new VidbleMedia(mediaUrl.toString()));
            parserResponse.setOriginalUrl(mediaUrl);
            return parserResponse;
        }
    }
}
