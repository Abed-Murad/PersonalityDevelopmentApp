package com.am.betterme.data.model;

import java.util.Date;
import java.util.List;

public class Post {
    private String body;
    private Date date;
    private String image_url;
    private String title;
    private String url;
    private boolean is_video;
    private List<String> tags;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isIs_video() {
        return is_video;
    }

    public void setIs_video(boolean is_video) {
        this.is_video = is_video;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Post() {
    }

    @Override
    public String toString() {
        return "Post{" +
                "body='" + body + '\'' +
                ", date=" + date +
                ", image_url='" + image_url + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", is_video=" + is_video +
                ", tags=" + tags +
                '}';
    }
}
