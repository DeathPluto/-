package com.zyxf.workdivision.http;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by DeathPluto on 2015/5/18.
 */
public class VolleyTool {
    private static VolleyTool instance;
    private RequestQueue queue;

    private VolleyTool(Context context) {
        queue = Volley.newRequestQueue(context);
    }


    public static VolleyTool getInstance(Context context) {
        if (instance == null) {
            synchronized (VolleyTool.class) {
                if (instance == null) {
                    instance = new VolleyTool(context);
                }
            }
        }
        return instance;
    }

    public RequestQueue getQueue() {
        return queue;
    }
}
