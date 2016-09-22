package com.fernandobarillas.albumparser.model;

import com.fernandobarillas.albumparser.media.IMedia;
import com.fernandobarillas.albumparser.media.IMediaAlbum;
import com.fernandobarillas.albumparser.util.ParseUtils;

import java.net.URL;
import java.util.List;

/**
 * Created by fb on 9/22/16.
 */
public class ExpectedAlbum implements IMediaAlbum {

    private List<IMedia> mAlbumMedia;
    private int          mCount;
    private boolean      mIsEmpty;
    private URL          mPreviewUrl;

    private ExpectedAlbum() {
        mAlbumMedia = null;
        mCount = COUNT_UNAVAILABLE;
        mPreviewUrl = null;
        mIsEmpty = true;
    }

    @Override
    public List<IMedia> getAlbumMedia() {
        return mAlbumMedia;
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public boolean isEmpty() {
        return mIsEmpty;
    }

    @Override
    public URL getPreviewUrl() {
        return mPreviewUrl;
    }

    public static class Builder {

        private ExpectedAlbum mExpectedAlbum;

        public Builder() {
            mExpectedAlbum = new ExpectedAlbum();
        }

        public ExpectedAlbum build() {
            return mExpectedAlbum;
        }

        public Builder setAlbumMedia(List<IMedia> albumMedia) {
            mExpectedAlbum.mAlbumMedia = albumMedia;
            return this;
        }

        public Builder setCount(int count) {
            mExpectedAlbum.mCount = count;
            return this;
        }

        public Builder setIsEmpty(boolean isEmpty) {
            mExpectedAlbum.mIsEmpty = isEmpty;
            return this;
        }

        public Builder setPreviewUrl(URL previewUrl) {
            mExpectedAlbum.mPreviewUrl = previewUrl;
            return this;
        }

        public Builder setPreviewUrl(String previewUrl) {
            return setPreviewUrl(ParseUtils.getUrlObject(previewUrl));
        }
    }
}
