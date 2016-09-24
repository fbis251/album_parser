package com.fernandobarillas.albumparser.giphy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageVariation {

    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("width")
    @Expose
    public int    width;
    @SerializedName("height")
    @Expose
    public int    height;
    @SerializedName("size")
    @Expose
    public int    size;
    @SerializedName("frames")
    @Expose
    public int    frames;
    @SerializedName("mp4")
    @Expose
    public String mp4;
    @SerializedName("mp4_size")
    @Expose
    public int    mp4Size;
    @SerializedName("webp")
    @Expose
    public String webp;
    @SerializedName("webp_size")
    @Expose
    public int    webpSize;

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
