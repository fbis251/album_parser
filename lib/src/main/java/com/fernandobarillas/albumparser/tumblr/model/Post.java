package com.fernandobarillas.albumparser.tumblr.model;

import com.fernandobarillas.albumparser.media.BaseMediaAlbum;
import com.fernandobarillas.albumparser.media.IMedia;
import com.fernandobarillas.albumparser.util.ParseUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Post extends BaseMediaAlbum {

    @SerializedName("blog_name")
    @Expose
    public String blogName;

    @SerializedName("id")
    @Expose
    public long id;

    @SerializedName("post_url")
    @Expose
    public String postUrl;

    @SerializedName("slug")
    @Expose
    public String slug;

    @SerializedName("type")
    @Expose
    public String type;

    @SerializedName("date")
    @Expose
    public String date;

    @SerializedName("timestamp")
    @Expose
    public int timestamp;

    @SerializedName("state")
    @Expose
    public String state;

    @SerializedName("format")
    @Expose
    public String format;

    @SerializedName("reblog_key")
    @Expose
    public String reblogKey;

    @SerializedName("tags")
    @Expose
    public List<String> tags = new ArrayList<String>();

    @SerializedName("short_url")
    @Expose
    public String shortUrl;

    @SerializedName("summary")
    @Expose
    public String summary;

    @SerializedName("photos")
    @Expose
    public List<Photo> photos = new ArrayList<>();

    @SerializedName("recommended_source")
    @Expose
    public Object recommendedSource;

    @SerializedName("recommended_color")
    @Expose
    public Object recommendedColor;

    @SerializedName("highlighted")
    @Expose
    public List<Object> highlighted = new ArrayList<Object>();

    @SerializedName("note_count")
    @Expose
    public int noteCount;

    @SerializedName("caption")
    @Expose
    public String caption;

    @SerializedName("video_url")
    @Expose
    public String videoUrl;

    @SerializedName("html5_capable")
    @Expose
    public boolean html5Capable;

    @SerializedName("thumbnail_url")
    @Expose
    public String thumbnailUrl;

    @SerializedName("thumbnail_width")
    @Expose
    public int thumbnailWidth;

    @SerializedName("thumbnail_height")
    @Expose
    public int thumbnailHeight;

    @SerializedName("duration")
    @Expose
    public int duration;

    @SerializedName("video_type")
    @Expose
    public String videoType;

    @SerializedName("can_send_in_message")
    @Expose
    public boolean canSendInMessage;

    @SerializedName("can_like")
    @Expose
    public boolean canLike;

    @SerializedName("can_reblog")
    @Expose
    public boolean canReblog;

    @SerializedName("display_avatar")
    @Expose
    public boolean displayAvatar;

    private List<IMedia> mMediaList;

    @Override
    public List<IMedia> getAlbumMedia() {
        if (mMediaList == null) {
            mMediaList = new ArrayList<>();
            if (photos != null) {
                mMediaList.addAll(photos);
            }
            if (videoUrl != null) {
                Video video = new Video(videoUrl, thumbnailUrl, caption, duration);
                mMediaList.add(video);
            }
        }
        return mMediaList;
    }

    @Override
    public int getCount() {
        return getAlbumMedia() != null ? getAlbumMedia().size() : COUNT_UNAVAILABLE;
    }

    @Override
    public URL getPreviewUrl() {
        if (thumbnailUrl != null) return ParseUtils.getUrlObject(thumbnailUrl);
        if (photos != null && photos.size() > 0) {
            // Return the first image as the preview
            return photos.get(0).getPreviewUrl();
        }
        return null;
    }
}
