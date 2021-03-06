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

package com.fernandobarillas.albumparser.media;

import java.net.URL;

/**
 * Class that sets default values for the IMedia interface
 */
public class BaseMedia implements IMedia {

    @Override
    public int getByteSize(boolean highQuality) {
        return SIZE_UNAVAILABLE;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public double getDuration() {
        return DURATION_UNAVAILABLE;
    }

    @Override
    public int getHeight(boolean highQuality) {
        return SIZE_UNAVAILABLE;
    }

    @Override
    public URL getPreviewUrl() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public URL getUrl(boolean highQuality) {
        return null;
    }

    @Override
    public int getWidth(boolean highQuality) {
        return SIZE_UNAVAILABLE;
    }

    @Override
    public boolean isGif() {
        return false;
    }

    @Override
    public boolean isVideo() {
        return false;
    }

    @Override
    public String toString() {
        return " Media{"
                + "Title='"
                + getTitle()
                + '\''
                + ", Description='"
                + getDescription()
                + '\''
                + ", isGif='"
                + isGif()
                + '\''
                + ", isVideo='"
                + isVideo()
                + '\''
                + ", highQuality='"
                + getUrl(true)
                + '\''
                + ", lowQuality='"
                + getUrl(false)
                + '\''
                + ", preview='"
                + getPreviewUrl()
                + '\''
                + '}';
    }

    protected double defaultDurationIfNull(Double doubleValue) {
        return doubleValue != null ? doubleValue : (double) DURATION_UNAVAILABLE;
    }

    protected int defaultSizeIfNull(Integer integerValue) {
        return integerValue != null ? integerValue : SIZE_UNAVAILABLE;
    }
}
