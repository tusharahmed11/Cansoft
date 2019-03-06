package com.cansoft.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Post {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("date_gmt")
    @Expose
    private String dateGmt;

    @SerializedName("slug")
    @Expose
    private String slug;

    @SerializedName("title")
    @Expose
    private Title title;
    @SerializedName("content")
    @Expose
    private Content content;

    @SerializedName("featured_media")
    @Expose
    private Integer featuredMedia;

    @SerializedName("categories")
    @Expose
    private List<Integer> categories = null;
    @SerializedName("tags")
    @Expose
    private List<Object> tags = null;

    @SerializedName("_embedded")
    @Expose
    private Embedded embedded;

    public Post() {
    }

    public Post(Integer id, String date, String dateGmt, String slug, Title title, Content content, Integer featuredMedia, List<Integer> categories, List<Object> tags, Embedded embedded) {
        this.id = id;
        this.date = date;
        this.dateGmt = dateGmt;
        this.slug = slug;
        this.title = title;
        this.content = content;
        this.featuredMedia = featuredMedia;
        this.categories = categories;
        this.tags = tags;
        this.embedded = embedded;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateGmt() {
        return dateGmt;
    }

    public void setDateGmt(String dateGmt) {
        this.dateGmt = dateGmt;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Integer getFeaturedMedia() {
        return featuredMedia;
    }

    public void setFeaturedMedia(Integer featuredMedia) {
        this.featuredMedia = featuredMedia;
    }

    public List<Integer> getCategories() {
        return categories;
    }

    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }

    public List<Object> getTags() {
        return tags;
    }

    public void setTags(List<Object> tags) {
        this.tags = tags;
    }

    public Embedded getEmbedded() {
        return embedded;
    }

    public void setEmbedded(Embedded embedded) {
        this.embedded = embedded;
    }
}
