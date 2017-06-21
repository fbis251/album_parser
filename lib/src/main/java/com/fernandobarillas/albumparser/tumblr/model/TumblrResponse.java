package com.fernandobarillas.albumparser.tumblr.model;

import com.fernandobarillas.albumparser.media.BaseApiResponse;
import com.fernandobarillas.albumparser.media.IMediaAlbum;
import com.squareup.moshi.Json;

import java.net.URL;

public class TumblrResponse extends BaseApiResponse<BaseTumblrMedia> {

    @Json(name = "response")
    public ResponseData response;

    private transient IMediaAlbum<BaseTumblrMedia> mAlbum;

    @Override
    public IMediaAlbum<BaseTumblrMedia> getAlbum() {
        if (mAlbum == null) {
            if (response == null || response.posts == null) {
                return null;
            }
            mAlbum = response.posts.get(0);
        }

        return mAlbum;
    }

    @Override
    public URL getPreviewUrl() {
        IMediaAlbum album = getAlbum();
        return album != null ? album.getPreviewUrl() : null;
    }

    @Override
    public boolean isAlbum() {
        IMediaAlbum album = getAlbum();
        return album != null && album.getAlbumMedia() != null;
    }

    @Override
    public boolean isSuccessful() {
        return getAlbum() != null;
    }
}
