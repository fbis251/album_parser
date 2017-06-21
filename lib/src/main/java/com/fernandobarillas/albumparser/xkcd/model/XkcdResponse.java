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

package com.fernandobarillas.albumparser.xkcd.model;

import com.fernandobarillas.albumparser.media.BaseApiResponse;
import com.squareup.moshi.Json;

public class XkcdResponse extends BaseApiResponse<XkcdImage> {

    @Json(name = "month")
    public String  month;
    @Json(name = "num")
    public Integer num;
    @Json(name = "link")
    public String  link;
    @Json(name = "year")
    public String  year;
    @Json(name = "news")
    public String  news;
    @Json(name = "safe_title")
    public String  safeTitle;
    @Json(name = "transcript")
    public String  transcript;
    @Json(name = "alt")
    public String  alt;
    @Json(name = "img")
    public String  img;
    @Json(name = "title")
    public String  title;
    @Json(name = "day")
    public String  day;

    private XkcdImage mImage;

    @Override
    public XkcdImage getMedia() {
        if (mImage == null) {
            mImage = new XkcdImage(title, alt, img);
        }

        return mImage;
    }

    @Override
    public boolean isSuccessful() {
        return img != null;
    }
}
