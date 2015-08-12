package com.zyxf.workdivision;

import android.app.Dialog;
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
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zyxf.workdivision.base.BaseActivity;
import com.zyxf.workdivision.base.BaseAdapter;
import com.zyxf.workdivision.bean.Project;
import com.zyxf.workdivision.bean.response.Check;
import com.zyxf.workdivision.bean.response.ResProject;
import com.zyxf.workdivision.config.Constants;
import com.zyxf.workdivision.http.Urls;
import com.zyxf.workdivision.manager.DialogManager;
import com.zyxf.workdivision.utils.LogUtils;
import com.zyxf.workdivision.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DeathPluto on 2015/5/19.
 */
public class ProjectActivity extends BaseActivity {
    private TextView titleTv;
    private List<Project> list;
    private ProjectAdapter adapter;
    private PullToRefreshListView mListView;
    private Dialog progressDialog;
    private boolean isLeader = false;
    private Check mCheck;
    private ResProject resProject;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_project);

        mCheck = (Check) mCache.getAsObject(Constants.CHECK);
        if (TextUtils.equals(mCheck.type, "leader")) {
            isLeader = true;
        } else {
            isLeader = false;
        }
        list = new ArrayList<>();
        mListView = (PullToRefreshListView) this.findViewById(R.id.listview);
        titleTv = (TextView) this.findViewById(R.id.tv_title);
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

    private void loadMore() {
        String url = resProject.next_page_url;
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(getApplicationContext(), "无更多工程信息了", Toast.LENGTH_SHORT).show();
            mListView.onRefreshComplete();
        } else {
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Gson gson = new Gson();
                    resProject = gson.fromJson(s, ResProject.class);
                    list.addAll(resProject.data);
                    initData();
                    mListView.onRefreshComplete();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    LogUtils.i(volleyError.toString() + "");
                    Toast.makeText(getApplicationContext(), "网络异常,加载失败", Toast.LENGTH_SHORT).show();
                    mListView.onRefreshComplete();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    String cookie = PreferenceUtils.getString(getApplicationContext(), Constants.COOKIE, null);
                    Map<String, String> map = new HashMap<>();
                    map.put("Cookie", cookie);
                    return map;
                }
            };
            mQueue.add(request);
        }

    }

    @Override
    public void initData() {
        if (adapter == null) {
            adapter = new ProjectAdapter(list);
            mListView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initAfterSetListeners() {
        titleTv.setText("工地工程信息");

        progressDialog = DialogManager.showProgressDialog(this, "获取信息中...");
        String url = Urls.URL_CHECK_PROJECT;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                LogUtils.i(s);
                resProject = gson.fromJson(s, ResProject.class);
                list.addAll(resProject.data);
                initData();
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null) {
                    LogUtils.i(volleyError.toString());
                }
                Toast.makeText(getApplicationContext(), "网络异常,获取信息失败", Toast.LENGTH_SHORT).show();
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String cookie = PreferenceUtils.getString(getApplicationContext(), Constants.COOKIE, null);
                Map<String, String> map = new HashMap<>();
                map.put("Cookie", cookie);
                return map;
            }
        };
        mQueue.add(request);

    }

    static class ViewHolder {
        TextView chineseTv;
        TextView englishTv;
        TextView countryTv;
        TextView cityTv;
        TextView addressTv;
        TextView personTv;
        TextView faxTv;
        TextView postTv;
        TextView phone1Tv;
        TextView phone2Tv;

        ViewHolder(View v) {
            chineseTv = (TextView) v.findViewById(R.id.tv_name);
            englishTv = (TextView) v.findViewById(R.id.tv_name_en);
            countryTv = (TextView) v.findViewById(R.id.tv_country);
            cityTv = (TextView) v.findViewById(R.id.tv_city);
            addressTv = (TextView) v.findViewById(R.id.tv_address);
            personTv = (TextView) v.findViewById(R.id.tv_person);
            faxTv = (TextView) v.findViewById(R.id.tv_fax);
            postTv = (TextView) v.findViewById(R.id.tv_post);
            phone1Tv = (TextView) v.findViewById(R.id.tv_phone_1);
            phone2Tv = (TextView) v.findViewById(R.id.tv_phone_2);
        }
    }

    private class ProjectAdapter extends BaseAdapter<Project> {
        private ViewHolder holder;

        private ProjectAdapter(List<Project> list) {
            super(list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Project item = getItem(position);
            if (convertView != null) {
                holder = (ViewHolder) convertView.getTag();
            } else {
                convertView = View.inflate(parent.getContext(), R.layout.item_listview_project, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            holder.chineseTv.setText(item.chinese_name);
            holder.englishTv.setText(item.english_name);
            holder.countryTv.setText(item.country);
            holder.cityTv.setText(item.city);
            holder.addressTv.setText(item.address);
            holder.personTv.setText(item.supervisor);
            holder.faxTv.setText(item.fax);
            holder.postTv.setText(item.zip_code);
            holder.phone1Tv.setText(item.phone_number_1);
            holder.phone2Tv.setText(item.phone_number_2);
            return convertView;
        }

    }

    @Override
    protected void setListeners() {

    }

    @Override
    public void onClick(View v) {

    }
}
