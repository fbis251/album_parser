package com.fernandobarillas.albumparser.tumblr.model;

import com.fernandobarillas.albumparser.media.BaseMedia;
import com.fernandobarillas.albumparser.tumblr.api.TumblrApi;
import com.fernandobarillas.albumparser.util.ParseUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Photo extends BaseMedia {

    @SerializedName("caption")
    @Expose
    public String caption;
    @SerializedName("alt_sizes")
    @Expose
    public List<AltSize> altSizes = new ArrayList<>();
    @SerializedName("original_size")
    @Expose
    public OriginalSize originalSize;

    private AltSize mLowQuality;
    private AltSize mPreview;

    @Override
    public String getDescription() {
        return caption;
    }

    @Override
    public int getHeight(boolean highQuality) {
        if (highQuality) return originalSize != null ? originalSize.height : SIZE_UNAVAILABLE;
        parseAltSizes();
        return mLowQuality != null ? mLowQuality.height : SIZE_UNAVAILABLE;
    }

    @Override
    public URL getPreviewUrl() {
        parseAltSizes();
        if (mPreview != null) return ParseUtils.getUrlObject(mPreview.url);
        return null;
    }

    @Override
    public URL getUrl(boolean highQuality) {
        if (highQuality && originalSize != null) {
            return ParseUtils.getUrlObject(originalSize.url);
        }
        parseAltSizes();
        return mLowQuality != null ? ParseUtils.getUrlObject(mLowQuality.url) : null;
    }

    @Override
    public int getWidth(boolean highQuality) {
        if (highQuality) return originalSize != null ? originalSize.width : SIZE_UNAVAILABLE;
        parseAltSizes();
        return mLowQuality != null ? mLowQuality.width : SIZE_UNAVAILABLE;
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
