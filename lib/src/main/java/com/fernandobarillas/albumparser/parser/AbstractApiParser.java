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

import com.fernandobarillas.albumparser.exception.InvalidMediaUrlException;

import java.io.IOException;
import java.net.URL;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Abstract class for parsers for API responses
 */
public abstract class AbstractApiParser {
    protected OkHttpClient mClient;

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

    /**
     * @param apiUrl The URL to use when building the instance
     * @return A Retrofit instance for the passed-in API URL.
     */
    protected Retrofit getRetrofit(String apiUrl) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(apiUrl)
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
     * @throws InvalidMediaUrlException When the passed-in mediaUrl was not supported by the parser
     * @throws IOException              When there was an error during the HTTP call
     */
    protected abstract ParserResponse parse(URL mediaUrl)
            throws InvalidMediaUrlException, IOException;
}
