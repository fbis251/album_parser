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

import com.fernandobarillas.albumparser.parser.AbstractApiParser;
import com.fernandobarillas.albumparser.parser.ParserResponse;
import com.fernandobarillas.albumparser.exception.InvalidMediaUrlException;
import com.fernandobarillas.albumparser.vidble.api.VidbleApi;
import com.fernandobarillas.albumparser.vidble.model.VidbleMedia;
import com.fernandobarillas.albumparser.vidble.model.VidbleResponse;

import java.io.IOException;
import java.net.URL;

import okhttp3.OkHttpClient;
import retrofit2.Response;

/**
 * Parser for Vidble API responses
 */
public class VidbleParser extends AbstractApiParser {
    public VidbleParser(OkHttpClient client) {
        super(client);
    }

    @Override
    public ParserResponse parse(URL mediaUrl) throws InvalidMediaUrlException, IOException {
        String hash = VidbleUtils.getHash(mediaUrl);
        if (hash == null) {
            throw new InvalidMediaUrlException();
        }

        if (VidbleUtils.isAlbum(hash)) {
            VidbleApi service = getRetrofit(VidbleApi.API_URL).create(VidbleApi.class);
            Response<VidbleResponse> response = service.getAlbumData(hash).execute();
            VidbleResponse vidbleResponse = response.body();
            vidbleResponse.setHash(hash);
            vidbleResponse.setOriginalUrl(mediaUrl.toString());
            return new ParserResponse(vidbleResponse);
        } else {
            return new ParserResponse(new VidbleMedia(mediaUrl.toString()));
        }
    }
}
