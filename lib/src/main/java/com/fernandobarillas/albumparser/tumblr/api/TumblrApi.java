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

package com.fernandobarillas.albumparser.tumblr.api;

import com.fernandobarillas.albumparser.tumblr.model.TumblrResponse;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Retrofit Interface for the Tumblr API
 */
public interface TumblrApi {
    // No trailing slashes!
    String BASE_DOMAIN = "tumblr.com";
    String API_URL     = "https://api." + BASE_DOMAIN;

    String[]    VALID_DOMAINS     = {
            "*." + BASE_DOMAIN
    };
    Set<String> VALID_DOMAINS_SET = new HashSet<>(Arrays.asList(VALID_DOMAINS));

    /** The width of the image to use as the "low quality" version */
    int LOW_QUALITY_WIDTH = 500;
    /** The width of the image to use as the preview version */
    int PREVIEW_WIDTH     = 400;

    @GET("/v2/blog/{blogDomain}/posts/photo")
    Call<TumblrResponse> getPost(@Path("blogDomain") String blogDomain,
            @Query("id") String postId,
            @Query("api_key") String apiKey);
}
