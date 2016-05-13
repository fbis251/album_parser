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

package com.fernandobarillas.albumparser.gfycat;

import com.fernandobarillas.albumparser.IApiParser;
import com.fernandobarillas.albumparser.ParserResponse;
import com.fernandobarillas.albumparser.exception.InvalidMediaUrlException;
import com.fernandobarillas.albumparser.gfycat.api.GfycatApi;
import com.fernandobarillas.albumparser.gfycat.model.cajax.CajaxResponse;
import com.fernandobarillas.albumparser.gfycat.model.cajax.GfyItem;

import java.io.IOException;
import java.net.URL;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.fernandobarillas.albumparser.util.ParseUtils.isDirectUrl;

/**
 * Parser for Gfycat API responses
 */
public class GfycatParser implements IApiParser {
    @Override
    public ParserResponse parse(URL mediaUrl) throws InvalidMediaUrlException, IOException {
        String hash = GfycatUtils.getHash(mediaUrl.toString());
        if (hash == null) {
            throw new InvalidMediaUrlException();
        }

        if (isDirectUrl(mediaUrl)) {
            GfyItem gfyItem = new GfyItem();
            gfyItem.mp4Url = mediaUrl.toString();
            gfyItem.mobileUrl = GfycatUtils.getMobileUrl(hash);
            gfyItem.posterUrl = GfycatUtils.getPosterUrl(hash);
            return new ParserResponse(gfyItem);
        }

        Retrofit retrofit = new Retrofit.Builder().baseUrl(GfycatApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GfycatApi service = retrofit.create(GfycatApi.class);
        Response<CajaxResponse> response = service.getCajax(hash)
                .execute();
        CajaxResponse mediaResponse = response.body();
        mediaResponse.setOriginalUrl(mediaUrl.toString());

        return new ParserResponse(mediaResponse);
    }
}
