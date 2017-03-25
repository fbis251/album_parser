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
import com.fernandobarillas.albumparser.eroshare.EroshareParser;
import com.fernandobarillas.albumparser.exception.InvalidApiKeyException;
import com.fernandobarillas.albumparser.exception.InvalidApiResponseException;
import com.fernandobarillas.albumparser.exception.InvalidMediaUrlException;
import com.fernandobarillas.albumparser.gfycat.GfycatParser;
import com.fernandobarillas.albumparser.giphy.GiphyParser;
import com.fernandobarillas.albumparser.imgur.ImgurParser;
import com.fernandobarillas.albumparser.imgur.model.Image;
import com.fernandobarillas.albumparser.media.DirectMedia;
import com.fernandobarillas.albumparser.parser.ParserResponse;
import com.fernandobarillas.albumparser.reddit.RedditParser;
import com.fernandobarillas.albumparser.streamable.StreamableParser;
import com.fernandobarillas.albumparser.tumblr.TumblrParser;
import com.fernandobarillas.albumparser.vidble.VidbleParser;
import com.fernandobarillas.albumparser.vidme.VidmeParser;
import com.fernandobarillas.albumparser.xkcd.XkcdParser;

import java.io.IOException;
import java.net.URL;

import okhttp3.OkHttpClient;

import static com.fernandobarillas.albumparser.util.ParseUtils.getUrlObject;
import static com.fernandobarillas.albumparser.util.ParseUtils.isImageExtension;
import static com.fernandobarillas.albumparser.util.ParseUtils.isVideoExtension;

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
    private static final int DIRECT_GIF = 1;
    private static final int DEVIANTART = 2;
    private static final int EROSHARE   = 3;
    private static final int GFYCAT     = 4;
    private static final int GIPHY      = 5;
    private static final int IMGUR      = 6;
    private static final int REDDIT     = 7;
    private static final int STREAMABLE = 8;
    private static final int VIDBLE     = 9;
    private static final int VIDME      = 10;
    private static final int TUMBLR     = 11;
    private static final int XKCD       = 12;

    /** The OkHttpClient instance to use when making all the API calls */
    private OkHttpClient mClient;

    // API Keys
    private String mGiphyApiKey;
    private String mImgurClientId;
    private String mTumblrApiKey;

    // Default preview and low quality URL dimensions/sizes
    private String mImgurPreviewSize;
    private String mImgurLowQualitySize;

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
        return isSupported(getUrlObject(url));
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

    private static int getMediaProvider(URL url) {
        if (url == null || url.getHost() == null) return UNKNOWN;
        String domain = url.getHost().toLowerCase();

        if (new DeviantartParser().canParse(url)) {
            return DEVIANTART;
        }
        if (new EroshareParser().canParse(url)) {
            return EROSHARE;
        }
        if (new GfycatParser().canParse(url)) {
            return GFYCAT;
        }
        if (new GiphyParser().canParse(url)) {
            return GIPHY;
        }
        if (new ImgurParser().canParse(url)) {
            return IMGUR;
        }
        if (new RedditParser().canParse(url)) {
            return REDDIT;
        }
        if (new StreamableParser().canParse(url)) {
            return STREAMABLE;
        }
        if (new VidbleParser().canParse(url)) {
            return VIDBLE;
        }
        if (new VidmeParser().canParse(url)) {
            return VIDME;
        }
        if (new TumblrParser().canParse(url)) {
            return TUMBLR;
        }
        if (new XkcdParser().canParse(url)) {
            return XKCD;
        }
        if (isImageExtension(url) || isVideoExtension(url)) {
            return DIRECT;
        }

        return UNKNOWN;
    }

    /**
     * @return The OkHttpClient instance that the library is using for its HTTP calls.
     */
    public OkHttpClient getClient() {
        return mClient;
    }

    /**
     * @return The API key the library is using for its Giphy API calls
     */
    public String getGiphyApiKey() {
        return mGiphyApiKey;
    }

    /**
     * Gets the client ID token that will be used when making requests to the Imgur API.
     *
     * @return The Imgur Client ID if one was set by {@link #setImgurClientId(String)}, null
     * otherwise
     */
    public String getImgurClientId() {
        return mImgurClientId;
    }

    /**
     * @return The API key used to make calls to the Tumblr API
     */
    public String getTumblrApiKey() {
        return mTumblrApiKey;
    }

    /**
     * @param urlString The URL to parse and receive data for
     * @return The API response for the passed-in URL.
     * @throws IOException                 When there are any network issues such as a host not
     *                                     being reached.
     * @throws InvalidApiKeyException      When you attempt to use an API that requires a key
     *                                     without first setting the key
     * @throws InvalidApiResponseException When the API returns a null response or a response which
     *                                     the library could not parse.
     * @throws InvalidMediaUrlException    When the passed-in URL is not supported by this library
     *                                     and cannot be parsed
     */
    public ParserResponse parseUrl(String urlString)
            throws IOException, InvalidApiKeyException, InvalidApiResponseException,
            InvalidMediaUrlException {
        return parseUrl(getUrlObject(urlString));
    }

    /**
     * @param mediaUrl The URL to parse and receive data for
     * @return The API response for the passed-in URL.
     * @throws IOException                 When there are any network issues such as a host not
     *                                     being reached.
     * @throws InvalidApiKeyException      When you attempt to use an API that requires a key
     *                                     without first setting the key
     * @throws InvalidApiResponseException When the API returns a null response or a response which
     *                                     the library could not parse.
     * @throws InvalidMediaUrlException    When the passed-in URL is not supported by this library
     *                                     and cannot be parsed
     */
    public ParserResponse parseUrl(URL mediaUrl)
            throws IOException, InvalidApiKeyException, InvalidApiResponseException,
            InvalidMediaUrlException {
        switch (getMediaProvider(mediaUrl)) {
            case DEVIANTART:
                return new DeviantartParser(mClient).parse(mediaUrl);
            case EROSHARE:
                return new EroshareParser(mClient).parse(mediaUrl);
            case GFYCAT:
                return new GfycatParser(mClient).parse(mediaUrl);
            case GIPHY:
                return new GiphyParser(mClient, mGiphyApiKey).parse(mediaUrl);
            case IMGUR:
                ImgurParser imgurParser = new ImgurParser(mClient, mImgurClientId);
                if (mImgurLowQualitySize != null) {
                    imgurParser.setLowQualitySize(mImgurLowQualitySize);
                }
                if (mImgurPreviewSize != null) {
                    imgurParser.setPreviewSize(mImgurPreviewSize);
                }
                return imgurParser.parse(mediaUrl);
            case REDDIT:
                return new RedditParser().parse(mediaUrl);
            case STREAMABLE:
                return new StreamableParser(mClient).parse(mediaUrl);
            case VIDBLE:
                return new VidbleParser(mClient).parse(mediaUrl);
            case VIDME:
                return new VidmeParser(mClient).parse(mediaUrl);
            case TUMBLR:
                return new TumblrParser(mClient, mTumblrApiKey).parse(mediaUrl);
            case XKCD:
                return new XkcdParser(mClient).parse(mediaUrl);
            case DIRECT:
                return new ParserResponse(new DirectMedia(mediaUrl));
            case UNKNOWN:
            default:
                // Media is not supported or a URL that doesn't point to any media passed in
                throw new InvalidMediaUrlException(mediaUrl);
        }
    }

    /**
     * Sets the API key to use when making requests to the Giphy API
     *
     * @param giphyApiKey The API key to use when making requests to the Giphy API
     */
    public void setGiphyApiKey(String giphyApiKey) {
        mGiphyApiKey = giphyApiKey;
    }

    /**
     * Sets the client ID to use when making requests to the Imgur API
     *
     * @param imgurClientId The client ID to use when making requests to the Imgur API
     */
    public void setImgurClientId(String imgurClientId) {
        mImgurClientId = imgurClientId;
    }

    /**
     * Sets the default size of the low quality URL imgur returns
     *
     * @param lowQualitySize The default size of the low quality URL Imgur returns. Look at {@link
     *                       Image} for available sizes. Examples: {@link Image#ORIGINAL}, {@link
     *                       Image#GIANT_THUMBNAIL}
     */
    public void setImgurLowQualitySize(String lowQualitySize) {
        mImgurLowQualitySize = lowQualitySize;
    }

    /**
     * Sets the default size of the preview URL imgur returns
     *
     * @param previewSize The default size of the preview URL Imgur returns. Look at {@link Image}
     *                    for available sizes. Examples: {@link Image#BIG_SQUARE}, {@link
     *                    Image#MEDIUM_THUMBNAIL}
     */
    public void setImgurPreviewSize(String previewSize) {
        mImgurPreviewSize = previewSize;
    }

    /**
     * Sets the API key used to make calls to the Tumblr API. Notice, the Tumblr API will not send a
     * response unless you have first set the key using this method. If you attempt to make calls to
     * the Tumblr API with no key set, the parser will return an {@link InvalidApiKeyException}
     *
     * @param tumblrApiKey The key to use to make calls to the Tumblr API
     */
    public void setTumblrApiKey(String tumblrApiKey) {
        mTumblrApiKey = tumblrApiKey;
    }
}
