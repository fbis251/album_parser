package com.fernandobarillas.albumparser.giphy.model;

import com.squareup.moshi.Json;

public class ImageVariation {

    @Json(name = "url")
    public String  url;
    @Json(name = "width")
    public Integer width;
    @Json(name = "height")
    public Integer height;
    @Json(name = "size")
    public Integer size;
    @Json(name = "frames")
    public Integer frames;
    @Json(name = "mp4")
    public String  mp4;
    @Json(name = "mp4_size")
    public Integer mp4Size;
    @Json(name = "webp")
    public String  webp;
    @Json(name = "webp_size")
    public Integer webpSize;

    @Override
    public String toString() {
        return "ImageVariation{"
                + "url='"
                + url
                + '\''
                + ", width="
                + width
                + ", height="
                + height
                + ", size="
                + size
                + ", frames="
                + frames
                + ", mp4='"
                + mp4
                + '\''
                + ", mp4Size="
                + mp4Size
                + ", webp='"
                + webp
                + '\''
                + ", webpSize="
                + webpSize
                + '}';
    }
}
