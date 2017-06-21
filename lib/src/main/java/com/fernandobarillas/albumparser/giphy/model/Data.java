package com.fernandobarillas.albumparser.giphy.model;

import com.squareup.moshi.Json;

public class Data {

    @Json(name = "type")
    public String  type;
    @Json(name = "id")
    public String  id;
    @Json(name = "slug")
    public String  slug;
    @Json(name = "url")
    public String  url;
    @Json(name = "bitly_gif_url")
    public String  bitlyGifUrl;
    @Json(name = "bitly_url")
    public String  bitlyUrl;
    @Json(name = "embed_url")
    public String  embedUrl;
    @Json(name = "username")
    public String  username;
    @Json(name = "source")
    public String  source;
    @Json(name = "rating")
    public String  rating;
    @Json(name = "content_url")
    public String  contentUrl;
    @Json(name = "source_tld")
    public String  sourceTld;
    @Json(name = "source_post_url")
    public String  sourcePostUrl;
    @Json(name = "is_indexable")
    public Integer isIndexable;
    @Json(name = "import_datetime")
    public String  importDatetime;
    @Json(name = "trending_datetime")
    public String  trendingDatetime;
    @Json(name = "images")
    public Image   images;
}
