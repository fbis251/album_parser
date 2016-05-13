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

import com.fernandobarillas.albumparser.imgur.ImgurUtils;
import com.fernandobarillas.albumparser.imgur.api.ImgurApi;
import com.fernandobarillas.albumparser.imgur.model.ImgurResponse;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fb on 5/9/16.
 */
public class ImgurTest {
    // @formatter:off
    private static String[] IMGUR_URLS     = {
            "http://imgur.com/gallery/mhcWa37/new",
            "https://imgur.com/gallery/PBTrqAA",
            "http://i.imgur.com/sCjRLQG.jpg?1",
            "http://imgur.com/a/cvehZ",
            "http://imgur.com/gallery/0Kqrz",
            "http://imgur.com/awsGf9p",
            "https://imgur.com/gallery/WKauF", // GIF album
    };
    // @formatter:on

    public static void imgurTest() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ImgurApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ImgurApi service = retrofit.create(ImgurApi.class);
        for (String url : IMGUR_URLS) {
            String hash = ImgurUtils.getHash(url);
            if (hash == null) {
                // TODO: Handle invalid Imgur URL error
                System.out.println("No hash for URL: " + url);
                continue;
            }

            if (ImgurUtils.isAlbum(hash)) {
                System.out.println("Album Hash " + hash);
                try {
                    Response<ImgurResponse> response = service.getAlbumData(hash)
                            .execute();
                    ImgurResponse imgurResponse = response.body();
                    imgurResponse.setHash(hash);
                    imgurResponse.setOriginalUrl(url);
                    MediaTest.testApiResponse(imgurResponse);
                } catch (IOException e) {
                    System.err.println("Bad response for URL: " + url);
                    break;
                }
            } else {
                System.out.println("Gallery Hash " + hash);
                System.out.println("Image URL: " + ImgurUtils.getImageUrl(hash));
            }

            System.out.println(MediaTest.SEPARATOR);
        }
    }
}
