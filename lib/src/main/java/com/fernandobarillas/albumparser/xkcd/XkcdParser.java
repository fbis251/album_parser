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
import com.fernandobarillas.albumparser.xkcd.model.XkcdImage;
import com.fernandobarillas.albumparser.xkcd.model.XkcdResponse;

import java.io.IOException;
import java.net.URL;
import java.util.Set;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import retrofit2.Response;

/**
 * Parser for the XKCD API
 */
public class XkcdParser extends AbstractApiParser {

    private static final Pattern COMICS_REGEX = Pattern.compile("([\\w.]+)");
    private static final Pattern NUMBER_REGEX = Pattern.compile("^(\\d+)$");
    private static final String  PATH_COMICS  = "comics";

    public XkcdParser() {
    }

    public XkcdParser(OkHttpClient client) {
        super(client);
    }

    @Override
    public String getApiUrl() {
        return XkcdApi.API_URL;
    }

    @Override
    public String getBaseDomain() {
        return XkcdApi.BASE_DOMAIN;
    }

    @Override
    public String getHash(URL mediaUrl) {
        if (!isValidDomain(mediaUrl)) throw new InvalidMediaUrlException(mediaUrl);
        String[] splitPath = ParseUtils.getSplitPath(mediaUrl);
        String hash;

        // No path segments for the URL
        if (splitPath == null || splitPath.length < 1) throw new InvalidMediaUrlException(mediaUrl);
        String firstSegment = splitPath[0];
        if (splitPath.length >= 2
                && PATH_COMICS.equalsIgnoreCase(firstSegment)
                && ParseUtils.isImageExtension(mediaUrl)) {
            String secondSegment = splitPath[1];
            hash = ParseUtils.hashRegex(secondSegment, COMICS_REGEX);
            hash = String.format("/%s/%s", PATH_COMICS, hash);
        } else {
            hash = ParseUtils.hashRegex(firstSegment, NUMBER_REGEX);
            getComicNumber(mediaUrl, hash);
        }
        if (hash == null) throw new InvalidMediaUrlException(mediaUrl);
        return hash;
    }

    @Override
    public Set<String> getValidDomains() {
        return XkcdApi.VALID_DOMAINS_SET;
    }

    @Override
    public ParserResponse parse(URL mediaUrl) throws IOException, RuntimeException {
        String hash = getHash(mediaUrl);
        if (hash == null) {
            throw new InvalidMediaUrlException(mediaUrl);
        }

        if (hash.startsWith("/comics/") && ParseUtils.isImageExtension(mediaUrl)) {
            // Direct link to an xkcd comic image
            XkcdImage image = new XkcdImage(null, null, mediaUrl.toString());
            ParserResponse parserResponse = new ParserResponse(image);
            parserResponse.setOriginalUrl(mediaUrl);
            return parserResponse;
        }

        long comicNumber = getComicNumber(mediaUrl, hash);
        XkcdApi service = getRetrofit().create(XkcdApi.class);
        Response<XkcdResponse> serviceResponse = service.getComic(comicNumber).execute();
        XkcdResponse apiResponse = serviceResponse.body();
        return getParserResponse(mediaUrl, apiResponse);
    }

    @Override
    protected boolean isValidDomain(URL mediaUrl) {
        if (mediaUrl == null) return false;
        String domain = mediaUrl.getHost().toLowerCase();
        String baseDomain = getBaseDomain();
        return baseDomain.equals(domain) || getValidDomains().contains(domain);
    }

    private long getComicNumber(final URL mediaUrl, final String hash)
            throws InvalidMediaUrlException {
        long comicNumber;
        try {
            comicNumber = Long.parseLong(hash);
            if (comicNumber < 1) {
                // XKCD comic numbers 1 or higher
                throw new InvalidMediaUrlException(mediaUrl);
            }
        } catch (NumberFormatException e) {
            // XKCD API only supports getting info via an integer, the URL isn't valid
            throw new InvalidMediaUrlException(mediaUrl);
        }
        return comicNumber;
    }
}
