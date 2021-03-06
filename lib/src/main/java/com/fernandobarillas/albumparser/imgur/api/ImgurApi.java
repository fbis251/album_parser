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

package com.fernandobarillas.albumparser.imgur.api;

import com.fernandobarillas.albumparser.imgur.model.AlbumResponse;
import com.fernandobarillas.albumparser.imgur.model.v3.AlbumResponseV3;
import com.fernandobarillas.albumparser.imgur.model.v3.GalleryResponseV3;
import com.fernandobarillas.albumparser.imgur.model.v3.ImageResponseV3;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Retrofit Interface for the Imgur API
 */
public interface ImgurApi {
    // No trailing slash!
    String BASE_DOMAIN = "imgur.com";

    String[]    VALID_DOMAINS     = {
            BASE_DOMAIN,
            "www." + BASE_DOMAIN,
            "i.stack." + BASE_DOMAIN,
            "i." + BASE_DOMAIN,
            "m." + BASE_DOMAIN,
            "bildgur.de",
            "b.bildgur.de",
            "i.bildgur.de",
    };
    Set<String> VALID_DOMAINS_SET = new HashSet<>(Arrays.asList(VALID_DOMAINS));

    String API_URL                 = "https://" + BASE_DOMAIN;
    String API_URL_V3              = "https://api." + BASE_DOMAIN + "/3";
    String IMAGE_URL               = "https://i." + BASE_DOMAIN;
    String CLIENT_ID_HEADER_PREFIX = "Client-ID";

    @GET("/ajaxalbums/getimages/{hash}/hit.json?all=true")
    Call<AlbumResponse> getAlbumData(@Path("hash") String hash);

    // https://api.imgur.com/3/album/{id}
    @GET(API_URL_V3 + "/album/{hash}")
    Call<AlbumResponseV3> getV3Album(@Header("Authorization") String authHeader,
            @Path("hash") String hash);

    // https://api.imgur.com/3/image/{id}
    @GET(API_URL_V3 + "/image/{hash}")
    Call<ImageResponseV3> getV3Image(@Header("Authorization") String authHeader,
            @Path("hash") String hash);

    // https://api.imgur.com/3/image/{id}
    @GET(API_URL_V3 + "/gallery/{hash}")
    Call<GalleryResponseV3> getV3Gallery(@Header("Authorization") String authHeader,
            @Path("hash") String hash);
}
