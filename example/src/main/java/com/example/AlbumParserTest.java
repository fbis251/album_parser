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

import com.fernandobarillas.albumparser.AlbumParser;
import com.fernandobarillas.albumparser.media.IApiResponse;
import com.fernandobarillas.albumparser.media.IMedia;
import com.fernandobarillas.albumparser.media.IMediaAlbum;
import com.fernandobarillas.albumparser.parser.ParserResponse;
import com.fernandobarillas.albumparser.util.ParseUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

/**
 * Created by fb on 5/13/16.
 */
public class AlbumParserTest {
    protected final static String SEPARATOR = "\n--------------------\n";
    // @formatter:off
    private static final String[] TEST_URLS = {
            "https://gfycat.com/gifs/detail/AngryFrequentChuckwalla",
            "https://gfycat.com/UnconsciousTalkativeBluejay", // 404
            "https://fat.gfycat.com/PotableLeftAbalone.webm",
            "https://fat.gfycat.com/PotableLeftAbalone.mp4",
            "https://gfycat.com/IndelibleMerryBuck",
            "https://thumbs.gfycat.com/PotableLeftAbalone-mobile.mp4",
            "https://giphy.com/gifs/omaze-chris-pratt-dinosaurs-l0NhZ0aUSE8fXag12",
            "http://giphy.com/embed/l0NhZ0aUSE8fXag12?html5=true",
            "https://i.giphy.com/iWZt0CknIElFu.gif",
            "https://media.giphy.com/media/PNSCRvHld1eP6/giphy.gif",
            "https://media.giphy.com/media/12X320LRlo6k5a/giphy.gif", // No MP4 urls, only GIF urls
            "https://imgur.com/gallery/WKauF",  // Album with gallery URL
            "http://imgur.com/awsGf9p",
            "http://imgur.com/gallery/0Kqrz",
            "http://imgur.com/a/cvehZ",
            "http://i.imgur.com/sCjRLQG.jpg?1",
            "https://imgur.com/gallery/PBTrqAA",
            "http://imgur.com/gallery/mhcWa37/new",
            "https://imgur.com/zzaVA8m",
            "https://imgur.com/a/cU6rs",     // Static image album
            "http://i.imgur.com/0t3yWP9.gifv", // direct gifv
            "http://i.imgur.com/0t3yWP9t.jpg", // Thumbnail URL
            "https://streamable.com/hm0i?t=0.2",
            "https://cdn.streamable.com/video/mp4/w78y.mp4",
            "https://cdn.streamable.com/video/mp4-mobile/ghju.mp4",
            "https://streamable.com/7k1v",
            "https://www.vidble.com/album/cfQZodMa",
            "https://www.vidble.com/album/7wOKVlEE",
            "https://www.vidble.com/show/8Ac2nQ4gJr", // GIF content
            "https://vidble.com/7VRBiHVRco_med.jpg", // GIF with JPG extension
            "https://vid.me/2wl6",
            "https://vid.me/e/QUIs",
            "http://inkyshade.deviantart.com/art/Steven-is-my-Universe-524095996", // GIF
            "http://mlewin.deviantart.com/art/Cyclops-611427095",
            "http://m.xkcd.com/1728/",
            "http://www.xkcd.com/1722/#",
            "http://xkcd.com/1099/",
            "http://fbis251.tumblr.com/post/150135750508/",
            "http://fbis251.tumblr.com/post/150134742963/",
            "http://aatkaw.tumblr.com/post/150140358556/hana",
    };
    // @formatter:on

    public static boolean testAlbumParser(final OkHttpClient client) {
        AlbumParser albumParser = new AlbumParser.Builder()
                .okHttpClient(client)
                .build();
        int count = 0;
        List<String> errorUrls = new ArrayList<>();
        for (String testUrl : TEST_URLS) {
            System.out.println("Testing " + testUrl);
            try {
                ParserResponse parserResponse = albumParser.parseUrl(testUrl);
                URL originalUrl = parserResponse.getOriginalUrl();
                if (parserResponse.isSingleMedia()) {
                    IMedia media = parserResponse.getMedia();
                    System.out.println(getMediaInfo(originalUrl, media));
                } else {
                    // Album
                    IApiResponse apiResponse = parserResponse.getApiResponse();
                    if (apiResponse == null) {
                        System.err.println("Null API response for url = [" + testUrl + "]");
                    }

                    System.out.println("Original URL: " + originalUrl);
                    testApiResponse(originalUrl, apiResponse);
                }
            } catch (Exception e) {
                System.err.println("Error: " + testUrl + " " + e.getMessage());
                e.printStackTrace();
                errorUrls.add(testUrl);
            }
            count++;
        }

        System.out.printf("%d/%d done\n", count, TEST_URLS.length);
        System.out.printf("URLs with errors: %d\n", errorUrls.size());
        for (String errorUrl : errorUrls) {
            System.out.println("\t" + errorUrl);
        }
        return true;
    }

    protected static String getMediaInfo(URL originalUrl, IMedia media) {
        if (media == null) return originalUrl + " Error: media is null";
        return originalUrl
                + " Media{"
                + "Title='"
                + media.getTitle()
                + '\''
                + ", Description='"
                + media.getDescription()
                + '\''
                + ", isVideo='"
                + media.isVideo()
                + '\''
                + ", highQuality='"
                + media.getUrl(true)
                + '\''
                + ", highStats='"
                + getVideoInfo(media, true)
                + '\''
                + ", lowQuality='"
                + media.getUrl(false)
                + '\''
                + ", lowStats='"
                + getVideoInfo(media, false)
                + '\''
                + ", preview='"
                + media.getPreviewUrl()
                + '\''
                + '}';
    }

    protected static void testApiResponse(URL originalUrl, IApiResponse<IMedia> apiResponse) {
        if (apiResponse == null) return;
        if (!apiResponse.isSuccessful()) {
            System.err.println("API Error");
            if (apiResponse.getErrorMessage() == null) return;
            System.err.println(apiResponse.getErrorMessage());
            return;
        }

        System.out.println("Preview URL: " + apiResponse.getPreviewUrl());

        if (apiResponse.isAlbum()) {
            System.out.println("API returned an album");
            IMediaAlbum<IMedia> album = apiResponse.getAlbum();
            if (album == null) {
                System.err.println("Response had a null album: " + originalUrl);
                return;
            }
            System.out.println("Album media count: " + album.getCount());
            for (IMedia media : album.getAlbumMedia()) {
                System.out.println(getMediaInfo(originalUrl, media));
            }
        } else {
            System.out.println("Response not album");
            IMedia media = apiResponse.getMedia();
            System.out.println(getMediaInfo(originalUrl, media));
        }

        System.out.println(SEPARATOR);
    }

    private static String getVideoInfo(IMedia media, boolean highQuality) {
        if (media == null) return null;
        String result = "";
        if (media.getWidth(highQuality) > 0) {
            result += String.format("%dw", media.getWidth(highQuality));
        }
        if (media.getHeight(highQuality) > 0) {
            result += String.format(" %dh", media.getHeight(highQuality));
        }
        if (media.getDuration() > 0) result += String.format(" %.1fs", media.getDuration());
        if (media.getByteSize(highQuality) > 0) {
            result += " " + ParseUtils.getSizeInMbString(media.getByteSize(highQuality)) + "MB";
        }
        return result;
    }
}
