package com.fernandobarillas.albumparser.model;

import com.fernandobarillas.albumparser.media.BaseMedia;
import com.fernandobarillas.albumparser.util.ParseUtils;

import java.net.URL;

/**
 * Created by fb on 9/22/16.
 */
public class ExpectedMedia extends BaseMedia {

    private boolean mIsVideo;
    private boolean mIsGif;
    private double  mDuration;
    private int     mLowQualityByteSize;
    private int     mHighQualityByteSize;
    private int     mLowQualityHeight;
    private int     mHighQualityHeight;
    private int     mLowQualityWidth;
    private int     mHighQualityWidth;
    private String  mDescription;
    private String  mTitle;
    private URL     mPreviewUrl;
    private URL     mHighQualityUrl;
    private URL     mLowQualityUrl;

    private ExpectedMedia() {
        mDuration = DURATION_UNAVAILABLE;
        mLowQualityByteSize = SIZE_UNAVAILABLE;
        mHighQualityByteSize = SIZE_UNAVAILABLE;
        mLowQualityWidth = SIZE_UNAVAILABLE;
        mHighQualityWidth = SIZE_UNAVAILABLE;
        mLowQualityHeight = SIZE_UNAVAILABLE;
        mHighQualityHeight = SIZE_UNAVAILABLE;
    }

    @Override
    public int getByteSize(boolean highQuality) {
        return highQuality ? mHighQualityByteSize : mLowQualityByteSize;
    }

    @Override
    public String getDescription() {
        return mDescription;
    }

    @Override
    public double getDuration() {
        return mDuration;
    }

    @Override
    public int getHeight(boolean highQuality) {
        return highQuality ? mHighQualityHeight : mLowQualityHeight;
    }

    @Override
    public URL getPreviewUrl() {
        return mPreviewUrl;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public URL getUrl(boolean highQuality) {
        return highQuality ? mHighQualityUrl : mLowQualityUrl;
    }

    @Override
    public int getWidth(boolean highQuality) {
        return highQuality ? mHighQualityWidth : mLowQualityWidth;
    }

    @Override
    public boolean isGif() {
        return mIsGif;
    }

    @Override
    public boolean isVideo() {
        return mIsVideo;
    }

    public static class Builder {

        private ExpectedMedia mExpectedMedia;

        public Builder() {
            mExpectedMedia = new ExpectedMedia();
        }

        public ExpectedMedia build() {
            return mExpectedMedia;
        }

        public Builder setDescription(String description) {
            mExpectedMedia.mDescription = description;
            return this;
        }

        public Builder setDuration(double duration) {
            mExpectedMedia.mDuration = duration;
            return this;
        }

        public Builder setHighQualityByteSize(int highQualityByteSize) {
            mExpectedMedia.mHighQualityByteSize = highQualityByteSize;
            return this;
        }

        public Builder setHighQualityHeight(int highQualityHeight) {
            mExpectedMedia.mHighQualityHeight = highQualityHeight;
            return this;
        }

        public Builder setHighQualityUrl(URL highQualityUrl) {
            mExpectedMedia.mHighQualityUrl = highQualityUrl;
            return this;
        }

        public Builder setHighQualityUrl(String highQualityUrl) {
            return setHighQualityUrl(ParseUtils.getUrlObject(highQualityUrl));
        }

        public Builder setHighQualityWidth(int highQualityWidth) {
            mExpectedMedia.mHighQualityWidth = highQualityWidth;
            return this;
        }

        public Builder setIsGif(boolean isGif) {
            mExpectedMedia.mIsGif = isGif;
            return this;
        }

        public Builder setIsVideo(boolean isVideo) {
            mExpectedMedia.mIsVideo = isVideo;
            return this;
        }

        public Builder setLowQualityByteSize(int lowQualityByteSize) {
            mExpectedMedia.mLowQualityByteSize = lowQualityByteSize;
            return this;
        }

        public Builder setLowQualityHeight(int lowQualityHeight) {
            mExpectedMedia.mLowQualityHeight = lowQualityHeight;
            return this;
        }

        public Builder setLowQualityUrl(String lowQualityUrl) {
            return setLowQualityUrl(ParseUtils.getUrlObject(lowQualityUrl));
        }

        public Builder setLowQualityUrl(URL lowQualityUrl) {
            mExpectedMedia.mLowQualityUrl = lowQualityUrl;
            return this;
        }

        public Builder setLowQualityWidth(int lowQualityWidth) {
            mExpectedMedia.mLowQualityWidth = lowQualityWidth;
            return this;
        }

        public Builder setPreviewUrl(URL previewUrl) {
            mExpectedMedia.mPreviewUrl = previewUrl;
            return this;
        }

        public Builder setPreviewUrl(String previewUrl) {
            return setPreviewUrl(ParseUtils.getUrlObject(previewUrl));
        }

        public Builder setTitle(String title) {
            mExpectedMedia.mTitle = title;
            return this;
        }
    }
}
