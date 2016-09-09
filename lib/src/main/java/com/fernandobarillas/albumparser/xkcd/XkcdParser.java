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

package com.fernandobarillas.albumparser.xkcd;

import com.fernandobarillas.albumparser.exception.InvalidMediaUrlException;
import com.fernandobarillas.albumparser.parser.AbstractApiParser;
import com.fernandobarillas.albumparser.parser.ParserResponse;
import com.fernandobarillas.albumparser.util.ParseUtils;
import com.fernandobarillas.albumparser.xkcd.api.XkcdApi;
import com.fernandobarillas.albumparser.xkcd.model.XkcdResponse;

import java.io.IOException;
import java.net.URL;

import okhttp3.OkHttpClient;
import retrofit2.Response;

/**
 * Parser for XKCD API responses
 */
public class XkcdParser extends AbstractApiParser {
    public XkcdParser(OkHttpClient client) {
        super(client);
    }

    @Override
    public ParserResponse parse(URL mediaUrl) throws InvalidMediaUrlException, IOException {
        String comicNumberString = getHash(mediaUrl.toString());
        if (comicNumberString == null) {
            throw new InvalidMediaUrlException(mediaUrl);
        }

        int comicNumber;
        try {
            comicNumber = Integer.parseInt(comicNumberString);
        } catch (NumberFormatException e) {
            // XKCD API only supports getting info via an integer, the URL isn't valid
            throw new InvalidMediaUrlException(mediaUrl);
        }

        XkcdApi service = getRetrofit(XkcdApi.API_URL).create(XkcdApi.class);
        Response<XkcdResponse> response = service.getComic(comicNumber).execute();
        XkcdResponse xkcdResponse = response.body();

        return new ParserResponse(xkcdResponse);
    }

    public static String getHash(String xkcdUrl) {
        URL url = ParseUtils.getUrlObject(xkcdUrl, XkcdApi.BASE_DOMAIN);
        if (url == null) return null; // Passed in String wasn't a valid URL
        String domain = url.getHost();
        if (domain.equalsIgnoreCase("what-if.xkcd.com")) return null;
        return ParseUtils.hashRegex(url.getPath(), "/(\\d+)");
    }
}
