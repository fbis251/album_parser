package com.fernandobarillas.albumparser.tumblr.model;

import com.fernandobarillas.albumparser.media.BaseApiResponse;
import com.fernandobarillas.albumparser.media.IMediaAlbum;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.URL;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class TumblrResponse extends BaseApiResponse {

    @SerializedName("response")
    @Expose
    public ResponseData response;

    private IMediaAlbum mAlbum;

    @Override
    public IMediaAlbum getAlbum() {
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
