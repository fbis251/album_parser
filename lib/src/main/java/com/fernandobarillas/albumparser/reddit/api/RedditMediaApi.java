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

package com.fernandobarillas.albumparser.reddit.api;

/**
 * Retrofit Interface for the reddit API
 */
public interface RedditMediaApi {
    // No trailing slash!
    String REDD_IT_DOMAIN         = "i.redd.it";
    String REDDITMEDIA_I_DOMAIN   = "i.redditmedia.com";
    String REDDITMEDIA_G_DOMAIN   = "g.redditmedia.com";
    String REDDITUPLOADS_I_DOMAIN = "i.reddituploads.com";

    String[] VALID_DOMAINS = {
            REDD_IT_DOMAIN, REDDITMEDIA_G_DOMAIN, REDDITMEDIA_I_DOMAIN, REDDITUPLOADS_I_DOMAIN
    };
    // No API implementation
}
