package com.example;

import com.fernandobarillas.albumparser.imgur.ImgurUtils;
import com.fernandobarillas.albumparser.imgur.api.ImgurApi;
import com.fernandobarillas.albumparser.imgur.model.Data;
import com.fernandobarillas.albumparser.imgur.model.Image;
import com.fernandobarillas.albumparser.imgur.model.ImgurResponse;
import com.google.gson.JsonSyntaxException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by fb on 5/3/16.
 */
public class Main {
    private static boolean ASYNC_REQUESTS = false;
    private static String[] IMGUR_URLS = {
            "http://google.com/test",
            "http://imgur.com/gallery/mhcWa37/new",
            "https://imgur.com/gallery/PBTrqAA",
            "http://i.imgur.com/sCjRLQG.jpg?1",
            "http://imgur.com/a/cvehZ",
            "http://i.imgur.com/9U0eIZR.jpg?fb",
            "http://imgur.com/gallery/0Kqrz",
            "http://imgur.com/awsGf9p",
    };

    public static void main(String[] args) {
        System.out.println("Starting");
        testLibrary();
        System.out.println("Done");
    }

    private static void testLibrary() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ImgurApi.API_URL)
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
                if (ASYNC_REQUESTS) {
                    service.getAlbumData(hash).enqueue(callbackHandler(hash));
                } else {
                    try {
                        Response<ImgurResponse> response = service.getAlbumData(hash).execute();
                        parseImgurResponse(response.body());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("Gallery Hash " + hash);
                System.out.println("Image URL: " + ImgurUtils.getImageUrl(hash));
            }

            System.out.println();
        }
    }

    private static Callback<ImgurResponse> callbackHandler(final String hash) {
        return new Callback<ImgurResponse>() {
            @Override
            public void onResponse(Call<ImgurResponse> call, Response<ImgurResponse> response) {
                System.out.println("Call URL: " + call.request().url());
                System.out.println("Response " + response.raw());
                parseImgurResponse(response.body());
            }

            @Override
            public void onFailure(Call<ImgurResponse> call, Throwable t) {
                System.err.println("Error " + t.getLocalizedMessage());
                System.err.println("OBJECT CLASS: " + t.getClass());

                if (t instanceof JsonSyntaxException) {
                    String url = ImgurApi.API_URL + hash + ".png";
                    System.err.println(url);
                    return;
                }

                t.printStackTrace();
            }
        };
    }

    private static void parseImgurResponse(ImgurResponse imgurResponse) {
        System.out.println("Success: " + imgurResponse.isSuccess());
        System.out.println("Status: " + imgurResponse.getStatus());
        Data imgurData = imgurResponse.getData();
        if (imgurData == null) return; // TODO: Handle null data
        System.out.println("Image Count: " + imgurResponse.getData().getCount());
        List<Image> imageList = imgurResponse.getData().getImages();
        if (imageList == null) return; // TODO: Handle null imageList
        for (Image image : imageList) {
            System.out.println("Image URL: " + image.getImageUrl());
        }
    }
}
