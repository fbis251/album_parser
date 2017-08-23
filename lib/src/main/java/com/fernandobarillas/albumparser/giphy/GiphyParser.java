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

package com.fernandobarillas.albumparser.giphy;

import com.fernandobarillas.albumparser.exception.InvalidApiKeyException;
import com.fernandobarillas.albumparser.exception.InvalidMediaUrlException;
import com.fernandobarillas.albumparser.giphy.api.GiphyApi;
import com.fernandobarillas.albumparser.giphy.model.GiphyMedia;
import com.fernandobarillas.albumparser.giphy.model.GiphyResponse;
import com.fernandobarillas.albumparser.parser.AbstractApiParser;
import com.fernandobarillas.albumparser.parser.ParserResponse;
import com.fernandobarillas.albumparser.util.ParseUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

import okhttp3.OkHttpClient;
import retrofit2.Response;

/**
 * Parser for the Giphy API
 */
public class GiphyParser extends AbstractApiParser {
    private String mGiphyApiKey = null;

    public GiphyParser() {
    }

    public GiphyParser(OkHttpClient client) {
        super(client);
    }

    public GiphyParser(OkHttpClient client, String giphyApiKey) {
        this(client);
        mGiphyApiKey = giphyApiKey;
    }

    @Override
    public String getApiUrl() {
        return GiphyApi.API_URL;
    }

    @Override
    public String getBaseDomain() {
        return GiphyApi.BASE_DOMAIN;
    }

    @Override
    public String getHash(URL mediaUrl) throws InvalidMediaUrlException {
        if (mediaUrl == null) throw new InvalidMediaUrlException(mediaUrl);
        String hash;
        String path = mediaUrl.getPath();

        if (path.startsWith("/gifs/")) {
            hash = ParseUtils.hashRegex(path, "/gifs/(?:.*-)?(\\w+)\\/?.*$");
        } else if (path.startsWith("/embed/")) {
            hash = ParseUtils.hashRegex(path, "/embed/(\\w+)");
        } else if (path.startsWith("/media/")) {
            hash = ParseUtils.hashRegex(path, "/media/(\\w+)");
        } else {
            hash = ParseUtils.hashRegex(path, "/(\\w+)");
        }

        if (hash == null) throw new InvalidMediaUrlException(mediaUrl);
        return hash;
    }

    @Override
    public Set<String> getValidDomains() {
        return GiphyApi.VALID_DOMAINS_SET;
    }

    @Override
    public ParserResponse parse(URL mediaUrl) throws IOException, RuntimeException {
        String hash = getHash(mediaUrl);
        String apiKey = null;
        if (mGiphyApiKey != null) {
            apiKey = mGiphyApiKey.trim();
            if (apiKey.isEmpty()) {
                throw new InvalidApiKeyException(mediaUrl,
                        apiKey,
                        "Giphy API key cannot be blank. Please set the key to null to use API calls");
            }
        }
        if (apiKey != null) {
            GiphyApi service = getRetrofit().create(GiphyApi.class);
            Response<GiphyResponse> serviceResponse = service.getGif(hash, apiKey).execute();
            GiphyResponse apiResponse = serviceResponse.body();
            return getParserResponse(mediaUrl, apiResponse, serviceResponse);
        }

        // API key was null, try to generate a response anyway. Some of the URLs in the returned
        // media might be invalid since we're guessing
        ParserResponse parserResponse = new ParserResponse(new GiphyMedia(hash));
        parserResponse.setOriginalUrl(mediaUrl);
        return parserResponse;
    }
}
