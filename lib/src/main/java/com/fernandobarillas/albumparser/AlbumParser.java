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
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.fernandobarillas.albumparser;

import com.fernandobarillas.albumparser.deviantart.DeviantartParser;
import com.fernandobarillas.albumparser.deviantart.api.DeviantartApi;
import com.fernandobarillas.albumparser.exception.InvalidMediaUrlException;
import com.fernandobarillas.albumparser.gfycat.GfycatParser;
import com.fernandobarillas.albumparser.gfycat.api.GfycatApi;
import com.fernandobarillas.albumparser.giphy.GiphyParser;
import com.fernandobarillas.albumparser.giphy.api.GiphyApi;
import com.fernandobarillas.albumparser.imgur.ImgurParser;
import com.fernandobarillas.albumparser.imgur.api.ImgurApi;
import com.fernandobarillas.albumparser.media.DirectMedia;
import com.fernandobarillas.albumparser.parser.ParserResponse;
import com.fernandobarillas.albumparser.streamable.StreamableParser;
import com.fernandobarillas.albumparser.streamable.api.StreamableApi;
import com.fernandobarillas.albumparser.util.ParseUtils;
import com.fernandobarillas.albumparser.vidble.VidbleParser;
import com.fernandobarillas.albumparser.vidble.api.VidbleApi;
import com.fernandobarillas.albumparser.vidme.VidmeParser;
import com.fernandobarillas.albumparser.vidme.api.VidmeApi;
import com.fernandobarillas.albumparser.xkcd.XkcdParser;
import com.fernandobarillas.albumparser.xkcd.api.XkcdApi;

import java.io.IOException;
import java.net.URL;

import okhttp3.OkHttpClient;

import static com.fernandobarillas.albumparser.util.ParseUtils.isDirectUrl;

/**
 * Class that facilitates parsing API responses from various image/video hosting services. This
 * class makes it as easy as passing in a URL and receiving a response with either an album or media
 * that the API returned, regardless of which API provider was used. All responses conform to the
 * interfaces in the media package so iterating and parsing the returned Objects is simple, reducing
 * code redundancy.
 */
public class AlbumParser {

    private static final int UNKNOWN    = -1;
    private static final int DIRECT     = 0;
    private static final int DEVIANTART = 1;
    private static final int GFYCAT     = 2;
    private static final int GIPHY      = 3;
    private static final int IMGUR      = 4;
    private static final int STREAMABLE = 5;
    private static final int VIDBLE     = 6;
    private static final int VIDME      = 7;
    private static final int XKCD       = 8;

    /** The OkHttpClient instance to use when making all the API calls */
    private OkHttpClient mClient;

    /**
     * Instantiates an AlbumParser instance. The library will use its own OkHttpClient instance to
     * make all the HTTP calls.
     */
    public AlbumParser() {
        mClient = new OkHttpClient();
    }

    /**
     * Instantiates an AlbumParser instance with the passed in OkHttpClient. This is useful when
     * you're setting custom headers such as the username. You can also set up a Proxy in the client
     * before passing it into this class.
     *
     * @param client The OkHttpClient instance to use with all the HTTP calls this library makes
     */
    public AlbumParser(OkHttpClient client) {
        if (client != null) mClient = client;
    }

    /**
     * Checks whether a particular URL is supported by this library
     *
     * @param url The URL to check for support
     * @return True if the URL can be parsed by this library, false if the URL is unsupported
     */
    public static boolean isSupported(String url) {
        return isSupported(ParseUtils.getUrlObject(url));
    }

    /**
     * Checks whether a particular URL is supported by this library
     *
     * @param url The URL to check for support
     * @return True if the URL can be parsed by this library, false if the URL is unsupported
     */
    public static boolean isSupported(URL url) {
        return getMediaProvider(url) != UNKNOWN;
    }

    /**
     * @param urlString The URL to parse and receive data for
     * @return The API response for the passed-in URL.
     * @throws IOException              When there are any network issues such as a host not being
     *                                  reached.
     * @throws InvalidMediaUrlException When the passed-in URL is not supported by this library and
     *                                  cannot be parsed
     */
    public ParserResponse parseUrl(String urlString) throws IOException, InvalidMediaUrlException {
        URL mediaUrl = new URL(urlString);

        try {
            switch (getMediaProvider(mediaUrl)) {
                case DEVIANTART:
                    return new DeviantartParser(mClient).parse(mediaUrl);
                case GFYCAT:
                    return new GfycatParser(mClient).parse(mediaUrl);
                case GIPHY:
                    return new GiphyParser().parse(mediaUrl);
                case IMGUR:
                    return new ImgurParser(mClient).parse(mediaUrl);
                case STREAMABLE:
                    return new StreamableParser(mClient).parse(mediaUrl);
                case VIDBLE:
                    return new VidbleParser(mClient).parse(mediaUrl);
                case VIDME:
                    return new VidmeParser(mClient).parse(mediaUrl);
                case XKCD:
                    return new XkcdParser(mClient).parse(mediaUrl);
                case DIRECT:
                    return new ParserResponse(new DirectMedia(mediaUrl));
                case UNKNOWN:
                default:
                    // Media is not supported or a URL that doesn't point to any media passed in
                    throw new InvalidMediaUrlException(mediaUrl);
            }
        } catch (InvalidMediaUrlException e) {
            // The API parsers could not get a response, try to return a DirectMedia if possible
            if (isDirectUrl(mediaUrl)) {
                return new ParserResponse(new DirectMedia(mediaUrl));
            } else {
                throw e;
            }
        }
    }

    private static int getMediaProvider(URL url) {
        if (url == null || url.getHost() == null) return UNKNOWN;
        String domain = url.getHost().toLowerCase();

        if (isDomainMatch(domain, DeviantartApi.BASE_DOMAIN)) {
            return DEVIANTART;
        }
        if (isDomainMatch(domain, GfycatApi.BASE_DOMAIN)) {
            return GFYCAT;
        }
        if (isDomainMatch(domain, GiphyApi.BASE_DOMAIN)) {
            return GIPHY;
        }
        if (isDomainMatch(domain, ImgurApi.BASE_DOMAIN)) {
            return IMGUR;
        }
        if (isDomainMatch(domain, StreamableApi.BASE_DOMAIN)) {
            return STREAMABLE;
        }
        if (isDomainMatch(domain, VidbleApi.BASE_DOMAIN)) {
            return VIDBLE;
        }
        if (isDomainMatch(domain, VidmeApi.BASE_DOMAIN)) {
            return VIDME;
        }
        if (isDomainMatch(domain, XkcdApi.BASE_DOMAIN)) {
            return XKCD;
        }
        if (isDirectUrl(url)) {
            return DIRECT;
        }

        return UNKNOWN;
    }

    private static boolean isDomainMatch(String domain, String providerDomain) {
        return domain.endsWith("." + providerDomain) || domain.equals(providerDomain);
    }
}
