package com.fernandobarillas.albumparser.tumblr.model;

import com.fernandobarillas.albumparser.tumblr.api.TumblrApi;
import com.fernandobarillas.albumparser.util.ParseUtils;
import com.squareup.moshi.Json;

import java.net.URL;
import java.util.List;

public class Photo extends BaseTumblrMedia {

    @Json(name = "caption")
    public String        caption;
    @Json(name = "alt_sizes")
    public List<AltSize> altSizes;
    @Json(name = "original_size")
    public OriginalSize  originalSize;

    private AltSize mLowQuality;
    private AltSize mPreview;

    @Override
    public String getDescription() {
        return caption;
    }

    @Override
    public int getHeight(boolean highQuality) {
        parseAltSizes();
        BaseSize baseSize = highQuality ? originalSize : mLowQuality;
        return baseSize != null ? defaultSizeIfNull(baseSize.height) : SIZE_UNAVAILABLE;
    }

    @Override
    public URL getPreviewUrl() {
        parseAltSizes();
        return mPreview != null ? ParseUtils.getUrlObject(mPreview.url) : null;
    }

    @Override
    public URL getUrl(boolean highQuality) {
        parseAltSizes();
        BaseSize baseSize = highQuality ? originalSize : mLowQuality;
        return baseSize != null ? ParseUtils.getUrlObject(baseSize.url) : null;
    }

    @Override
    public int getWidth(boolean highQuality) {
        parseAltSizes();
        BaseSize baseSize = highQuality ? originalSize : mLowQuality;
        return baseSize != null ? defaultSizeIfNull(baseSize.width) : SIZE_UNAVAILABLE;
    }

    @Override
    public String toString() {
        return "Photo{"
                + "caption='"
                + caption
                + '\''
                + ", originalSize="
                + originalSize
                + ", mLowQuality="
                + mLowQuality
                + ", mPreview="
                + mPreview
                + '}';
    }

    private void parseAltSizes() {
        if (altSizes == null) return;
        if (mLowQuality != null && mPreview != null) return;
        for (AltSize altSize : altSizes) {
            if (altSize.width <= TumblrApi.LOW_QUALITY_WIDTH && mLowQuality == null) {
                mLowQuality = altSize;
            }
            if (altSize.width <= TumblrApi.PREVIEW_WIDTH && mPreview == null) {
                mPreview = altSize;
            }
        }
    }
}
