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

package com.fernandobarillas.albumparser.media;

import java.net.URL;

/**
 * Created by fb on 5/10/16.
 */
public interface IMediaResponse {
    /**
     * @return An album returned by the API, null if no album is available
     */
    IMediaAlbum getAlbum();

    /**
     * @return The domain name of the API provider, for example: imgur.com, streamable.com, gfycat.com
     */
    String getApiDomain();

    /**
     * @return The error message returned by an API response, null if no error message was returned
     */
    String getErrorMessage();

    /**
     * @return Gets the unique hash to this media for calls made to the API provider
     */
    String getHash();

    /**
     * Sets the hash that was used to make the API request. This is extremely important to set if you want to keep track
     * of where the response data came from
     */
    void setHash(String hash);

    /**
     * @return A direct link to the media returned by the API, null if no direct media link was returned by the API
     */
    IMedia getMedia();

    /**
     * @return The original URL that was made before the API request was made. If you want to keep track of the original
     * URL you need to set it using {@link #setOriginalUrl(String)}
     */
    String getOriginalUrlString();

    /**
     * @return A URL to the media's preview image, null if no preview is available
     */
    URL getPreviewUrl();

    /**
     * @return True when the API response contains multiple video/images in an album, false otherwise
     */
    boolean isAlbum();

    /**
     * @return True when the API request was successful, false otherwise
     */
    boolean isSuccessful();

    /**
     * @param originalUrl Sets the original URL used by the media before the request was made to the API. For example,
     *                    an Imgur original URL might be: https://imgur.com/gallery/PBTrqAA
     */
    void setOriginalUrl(String originalUrl);
}
