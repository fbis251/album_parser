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

package com.fernandobarillas.albumparser.gfycat.api;

import com.fernandobarillas.albumparser.gfycat.model.CheckLinkResponse;
import com.fernandobarillas.albumparser.gfycat.model.GfyItem;
import com.fernandobarillas.albumparser.gfycat.model.QueryHashResponse;
import com.fernandobarillas.albumparser.gfycat.model.TranscodeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Retrofit Interface for the Gfycat API
 */
public interface GfycatApi {
    // No trailing slash!
    String BASE_DOMAIN = "gfycat.com";
    String API_URL     = "https://" + BASE_DOMAIN;
    String UPLOAD_URL  = "https://upload." + BASE_DOMAIN;
    String THUMB_URL   = "https://thumbs." + BASE_DOMAIN;

    @GET("/cajax/checkUrl/{url}")
    Call<CheckLinkResponse> checkLink(@Path("url") String url);

    @GET(UPLOAD_URL + "/transcode")
    Call<GfyItem> convertGif(@Query("fetchUrl") String gifUrl);

    @GET(UPLOAD_URL + "/transcodeRelease/{randomString}")
    Call<TranscodeResponse> convertGif(@Path("randomString") String randomString,
            @Query("fetchUrl") String gifUrl);

    @GET("/cajax/get/{hash}")
    Call<QueryHashResponse> queryHash(@Path("hash") String hash);
}
