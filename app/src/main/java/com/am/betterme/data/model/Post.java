package com.am.betterme.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.am.betterme.util.FUNC;

import java.util.Date;
import java.util.List;

public class Post implements Parcelable {

    private String body;
    private Date date;
    private String imageUrl;
    private String title;
    private String url;
    private boolean isVideo;
    private List<String> tags;

    public Post() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return FUNC.getPrettyDate(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.body);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
        dest.writeString(this.imageUrl);
        dest.writeString(this.title);
        dest.writeString(this.url);
        dest.writeByte(this.isVideo ? (byte) 1 : (byte) 0);
        dest.writeStringList(this.tags);
    }

    protected Post(Parcel in) {
        this.body = in.readString();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        this.imageUrl = in.readString();
        this.title = in.readString();
        this.url = in.readString();
        this.isVideo = in.readByte() != 0;
        this.tags = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel source) {
            return new Post(source);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    @Override
    public String toString() {
        return "Post{" +
                "body='" + body + '\'' +
                ", date=" + date +
                ", imageUrl='" + imageUrl + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", isVideo=" + isVideo +
                ", tags=" + tags +
                '}';
    }
}
