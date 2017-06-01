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

package com.fernandobarillas.albumparser.eroshare;

import com.fernandobarillas.albumparser.eroshare.api.EroshareApi;
import com.fernandobarillas.albumparser.eroshare.model.EroshareAlbumResponse;
import com.fernandobarillas.albumparser.eroshare.model.EroshareItemResponse;
import com.fernandobarillas.albumparser.exception.InvalidMediaUrlException;
import com.fernandobarillas.albumparser.parser.AbstractApiParser;
import com.fernandobarillas.albumparser.parser.ParserResponse;
import com.fernandobarillas.albumparser.util.ParseUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Set;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import retrofit2.Response;

/**
 * A Parser for the Eroshare API
 */
public class EroshareParser extends AbstractApiParser {

    private static final String ITEM_PATH = "i";

    private static final Pattern ALBUM_PATTERN        = Pattern.compile("^/([^\\W_]{8})(?:/.*?|)$");
    private static final Pattern ITEM_PATTERN         = Pattern.compile("^/i/([^\\W_]{8})/?");
    private static final Pattern DIRECT_MEDIA_PATTERN =
            Pattern.compile("^/([^\\W_]{8})\\.[^\\W_]{3,4}/?$");

    public EroshareParser() {
    }

    public EroshareParser(OkHttpClient client) {
        super(client);
    }

    @Override
    public String getApiUrl() {
        return EroshareApi.API_URL;
    }

    @Override
    public String getBaseDomain() {
        return EroshareApi.BASE_DOMAIN;
    }

    @Override
    public String getHash(URL mediaUrl) throws InvalidMediaUrlException {
        if (!isValidDomain(mediaUrl)) {
            throw new InvalidMediaUrlException(mediaUrl);
        }
        String path = mediaUrl.getPath();
        String hash = ParseUtils.hashRegex(path, ALBUM_PATTERN);

        // Check if this is a direct media URL
        if (hash == null) {
            hash = ParseUtils.hashRegex(path, DIRECT_MEDIA_PATTERN);
        }

        // Check if this is an item URL
        if (hash == null) {
            hash = ParseUtils.hashRegex(path, ITEM_PATTERN);
        }

        if (hash == null) throw new InvalidMediaUrlException(mediaUrl);
        return hash;
    }

    @Override
    public Set<String> getValidDomains() {
        return EroshareApi.VALID_DOMAINS_SET;
    }

    @Override
    public ParserResponse parse(URL mediaUrl) throws IOException, RuntimeException {
        String hash = getHash(mediaUrl);
        if (hash == null) {
            throw new InvalidMediaUrlException(mediaUrl);
        }

        EroshareApi service = getRetrofit().create(EroshareApi.class);
        if (ParseUtils.isDirectUrl(mediaUrl)
                || ITEM_PATH.equalsIgnoreCase(ParseUtils.getFirstPathSegment(mediaUrl))) {
            Response<EroshareItemResponse> serviceResponse = service.getItem(hash).execute();
            EroshareItemResponse apiResponse = serviceResponse.body();
            return getParserResponse(mediaUrl, apiResponse);
        } else {
            Response<EroshareAlbumResponse> serviceResponse = service.getAlbum(hash).execute();
            EroshareAlbumResponse apiResponse = serviceResponse.body();
            return getParserResponse(mediaUrl, apiResponse);
        }
    }
}
