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

package com.example;

import com.fernandobarillas.albumparser.media.IMediaResponse;
import com.fernandobarillas.albumparser.vidme.VidmeUtils;
import com.fernandobarillas.albumparser.vidme.api.VidmeApi;
import com.fernandobarillas.albumparser.vidme.model.VidmeResponse;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fb on 5/10/16.
 */
public class VidmeTest {
    // @formatter:off
    private static final String[] TEST_URLS = {
            "https://vid.me/e/yYU",
            "https://vid.me/Y8JS",
    };
    // @formatter:on

    public static void vidmeTest() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(VidmeApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        VidmeApi service = retrofit.create(VidmeApi.class);

        for (String vidmeUrl : TEST_URLS) {
            String hash = VidmeUtils.getHash(vidmeUrl);
            if (hash == null) {
                System.out.println("Invalid vid.me URL: " + vidmeUrl);
                break;
            }

            try {
                Response<VidmeResponse> response = service.getVideoData(hash)
                        .execute();
                IMediaResponse mediaResponse = response.body();
                mediaResponse.setOriginalUrl(vidmeUrl);
                MediaTest.testResponse(mediaResponse);
            } catch (IOException e) {
                System.err.println("Error with request: " + vidmeUrl);
                e.printStackTrace();
                break;
            }
        }
    }
}
