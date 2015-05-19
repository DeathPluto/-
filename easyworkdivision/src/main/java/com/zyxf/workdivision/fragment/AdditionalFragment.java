package com.zyxf.workdivision.fragment;

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

    @Override
    protected void initView(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_additional, container, false);
        for (int i = 0; i < names.length; i++) {
            list.add(new GridItem(names[i]));
        }
        mGridView = (GridView) rootView.findViewById(R.id.gridview);
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
                    case 2:
                        //TODO 链接到京东网站
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
