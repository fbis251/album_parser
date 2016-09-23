package com.fernandobarillas.albumparser.model;

import com.fernandobarillas.albumparser.media.IApiResponse;
import com.fernandobarillas.albumparser.media.IMedia;
import com.fernandobarillas.albumparser.media.IMediaAlbum;
import com.fernandobarillas.albumparser.parser.IParserResponse;

import java.net.URL;

/**
 * Created by fb on 9/22/16.
 */
public class ExpectedParserResponse implements IParserResponse {

    private URL         mOriginalUrl;
    private IMediaAlbum mMediaAlbum;
    private IMedia      mMedia;
    private boolean     mIsAlbum;
    private boolean     mIsSingleMedia;

    private ExpectedParserResponse(URL originalUrl) {
        mOriginalUrl = originalUrl;
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
    public URL getOriginalUrl() {
        return mOriginalUrl;
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

        private ExpectedParserResponse mExpectedParserResponse;

        public Builder(URL originalUrl) {
            mExpectedParserResponse = new ExpectedParserResponse(originalUrl);
        }

        public ExpectedParserResponse build() {
            return mExpectedParserResponse;
        }

        public Builder setIsAlbum(boolean isAlbum) {
            mExpectedParserResponse.mIsAlbum = isAlbum;
            return this;
        }

        public Builder setIsSingleMedia(boolean isSingleMedia) {
            mExpectedParserResponse.mIsSingleMedia = isSingleMedia;
            return this;
        }

        public Builder setMedia(IMedia media) {
            mExpectedParserResponse.mMedia = media;
            return this;
        }

        public Builder setMediaAlbum(IMediaAlbum mediaAlbum) {
            mExpectedParserResponse.mMediaAlbum = mediaAlbum;
            return this;
        }
    }
}
