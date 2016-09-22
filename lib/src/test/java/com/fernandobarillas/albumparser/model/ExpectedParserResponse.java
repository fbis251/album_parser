package com.fernandobarillas.albumparser.model;

import com.fernandobarillas.albumparser.media.IApiResponse;
import com.fernandobarillas.albumparser.media.IMedia;
import com.fernandobarillas.albumparser.media.IMediaAlbum;
import com.fernandobarillas.albumparser.parser.IParserResponse;

/**
 * Created by fb on 9/22/16.
 */
public class ExpectedParserResponse implements IParserResponse {

    private IMediaAlbum mMediaAlbum;
    private IMedia      mMedia;
    private boolean     mIsAlbum;
    private boolean     mIsSingleMedia;

    private ExpectedParserResponse() {
        mMediaAlbum = null;
        mMedia = null;
        mIsAlbum = false;
        mIsSingleMedia = false;
    }

    @Override
    public IMediaAlbum getAlbum() {
        return mMediaAlbum;
    }

    @Override
    public IApiResponse getApiResponse() {
        // There is no parser response to return since this doesn't do an API call
        return null;
    }

    @Override
    public IMedia getMedia() {
        return mMedia;
    }

    @Override
    public boolean isAlbum() {
        return mIsAlbum;
    }

    @Override
    public boolean isSingleMedia() {
        return mIsSingleMedia;
    }


    public static class Builder {

        private ExpectedParserResponse mExpectedAlbum;

        public Builder() {
            mExpectedAlbum = new ExpectedParserResponse();
        }

        public ExpectedParserResponse build() {
            return mExpectedAlbum;
        }

        public Builder setIsAlbum(boolean isAlbum) {
            mExpectedAlbum.mIsAlbum = isAlbum;
            return this;
        }

        public Builder setIsSingleMedia(boolean isSingleMedia) {
            mExpectedAlbum.mIsSingleMedia = isSingleMedia;
            return this;
        }

        public Builder setMedia(IMedia media) {
            mExpectedAlbum.mMedia = media;
            return this;
        }

        public Builder setMediaAlbum(IMediaAlbum mediaAlbum) {
            mExpectedAlbum.mMediaAlbum = mediaAlbum;
            return this;
        }
    }
}
