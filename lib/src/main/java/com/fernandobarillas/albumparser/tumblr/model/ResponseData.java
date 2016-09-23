package com.fernandobarillas.albumparser.tumblr.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ResponseData {
    @SerializedName("posts")
    @Expose
    public List<Post> posts = new ArrayList<>();
    @SerializedName("total_posts")
    @Expose
    public int totalPosts;
}
