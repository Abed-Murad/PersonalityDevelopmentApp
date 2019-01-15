package com.am.betterme.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.am.betterme.R;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

import static com.am.betterme.util.CONST.BASE_YOUTUBE_URL;

public class FUNC {
    private static PrettyTime prettyTime = new PrettyTime();

    public static void openVideoOnYoutube(Context context, String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(BASE_YOUTUBE_URL + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    public static String getPrettyDate(Date date) {
        return prettyTime.format(date);
    }

    public static void startShareIntentForArticle(Context context, String title, String body) {
        String shareBody = title + "\n\n" + body + "\n\n\nSubscribe to PewDiePie";
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(sharingIntent, context.getResources().getString(R.string.share_using)));
    }
}
