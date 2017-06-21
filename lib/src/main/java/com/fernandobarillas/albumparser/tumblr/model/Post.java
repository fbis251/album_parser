package com.fernandobarillas.albumparser.tumblr.model;

import com.fernandobarillas.albumparser.media.BaseMediaAlbum;
import com.fernandobarillas.albumparser.util.ParseUtils;
import com.squareup.moshi.Json;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Post extends BaseMediaAlbum<BaseTumblrMedia> {

    @Json(name = "blog_name")
    public String       blogName;
    @Json(name = "id")
    public Long         id;
    @Json(name = "post_url")
    public String       postUrl;
    @Json(name = "slug")
    public String       slug;
    @Json(name = "type")
    public String       type;
    @Json(name = "date")
    public String       date;
    @Json(name = "timestamp")
    public Integer      timestamp;
    @Json(name = "state")
    public String       state;
    @Json(name = "format")
    public String       format;
    @Json(name = "reblog_key")
    public String       reblogKey;
    @Json(name = "tags")
    public List<String> tags;
    @Json(name = "short_url")
    public String       shortUrl;
    @Json(name = "summary")
    public String       summary;
    @Json(name = "photos")
    public List<Photo>  photos;
    @Json(name = "recommended_source")
    public Object       recommendedSource;
    @Json(name = "recommended_color")
    public Object       recommendedColor;
    @Json(name = "highlighted")
    public List<Object> highlighted;
    @Json(name = "note_count")
    public Integer      noteCount;
    @Json(name = "caption")
    public String       caption;
    @Json(name = "video_url")
    public String       videoUrl;
    @Json(name = "html5_capable")
    public Boolean      html5Capable;
    @Json(name = "thumbnail_url")
    public String       thumbnailUrl;
    @Json(name = "thumbnail_width")
    public Integer      thumbnailWidth;
    @Json(name = "thumbnail_height")
    public Integer      thumbnailHeight;
    @Json(name = "duration")
    public Integer      duration;
    @Json(name = "video_type")
    public String       videoType;
    @Json(name = "can_send_in_message")
    public Boolean      canSendInMessage;
    @Json(name = "can_like")
    public Boolean      canLike;
    @Json(name = "can_reblog")
    public Boolean      canReblog;
    @Json(name = "display_avatar")
    public Boolean      displayAvatar;

    private List<BaseTumblrMedia> mMediaList;

    @Override
    public List<BaseTumblrMedia> getAlbumMedia() {
        if (mMediaList == null) {
            mMediaList = new ArrayList<>();
            if (photos != null) {
                mMediaList.addAll(photos);
            }
            if (videoUrl != null) {
                Double videoDuration = null;
                if (duration != null) videoDuration = Double.valueOf(duration);
                Video video = new Video(videoUrl, thumbnailUrl, caption, videoDuration);
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
