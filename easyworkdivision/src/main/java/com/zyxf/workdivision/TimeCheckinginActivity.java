package com.zyxf.workdivision;

import android.app.Dialog;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zyxf.workdivision.base.BaseActivity;
import com.zyxf.workdivision.base.BaseAdapter;
import com.zyxf.workdivision.bean.Checkingin;
import com.zyxf.workdivision.bean.response.Check;
import com.zyxf.workdivision.bean.response.CheckinginForm;
import com.zyxf.workdivision.config.Constants;
import com.zyxf.workdivision.formatter.XValueFormatter;
import com.zyxf.workdivision.formatter.YValueFormatter;
import com.zyxf.workdivision.http.Urls;
import com.zyxf.workdivision.manager.DialogManager;
import com.zyxf.workdivision.utils.LogUtils;
import com.zyxf.workdivision.utils.PreferenceUtils;
import com.zyxf.workdivision.utils.UIUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    private List<Checkingin> list;
    private CheckinginAdapter adapter;
    private CheckinginForm checkinginForm;
    private Typeface mTf;
    private Dialog progressDialog;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private TextView emptyTv;
    private boolean hasInitChart = false;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_checkingin_time);
        mChart = (BarChart) this.findViewById(R.id.chart);
        mListView = (PullToRefreshListView) this.findViewById(R.id.listview);
        titleTv = (TextView) this.findViewById(R.id.tv_title);
        emptyTv = (TextView) this.findViewById(R.id.tv_empty);

        mCheck = (Check) mCache.getAsObject(Constants.CHECK);
        LogUtils.i(mCheck.type);
        if (TextUtils.equals(mCheck.type, "leader")) {
            isLeader = true;
        } else {
            isLeader = false;
        }

        if (isLeader) {
            mListView.setVisibility(View.GONE);
            mChart.setVisibility(View.VISIBLE);
        } else {
            mListView.setVisibility(View.VISIBLE);
            mChart.setVisibility(View.GONE);
        }


        titleTv.setText("当日考勤");

    }

    @Override
    public void initData() {
        if (isLeader) {
            initChart();
        } else {
            if (list == null || list.size() == 0) {
                mListView.setVisibility(View.GONE);
                emptyTv.setVisibility(View.VISIBLE);
            } else {
                adapter = new CheckinginAdapter(list);
                mListView.setAdapter(adapter);
            }

            mListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
            mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                }

                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    loadMore();
                }
            });
        }
    }

    private void initChart() {
        if (!hasInitChart) {
            hasInitChart = true;
            mChart.setDrawBarShadow(false);
            mChart.setDrawValueAboveBar(true);
            mChart.setDescription("");
            mChart.setNoDataTextDescription("无考勤记录");
            mChart.setPinchZoom(false);
            mChart.setScaleYEnabled(false);
            mChart.setDrawGridBackground(false);
            mChart.animateXY(1200, 1200);
            mChart.setNoDataText("无考勤记录");
            mChart.setVisibleXRange(3);
            mChart.zoom(3.0f, 1.0f, 0, 0);
            mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

            XAxis xAxis = mChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTypeface(mTf);
            xAxis.setAdjustXLabels(true);
            xAxis.setDrawGridLines(false);
            xAxis.setTextSize(7f);
            ValueFormatter custom = new YValueFormatter();

            YAxis leftAxis = mChart.getAxisLeft();
            leftAxis.setTypeface(mTf);
            leftAxis.setLabelCount(5);
            leftAxis.setXOffset(10);
            leftAxis.setValueFormatter(custom);
            leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            leftAxis.setSpaceTop(15f);

            mChart.getAxisRight().setEnabled(false);

            Legend l = mChart.getLegend();
            l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
            l.setForm(Legend.LegendForm.SQUARE);
            l.setFormSize(9f);
            l.setTextSize(11f);
            l.setXEntrySpace(4f);
        }


        setData();
    }

    private void setData() {
        String[] departments = UIUtils.getStringArray(R.array.departments);
        ArrayList<String> xVals = new ArrayList<String>();
        for (String department : departments) {
            xVals.add(department);
        }
        int maxCount = 10;
        ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();
        for (int i = 0; i < departments.length; i++) {
            int count = 0;
            for (int j = 0; j < list.size(); j++) {
                if (TextUtils.equals(list.get(j).department, departments[i])) {
                    count++;
                }
            }
            maxCount = maxCount < count ? count : maxCount;
            yVals.add(new BarEntry(count, i));
        }

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setLabelCount(maxCount / 5);

        BarDataSet set = new BarDataSet(yVals, "到工人数");
        set.setBarSpacePercent(45f);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        data.setValueTypeface(mTf);
        data.setGroupSpace(100);
        data.setValueFormatter(new XValueFormatter());
        mChart.setData(data);
    }

    private void loadMore() {
        String url = checkinginForm.next_page_url +
                "?begin_timestamp=" + getStartTime() +
                "&end_timestamp=" + getEndTime();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                checkinginForm = gson.fromJson(s, CheckinginForm.class);
                list.addAll(checkinginForm.data);
                adapter.notifyDataSetChanged();
                mListView.onRefreshComplete();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null) {
                    LogUtils.i(volleyError.toString());
                }
                mListView.onRefreshComplete();
                Toast.makeText(getApplicationContext(), "网络异常,加载失败", Toast.LENGTH_SHORT).show();
            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                String cookie = PreferenceUtils.getString(getApplicationContext(), Constants.COOKIE, null);
                map.put("Cookie", cookie);
                return map;
            }

        };
        mQueue.add(request);
    }

    @Override
    protected void initAfterSetListeners() {
        progressDialog = DialogManager.showProgressDialog(this, "加载数据中...");
        Check check = (Check) mCache.getAsObject(Constants.CHECK);
        if (isLeader) {
            String url = Urls.URL_SEARCH_CHECKINGIN +
                    "?begin_timestamp=" + getStartTime() +
                    "&end_timestamp=" + getEndTime();
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Gson gson = new Gson();
                    checkinginForm = gson.fromJson(s, CheckinginForm.class);
                    list = checkinginForm.data;
                    initData();
                    progressDialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    if (volleyError != null) {
                        LogUtils.i(volleyError.toString());
                    }
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "网络异常,加载数据失败", Toast.LENGTH_SHORT).show();
                }
            }) {

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
                    String cookie = PreferenceUtils.getString(getApplicationContext(), Constants.COOKIE, null);
                    map.put("Cookie", cookie);
                    return map;
                }
            };
            mQueue.add(request);
        } else {
            String url = Urls.URL_SEARCH_CHECKINGIN +
                    "?begin_timestamp=" + getStartTime() +
                    "&end_timestamp=" + getEndTime() +
                    "&id_strings=" + check.user.id_string;
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    LogUtils.i(s);
                    Gson gson = new Gson();
                    checkinginForm = gson.fromJson(s, CheckinginForm.class);
                    list = checkinginForm.data;
                    initData();
                    progressDialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    LogUtils.i("错误");
                    if (volleyError != null) {
                        LogUtils.i(volleyError.toString());
                    }
                    progressDialog.dismiss();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    String cookie = PreferenceUtils.getString(getApplicationContext(), Constants.COOKIE, null);
                    map.put("Cookie", cookie);
                    return map;
                }

            };
            mQueue.add(request);
        }
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


    @Override
    protected void setListeners() {

    }

    @Override
    public void onClick(View v) {

    }

    static class ViewHolder {
        TextView nameTv;
        TextView timeTv;
        TextView numberTv;

        public ViewHolder(View v) {
            nameTv = (TextView) v.findViewById(R.id.tv_name);
            timeTv = (TextView) v.findViewById(R.id.tv_time);
            numberTv = (TextView) v.findViewById(R.id.tv_number);
        }
    }

    private class CheckinginAdapter extends BaseAdapter<Checkingin> {
        private ViewHolder holder;

        private CheckinginAdapter(List<Checkingin> list) {
            super(list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Checkingin item = getItem(position);
            if (convertView != null) {
                holder = (ViewHolder) convertView.getTag();
            } else {
                convertView = View.inflate(parent.getContext(), R.layout.item_timecheckingin_listview, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            holder.numberTv.setText(item.serial_number);
            holder.nameTv.setText(item.name);
            holder.timeTv.setText(item.attendance_timestamp);
            return convertView;
        }
    }
}
