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

import com.fernandobarillas.albumparser.media.IMedia;
import com.fernandobarillas.albumparser.media.IMediaAlbum;
import com.fernandobarillas.albumparser.media.IMediaResponse;
import com.fernandobarillas.albumparser.util.ParseUtils;

/**
 * Created by fb on 5/11/16.
 */
public class MediaTest {
    protected final static String SEPARATOR = "\n--------------------\n";

    public static void testResponse(IMediaResponse mediaResponse) {
        System.out.println("Response from " + mediaResponse.getApiDomain() + " API");
        System.out.println("Original URL: " + mediaResponse.getOriginalUrlString());
        System.out.println("Preview URL: " + mediaResponse.getPreviewUrl());
        String hash = mediaResponse.getHash();

        if (mediaResponse.isAlbum()) {
            System.out.println("API returned an album");
            IMediaAlbum album = mediaResponse.getAlbum();
            if (album == null) {
                System.err.println("Response had a null album: " + mediaResponse.getHash());
                return;
            }
            System.out.println("Album media count: " + album.getCount());
            for (IMedia media : album.getAlbumMedia()) {
                System.out.println(getMediaInfo(hash, media));
            }
        } else {
            System.out.println("Response not album");
            IMedia media = mediaResponse.getMedia();
            System.out.println(getMediaInfo(hash, media));
        }

        System.out.println(SEPARATOR);
    }

    public static String getMediaInfo(String hash, IMedia media) {
        if (media == null) return hash + " Error: media is null";
        return hash + " Media{" +
                "Title='" + media.getTitle() + '\'' +
                ", Description='" + media.getDescription() + '\'' +
                ", isVideo='" + media.isVideo() + '\'' +
                ", highQuality='" + media.getUrl(true) + '\'' +
                ", highStats='" + getVideoInfo(media, true) + '\'' +
                ", lowQuality='" + media.getUrl(false) + '\'' +
                ", lowStats='" + getVideoInfo(media, false) + '\'' +
                '}';
    }

    private static String getVideoInfo(IMedia media, boolean highQuality) {
        if (media == null) return null;
        String result = "";
        if (media.getWidth(highQuality) > 0) result += String.format("%dw", media.getWidth(highQuality));
        if (media.getHeight(highQuality) > 0) result += String.format(" %dh", media.getHeight(highQuality));
        if (media.getDuration() > 0) result += String.format(" %.1fs", media.getDuration());
        if (media.getByteSize(highQuality) > 0)
            result += " " + ParseUtils.getSizeInMbString(media.getByteSize(highQuality)) + "MB";
        return result;
    }
}
