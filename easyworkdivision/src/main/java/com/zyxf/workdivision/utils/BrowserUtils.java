package com.zyxf.workdivision.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by DeathPluto on 2015/5/29.
 */
public class BrowserUtils {

    public static void openWebSite(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(it);
    }
}
