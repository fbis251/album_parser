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

import com.fernandobarillas.albumparser.vidble.VidbleUtils;
import com.fernandobarillas.albumparser.vidble.api.VidbleApi;
import com.fernandobarillas.albumparser.vidble.model.VidbleMedia;
import com.fernandobarillas.albumparser.vidble.model.VidbleResponse;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.MediaTest.SEPARATOR;

/**
 * Created by fb on 5/9/16.
 */
public class VidbleTest {
    // @formatter:off
    private static String[] VIDBLE_URLS = {
            "https://vidble.com/album/cfQZodMa",
            "https://vidble.com/z5YMVXptwO_med.jpg",
            "https://vidble.com/show/z5YMVXptwO",
            "https://vidble.com/album/7wOKVlEE",
            "https://vidble.com/7VRBiHVRco.gif",
            "https://vidble.com/show/7VRBiHVRco",
    };
    // @formatter:on

    public static void vidbleTest() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(VidbleApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        VidbleApi service = retrofit.create(VidbleApi.class);

        for (String url : VIDBLE_URLS) {
            String hash = VidbleUtils.getHash(url);
            if (hash == null) {
                // TODO: Handle invalid Vidble URL error
                System.out.println("No hash for URL: " + url);
                break;
            }

            if (VidbleUtils.isAlbum(hash)) {
                try {
                    Response<VidbleResponse> response = service.getAlbumData(hash)
                            .execute();
                    VidbleResponse vidbleResponse = response.body();
                    vidbleResponse.setHash(hash);
                    vidbleResponse.setOriginalUrl(url);
                    MediaTest.testResponse(vidbleResponse);
                } catch (IOException e) {
                    System.err.println("Album error: " + hash + " " + e.getMessage());
                    // TODO: Handle error
                    break;
                }
            } else {
                VidbleMedia vidbleMedia = new VidbleMedia(url);
                System.out.println(MediaTest.getMediaInfo(hash, vidbleMedia));
                System.out.println(SEPARATOR);
            }
        }
    }
}
