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

package com.fernandobarillas.albumparser.parser;

import com.fernandobarillas.albumparser.exception.InvalidApiResponseException;
import com.fernandobarillas.albumparser.exception.InvalidMediaUrlException;
import com.fernandobarillas.albumparser.media.IApiResponse;
import com.fernandobarillas.albumparser.util.ParseUtils;

import java.io.IOException;
import java.net.URL;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Abstract class for parsers for API responses. This defines a few helper methods to make getting
 * the correct Retrofit service instance easier such as {@link #getRetrofit(String)}
 */
public abstract class AbstractApiParser {
    private OkHttpClient mClient;

    /**
     * Instantiates the parser using the default OkHttpClient in Retrofit
     */
    public AbstractApiParser() {
    }

    /**
     * Instantiates the parser using the passed-in OkHttpClient
     *
     * @param client The client to use with all the retrofit requests
     */
    public AbstractApiParser(OkHttpClient client) {
        mClient = client;
    }

    public boolean canParse(String mediaUrl) {
        return canParse(ParseUtils.getUrlObject(mediaUrl));
    }

    /**
     * Checks whether the passed-in media URL can be parsed based on the URL alone, the API may
     * still return an invalid response. This is useful for filtering URLs based on whether a
     * certain parser is able to call the API for a response.
     *
     * @param mediaUrl The URL to check whether the parser can attempt to call the API for
     * @return True if this parser can properly parse the passed-in media URL, false otherwise.
     */
    public boolean canParse(URL mediaUrl) {
        return mediaUrl != null
                && isDomainMatch(mediaUrl.getHost(), getBaseDomain())
                && getHash(mediaUrl) != null;
    }

    /**
     * @return The API URL used to make all API service calls, for example:
     * "https://api.imgur.com/3"
     */
    public abstract String getApiUrl();

    /**
     * @return The base domain name of the API provider, for example: imgur.com, streamable.com,
     * gfycat.com
     */
    public abstract String getBaseDomain();

    /**
     * Gets the hash for the passed-in media URL
     *
     * @param mediaUrl The URL to get the hash for
     * @return A hash that can be used in an API call if the URL can be parsed, null otherwise
     */
    public String getHash(String mediaUrl) {
        return getHash(ParseUtils.getUrlObject(mediaUrl));
    }

    /**
     * Gets the hash for the passed-in media URL
     *
     * @param mediaUrl The URL to get the hash for
     * @return A hash that can be used in an API call if the URL can be parsed, null otherwise
     */
    public abstract String getHash(URL mediaUrl) throws InvalidMediaUrlException;

    public ParserResponse getParserResponse(final URL mediaUrl, final IApiResponse apiResponse)
            throws InvalidApiResponseException {
        if (apiResponse == null) throw new InvalidApiResponseException(mediaUrl);
        ParserResponse parserResponse = new ParserResponse(apiResponse);
        parserResponse.setOriginalUrl(mediaUrl);
        return parserResponse;
    }

    /**
     * @return A Retrofit instance for the passed-in API URL.
     */
    protected Retrofit getRetrofit() {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(getApiUrl())
                .addConverterFactory(GsonConverterFactory.create());
        if (mClient != null) {
            retrofitBuilder = retrofitBuilder.client(mClient);
        }
        return retrofitBuilder.build();
    }

    /**
     * Parses a media URL and attempts to get a response from the respective API
     *
     * @param mediaUrl The URL to attempt to parse and get an API response for
     * @return The parsed API response for the passed-in mediaUrl
     * @throws IOException      When there was an error during the HTTP call
     * @throws RuntimeException When the passed-in media URL was not supported by the parser, the
     *                          API returns a null response or a response which the library could
     *                          not parse.
     */
    protected abstract ParserResponse parse(URL mediaUrl) throws IOException, RuntimeException;

    private boolean isDomainMatch(String domain, String providerDomain) {
        return domain != null && providerDomain != null && (domain.endsWith("." + providerDomain)
                || domain.equals(providerDomain));
    }
}
