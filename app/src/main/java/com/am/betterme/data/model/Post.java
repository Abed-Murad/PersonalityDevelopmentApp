package com.am.betterme.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.am.betterme.util.FUNC;

import java.util.Date;
import java.util.List;

public class Post implements Parcelable {

    private String body;
    private Date date;
    private String image_url;
    private String title;
    private String url;
    private boolean is_video;
    private List<String> tags;

    public Post() {}

    public String getBody() {
        return body;
    }

    public String getDate() {
        return FUNC.getPrettyDate(date);
    }


    public String getImage_url() {
        return image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public boolean isIs_video() {
        return is_video;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.body);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
        dest.writeString(this.image_url);
        dest.writeString(this.title);
        dest.writeString(this.url);
        dest.writeByte(this.is_video ? (byte) 1 : (byte) 0);
        dest.writeStringList(this.tags);
    }

    protected Post(Parcel in) {
        this.body = in.readString();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        this.image_url = in.readString();
        this.title = in.readString();
        this.url = in.readString();
        this.is_video = in.readByte() != 0;
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
}
