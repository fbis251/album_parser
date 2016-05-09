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
import com.fernandobarillas.albumparser.vidble.exception.InvalidVidbleUrlException;
import com.fernandobarillas.albumparser.vidble.model.VidbleResponse;
import com.fernandobarillas.albumparser.vidble.model.VidbleUrl;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by fb on 5/9/16.
 */
public class VidbleTest {
    private static String[] VIDBLE_URLS = {
            "https://vidble.com/album/TESTTEST",
            "https://vidble.com/1234567890.jpg",
            "https://vidble.com/show/1234567890.gif",
    };

    public static void vidbleTest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(VidbleApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        VidbleApi service = retrofit.create(VidbleApi.class);
        boolean getOriginalQuality = true; // Set to false to get medium quality images

        for (String url : VIDBLE_URLS) {
            String hash = VidbleUtils.getHash(url);
            if (hash == null) {
                // TODO: Handle invalid Vidble URL error
                System.out.println("No hash for URL: " + url);
                break;
            }

            if (VidbleUtils.isAlbum(hash)) {
                System.out.println("Album Hash " + hash);
                try {
                    Response<VidbleResponse> response = service.getAlbumData(hash).execute();
                    List<String> pics = response.body().getPics(getOriginalQuality);
                    System.out.println("Image count: " + pics.size());
                    for (String picUrl : pics) {
                        System.out.println(hash + " " + picUrl);
                    }
                } catch (IOException e) {
                    System.err.println("Album error: " + hash + " " + e.getMessage());
                    // TODO: Handle error
                    break;
                }
            } else {
                try {
                    VidbleUrl vidbleUrl = new VidbleUrl(url);
                    System.out.println(hash + " " + vidbleUrl.getUrl(getOriginalQuality));
                } catch (InvalidVidbleUrlException e) {
                    System.out.println("Not a Vidble URL: " + url);
                    // TODO: Handle invalid Vidble URL error
                    break;
                }
            }
        }
    }
}
