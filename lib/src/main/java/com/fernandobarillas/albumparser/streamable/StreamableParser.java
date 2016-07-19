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

package com.fernandobarillas.albumparser.streamable;

import com.fernandobarillas.albumparser.parser.AbstractApiParser;
import com.fernandobarillas.albumparser.parser.ParserResponse;
import com.fernandobarillas.albumparser.exception.InvalidMediaUrlException;
import com.fernandobarillas.albumparser.streamable.api.StreamableApi;
import com.fernandobarillas.albumparser.streamable.model.Mp4;
import com.fernandobarillas.albumparser.streamable.model.Mp4Mobile;
import com.fernandobarillas.albumparser.streamable.model.StreamableMedia;
import com.fernandobarillas.albumparser.streamable.model.StreamableResponse;
import com.fernandobarillas.albumparser.util.ParseUtils;

import java.io.IOException;
import java.net.URL;

import okhttp3.OkHttpClient;
import retrofit2.Response;

/**
 * Parser for Streamable API responses
 */
public class StreamableParser extends AbstractApiParser {
    public StreamableParser(OkHttpClient client) {
        super(client);
    }

    @Override
    public ParserResponse parse(URL mediaUrl) throws InvalidMediaUrlException, IOException {
        String hash = StreamableUtils.getHash(mediaUrl.toString());
        if (hash == null) {
            throw new InvalidMediaUrlException();
        }

        if (ParseUtils.isDirectUrl(mediaUrl)) {
            Mp4 mp4 = new Mp4();
            Mp4Mobile mp4Mobile = new Mp4Mobile();
            mp4.url = StreamableUtils.getMp4Url(hash);
            mp4Mobile.url = StreamableUtils.getMp4MobileUrl(hash);
            StreamableMedia streamableMedia = new StreamableMedia(mp4, mp4Mobile);
            return new ParserResponse(streamableMedia);
        }

        StreamableApi service = getRetrofit(StreamableApi.API_URL).create(StreamableApi.class);
        Response<StreamableResponse> response = service.getVideo(hash).execute();
        StreamableResponse streamableResponse = response.body();
        streamableResponse.setHash(hash);
        streamableResponse.setOriginalUrl(mediaUrl.toString());

        return new ParserResponse(streamableResponse);
    }
}
