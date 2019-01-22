package com.am.betterme.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.am.betterme.R;
import com.heinrichreimersoftware.androidissuereporter.IssueReporterLauncher;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

import static com.am.betterme.util.CONST.BASE_YOUTUBE_URL;

public class FUNC {

    private static PrettyTime prettyTime = new PrettyTime();

    public static void shareArticle(Context context, String title, String body) {
        String shareBody = title + "\n\n" + body + "\n\n\nSubscribe to PewDiePie";
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(sharingIntent, context.getResources().getString(R.string.share_using)));
    }

    public static void startRateUsActivity(Context context) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())));
    }
    public static void startAboutActivity(Context context) {
        new LibsBuilder()
                .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                .withAboutIconShown(true)
                .withAboutVersionShown(true)
                .withAboutDescription(context.getString(R.string.about_libraries_description))
                .withFields(R.string.class.getFields())
                .start(context);
    }

    public static void startBugReportActivity(Context context) {
        IssueReporterLauncher.forTarget("HeinrichReimer", "android-issue-reporter")
                // [Optional] Auth token to open issues if users don't have a GitHub account
                // You can register a bot account on GitHub and copy ist OAuth2 token here.
                // (See #how-to-create-a-bot-key for further information.)
                .guestToken("28f479f73db97d912611b27579aad7a76ad2baf5")
                // [Optional] Force users to enter an email adress when the report is sent using
                // the guest token.
                .guestEmailRequired(true)
                // [Optional] Set a minimum character limit for the description to filter out
                // empty reports.
                .minDescriptionLength(20)
                // [Optional] Include other relevant info in the bug report (like custom variables)
                .putExtraInfo("Test 1", "Example string")
                .putExtraInfo("Test 2", true)
                // [Optional] Disable back arrow in toolbar
                .homeAsUpEnabled(false)
                .launch(context);
    }

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
}
