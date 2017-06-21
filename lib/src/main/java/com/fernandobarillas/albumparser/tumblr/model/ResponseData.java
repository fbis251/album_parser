package com.fernandobarillas.albumparser.tumblr.model;

import com.squareup.moshi.Json;

import java.util.List;

public class ResponseData {
    @Json(name = "posts")
    public List<Post> posts;
    @Json(name = "total_posts")
    public Integer    totalPosts;
}
