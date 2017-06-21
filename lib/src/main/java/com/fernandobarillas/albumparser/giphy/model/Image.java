package com.fernandobarillas.albumparser.giphy.model;

import com.fernandobarillas.albumparser.media.BaseMedia;
import com.fernandobarillas.albumparser.util.ParseUtils;
import com.squareup.moshi.Json;

import java.net.URL;

import static com.fernandobarillas.albumparser.util.ParseUtils.getUrlObject;

public class Image extends BaseMedia {

    @Json(name = "fixed_height")
    public ImageVariation fixedHeight;
    @Json(name = "fixed_height_still")
    public ImageVariation fixedHeightStill;
    @Json(name = "fixed_height_downsampled")
    public ImageVariation fixedHeightDownsampled;
    @Json(name = "fixed_width")
    public ImageVariation fixedWidth;
    @Json(name = "fixed_width_still")
    public ImageVariation fixedWidthStill;
    @Json(name = "fixed_width_downsampled")
    public ImageVariation fixedWidthDownsampled;
    @Json(name = "fixed_height_small")
    public ImageVariation fixedHeightSmall;
    @Json(name = "fixed_height_small_still")
    public ImageVariation fixedHeightSmallStill;
    @Json(name = "fixed_width_small")
    public ImageVariation fixedWidthSmall;
    @Json(name = "fixed_width_small_still")
    public ImageVariation fixedWidthSmallStill;
    @Json(name = "downsized")
    public ImageVariation downsized;
    @Json(name = "downsized_still")
    public ImageVariation downsizedStill;
    @Json(name = "downsized_large")
    public ImageVariation downsizedLarge;
    @Json(name = "downsized_medium")
    public ImageVariation downsizedMedium;
    @Json(name = "original")
    public ImageVariation original;
    @Json(name = "original_still")
    public ImageVariation originalStill;
    @Json(name = "looping")
    public ImageVariation looping;

    private URL mp4HighQualityUrl;
    private URL mp4LowQualityUrl;

    @Override
    public int getByteSize(boolean highQuality) {
        ImageVariation variation = highQuality ? original : fixedHeight;
        return (variation != null) ? defaultSizeIfNull(variation.mp4Size) : SIZE_UNAVAILABLE;
    }

    @Override
    public int getHeight(boolean highQuality) {
        ImageVariation variation = highQuality ? original : fixedHeight;
        return (variation != null) ? defaultSizeIfNull(variation.height) : SIZE_UNAVAILABLE;
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
        return (variation != null) ? defaultSizeIfNull(variation.width) : SIZE_UNAVAILABLE;
    }

    @Override
    public boolean isVideo() {
        // Giphy only has GIFs/Videos
        return true;
    }
}
