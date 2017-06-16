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

package com.fernandobarillas.albumparser.giphy.api;

import com.fernandobarillas.albumparser.giphy.model.GiphyResponse;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Retrofit Interface for the Giphy API
 */
public interface GiphyApi {
    // No trailing slash!
    String BASE_DOMAIN = "giphy.com";
    String API_URL     = "https://api." + BASE_DOMAIN;
    String MEDIA_URL   = "https://media." + BASE_DOMAIN;

    String[] VALID_DOMAINS = {
            BASE_DOMAIN, "*." + BASE_DOMAIN
    };

    Set<String> VALID_DOMAINS_SET = new HashSet<>(Arrays.asList(VALID_DOMAINS));

    @GET("/v1/gifs/{hash}")
    Call<GiphyResponse> getGif(@Path("hash") String hash, @Query("api_key") String apiKey);
}
