package com.zyxf.workdivision.fragment;

import android.content.Intent;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.zyxf.workdivision.FeedbackActivity;
import com.zyxf.workdivision.LinkActivity;
import com.zyxf.workdivision.R;
import com.zyxf.workdivision.adapter.GridAdapter;
import com.zyxf.workdivision.base.BaseFragment;
import com.zyxf.workdivision.bean.GridItem;
import com.zyxf.workdivision.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeathPluto on 2015/5/18.
 */
public class AdditionalFragment extends BaseFragment {
    private GridView mGridView;
    private GridAdapter adapter;
    private List<GridItem> list = new ArrayList<>();
    private String[] names = UIUtils.getStringArray(R.array.additional_function);
    private int[] resIds = new int[]{R.drawable.icon_feedback, R.drawable.icon_link, R.drawable.icon_worker_shop};

    @Override
    protected void initView(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_additional, container, false);
        for (int i = 0; i < names.length; i++) {
            list.add(new GridItem(names[i], resIds[i]));
        }
        mGridView = (GridView) rootView.findViewById(R.id.gridview);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int screenHeight = metrics.heightPixels;

        mGridView.setVerticalSpacing((int) (screenHeight * 0.08));

        adapter = new GridAdapter(list);
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(FeedbackActivity.class);
                        break;
                    case 1:
                        startActivity(LinkActivity.class);
                        break;
                    case 2:/* 链接到京东 */
                        Uri uri = Uri.parse("http://www.jd.com/");
                        Intent it = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(it);
                        break;
                }
            }
        });
    }

    @Override
    protected void setListeners() {

    }

    @Override
    public void onClick(View v) {

    }

}
