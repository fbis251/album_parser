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

import com.fernandobarillas.albumparser.streamable.StreamableUtils;
import com.fernandobarillas.albumparser.streamable.api.StreamableApi;
import com.fernandobarillas.albumparser.streamable.model.StreamableResponse;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fb on 5/10/16.
 */
public class StreamableTest {
    // @formatter:off
    private static final String[] TEST_URLS = {
        "https://streamable.com/7k1v",
        "https://cdn.streamable.com/video/mp4-mobile/ghju.mp4",
        "https://cdn.streamable.com/video/mp4/w78y.mp4",
        "https://streamable.com/hm0i?t=0.2",

    };
    // @formatter:on

    public static void streamableTest() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(StreamableApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        StreamableApi service = retrofit.create(StreamableApi.class);

        for (String streamableUrl : TEST_URLS) {
            if (StreamableUtils.isDirectUrl(streamableUrl)) {
                System.out.println("Direct url: " + streamableUrl);
                System.out.println(MediaTest.SEPARATOR);
                continue;
            }

            String hash = StreamableUtils.getHash(streamableUrl);
            if (hash == null) {
                System.out.println("Invalid Streamable URL: " + streamableUrl);
                break;
            }

            try {
                Response<StreamableResponse> response = service.getVideo(hash)
                        .execute();
                StreamableResponse streamableResponse = response.body();
                streamableResponse.setHash(hash);
                streamableResponse.setOriginalUrl(streamableUrl);
                MediaTest.testApiResponse(streamableResponse);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
