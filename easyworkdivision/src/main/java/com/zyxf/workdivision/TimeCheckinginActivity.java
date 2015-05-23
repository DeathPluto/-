package com.zyxf.workdivision;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.charts.BarChart;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zyxf.workdivision.application.HalcyonApplication;
import com.zyxf.workdivision.base.BaseActivity;
import com.zyxf.workdivision.bean.response.Check;
import com.zyxf.workdivision.config.Constants;
import com.zyxf.workdivision.http.Urls;
import com.zyxf.workdivision.utils.LogUtils;
import com.zyxf.workdivision.utils.PreferenceUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by DeathPluto on 2015/5/19.
 */
public class TimeCheckinginActivity extends BaseActivity {
    private TextView titleTv;
    private Check mCheck;
    private boolean isLeader = false;
    private BarChart mChart;
    private PullToRefreshListView mListView;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_checkingin_time);
        mChart = (BarChart) this.findViewById(R.id.chart);
        mListView = (PullToRefreshListView) this.findViewById(R.id.listview);
        titleTv = (TextView) this.findViewById(R.id.tv_title);

        mCheck = HalcyonApplication.getCheck();
        if (mCheck != null) {
            if (TextUtils.equals(mCheck.type, "leader")) {
                isLeader = true;
            } else {
                isLeader = false;
            }
        }

        if (isLeader) {
            mListView.setVisibility(View.GONE);
            mChart.setVisibility(View.VISIBLE);
        } else {
            mListView.setVisibility(View.VISIBLE);
            mChart.setVisibility(View.GONE);
        }


        titleTv.setText("实时考勤");

    }

    @Override
    protected void initAfterSetListeners() {
        StringRequest request = new StringRequest(Request.Method.GET, Urls.URL_SEARCH_CHECKINGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtils.i(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtils.i("错误");
                if (volleyError != null) {
                    LogUtils.i(volleyError.toString());
                }
            }
        }) {

            private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("begin_timestamp", getStartTime());
                map.put("end_timestamp", getEndTime());
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Cookie", PreferenceUtils.getString(getApplicationContext(), Constants.COOKIE, null));
                return map;
            }

            private String getStartTime() {
                Calendar todayStart = Calendar.getInstance(TimeZone.getTimeZone(Constants.TIMEZONE));
                todayStart.set(Calendar.HOUR, 0);
                todayStart.set(Calendar.MINUTE, 0);
                todayStart.set(Calendar.SECOND, 0);
                todayStart.set(Calendar.MILLISECOND, 0);
                return sdf.format(todayStart.getTime());
            }

            private String getEndTime() {
                return sdf.format(new Date());
            }
        };
        mQueue.add(request);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    public void onClick(View v) {

    }
}
