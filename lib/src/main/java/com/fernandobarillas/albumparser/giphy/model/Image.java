package com.fernandobarillas.albumparser.giphy.model;

import com.fernandobarillas.albumparser.media.BaseMedia;
import com.fernandobarillas.albumparser.util.ParseUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.URL;

import static com.fernandobarillas.albumparser.util.ParseUtils.getUrlObject;

public class Image extends BaseMedia {

    @SerializedName("fixed_height")
    @Expose
    public ImageVariation fixedHeight;
    @SerializedName("fixed_height_still")
    @Expose
    public ImageVariation fixedHeightStill;
    @SerializedName("fixed_height_downsampled")
    @Expose
    public ImageVariation fixedHeightDownsampled;
    @SerializedName("fixed_width")
    @Expose
    public ImageVariation fixedWidth;
    @SerializedName("fixed_width_still")
    @Expose
    public ImageVariation fixedWidthStill;
    @SerializedName("fixed_width_downsampled")
    @Expose
    public ImageVariation fixedWidthDownsampled;
    @SerializedName("fixed_height_small")
    @Expose
    public ImageVariation fixedHeightSmall;
    @SerializedName("fixed_height_small_still")
    @Expose
    public ImageVariation fixedHeightSmallStill;
    @SerializedName("fixed_width_small")
    @Expose
    public ImageVariation fixedWidthSmall;
    @SerializedName("fixed_width_small_still")
    @Expose
    public ImageVariation fixedWidthSmallStill;
    @SerializedName("downsized")
    @Expose
    public ImageVariation downsized;
    @SerializedName("downsized_still")
    @Expose
    public ImageVariation downsizedStill;
    @SerializedName("downsized_large")
    @Expose
    public ImageVariation downsizedLarge;
    @SerializedName("downsized_medium")
    @Expose
    public ImageVariation downsizedMedium;
    @SerializedName("original")
    @Expose
    public ImageVariation original;
    @SerializedName("original_still")
    @Expose
    public ImageVariation originalStill;
    @SerializedName("looping")
    @Expose
    public ImageVariation looping;

    private URL mp4HighQualityUrl;
    private URL mp4LowQualityUrl;

    @Override
    public int getByteSize(boolean highQuality) {
        ImageVariation variation = highQuality ? original : fixedHeight;
        return (variation != null) ? variation.mp4Size : SIZE_UNAVAILABLE;
    }

    @Override
    public int getHeight(boolean highQuality) {
        ImageVariation variation = highQuality ? original : fixedHeight;
        return (variation != null) ? variation.height : SIZE_UNAVAILABLE;
    }

    @Override
    public URL getPreviewUrl() {
        return originalStill != null ? getUrlObject(originalStill.url) : null;
    }

    @Override
    public URL getUrl(boolean highQuality) {
        if (highQuality) {
            if (mp4HighQualityUrl != null) return mp4HighQualityUrl;
            if (original != null) {
                if (original.mp4 != null && !original.mp4.isEmpty()) {
                    mp4HighQualityUrl = ParseUtils.getUrlObject(original.mp4);
                } else if (original.url != null && !original.url.isEmpty()) {
                    String newMp4Url = original.url.replace(EXT_GIF, EXT_MP4);
                    mp4HighQualityUrl = ParseUtils.getUrlObject(newMp4Url);
                }
            }
        } else {
            if (mp4LowQualityUrl != null) return mp4LowQualityUrl;
            if (fixedHeight != null && fixedHeight.mp4 != null && !fixedHeight.mp4.isEmpty()) {
                mp4LowQualityUrl = ParseUtils.getUrlObject(fixedHeight.mp4);
            }
        }

        return highQuality ? mp4HighQualityUrl : mp4LowQualityUrl;
    }

    @Override
    public int getWidth(boolean highQuality) {
        ImageVariation variation = highQuality ? original : fixedHeight;
        return (variation != null) ? variation.width : SIZE_UNAVAILABLE;
    }

    @Override
    public boolean isVideo() {
        // Giphy only has GIFs/Videos
        return true;
    }
}
