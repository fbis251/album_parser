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

package com.fernandobarillas.albumparser.reddit;

import com.fernandobarillas.albumparser.exception.InvalidMediaUrlException;
import com.fernandobarillas.albumparser.parser.AbstractApiParser;
import com.fernandobarillas.albumparser.parser.ParserResponse;
import com.fernandobarillas.albumparser.reddit.api.RedditMediaApi;
import com.fernandobarillas.albumparser.reddit.model.ReddItMedia;
import com.fernandobarillas.albumparser.reddit.model.RedditMediaMedia;
import com.fernandobarillas.albumparser.reddit.model.RedditUploadsMedia;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

/**
 * Parser for the reddit API
 */
public class RedditParser extends AbstractApiParser {

    @Override
    public String getApiUrl() {
        // RedditMedia doesn't have an API URL
        return null;
    }

    @Override
    public String getBaseDomain() {
        return RedditMediaApi.REDDITMEDIA_I_DOMAIN;
    }

    @Override
    public String getHash(URL mediaUrl) {
        if (isValidDomain(mediaUrl)) {
            // Reddit media has no API to get media information. Just return blank string if domain
            // was valid
            return "";
        }
        throw new InvalidMediaUrlException(mediaUrl);
    }

    @Override
    public Set<String> getValidDomains() {
        return RedditMediaApi.VALID_DOMAINS_SET;
    }

    @Override
    public ParserResponse parse(URL mediaUrl) throws IOException, RuntimeException {
        if (!isValidDomain(mediaUrl)) {
            throw new InvalidMediaUrlException(mediaUrl);
        }

        String domain = mediaUrl.getHost();
        if (domain.equalsIgnoreCase(RedditMediaApi.REDDITMEDIA_G_DOMAIN) || domain.equalsIgnoreCase(
                RedditMediaApi.REDDITMEDIA_I_DOMAIN)) {
            return new ParserResponse(new RedditMediaMedia(mediaUrl));
        } else if (domain.equalsIgnoreCase(RedditMediaApi.REDDITUPLOADS_I_DOMAIN)) {
            return new ParserResponse(new RedditUploadsMedia(mediaUrl));
        }

        // i.redd.it media
        return new ParserResponse(new ReddItMedia(mediaUrl));
    }
}
