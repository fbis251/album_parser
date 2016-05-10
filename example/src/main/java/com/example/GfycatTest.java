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

import com.fernandobarillas.albumparser.gfycat.GfycatUtils;
import com.fernandobarillas.albumparser.gfycat.api.GfycatApi;
import com.fernandobarillas.albumparser.gfycat.model.cajax.CajaxResponse;
import com.fernandobarillas.albumparser.gfycat.model.cajax.GfyItem;
import com.fernandobarillas.albumparser.gfycat.model.transcode.TranscodeResponse;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

/**
 * Created by fb on 5/9/16.
 */
public class GfycatTest {
    private static String[] GFYCAT_URLS = {
            "https://thumbs.gfycat.com/PotableLeftAbalone-mobile.mp4",
            "https://gfycat.com/IndelibleMerryBuck",
            "https://gfycat.com//GregariousPepperyBellfrog",
            "https://giant.gfycat.com/GregariousPepperyBellfrog.webm",
    };

    private static String[] GIF_URLS = {
            "https://i.imgur.com/XKRUHJH.gif",
            "http://i.imgur.com/XKRUHJH.gif",
            "i.imgur.com/XKRUHJH.gif",
            "i.imgur.com/XKRUHJH.jpg",
    };

    public static void gfycatTest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GfycatApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GfycatApi service = retrofit.create(GfycatApi.class);
        testHashes(service);
        testTranscode(service);
    }

    private static void testTranscode(GfycatApi service) {
        if (service == null) return;
        for (String gifUrl : GIF_URLS) {
            try {
                Response<TranscodeResponse> response = service.getTranscode(gifUrl).execute();
                TranscodeResponse transcodeRespose = response.body();
                if (transcodeRespose == null) break; // TODO: Handle bad data
                System.out.println(transcodeRespose);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private static void testHashes(GfycatApi service) {
        if (service == null) return;
        for (String gfycatUrl : GFYCAT_URLS) {
            String hash = GfycatUtils.getHash(gfycatUrl);
            if (hash == null) {
                System.out.println("Invalid Gfycat URL: " + gfycatUrl);
                break;
            }

            try {
                Response<CajaxResponse> response = service.getCajax(hash).execute();
                CajaxResponse cajax = response.body();
                GfyItem gfyItem = cajax.getGfyItem();
                if (gfyItem == null) break; // TODO: Handle bad data
                System.out.println(gfyItem);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
