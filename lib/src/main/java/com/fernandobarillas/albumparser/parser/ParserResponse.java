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

package com.fernandobarillas.albumparser.parser;

import com.fernandobarillas.albumparser.media.IApiResponse;
import com.fernandobarillas.albumparser.media.IMedia;

/**
 * Class that facilitates getting API responses, regardless of which API returned them. This class
 * will always try to return either a full API response when a call was made to the API, or it will
 * return an IMedia Object with a URL that links directly to video or an image
 */
public class ParserResponse {
    private IApiResponse mApiResponse;
    private IMedia       mMedia;

    /**
     * Constructor for direct IMedia objects
     *
     * @param media The media to return in this response
     */
    public ParserResponse(IMedia media) {
        mMedia = media;
    }

    /**
     * Constructor for full API IApiResponse. These can contain one or several IMedia objects or a
     * full album in them.
     *
     * @param apiResponse The API response to return in this response
     */
    public ParserResponse(IApiResponse apiResponse) {
        mApiResponse = apiResponse;
    }

    @Override
    public String toString() {
        return "ParserResponse{" + "mApiResponse=" + mApiResponse + ", mMedia=" + mMedia + '}';
    }

    /**
     * @return The IApiResponse returned by the parser
     */
    public IApiResponse getApiResponse() {
        return mApiResponse;
    }

    /**
     * @return The IMedia returned by the parser
     */
    public IMedia getMedia() {
        if (mMedia != null) return mMedia;
        if (mApiResponse != null && !mApiResponse.isAlbum()) {
            return mApiResponse.getMedia();
        }

        return null;
    }

    /**
     * @return True when there is only a single media, false when there is a full response from the
     * API available
     */
    public boolean isSingleMedia() {
        return mMedia != null;
    }
}
