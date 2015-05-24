package com.zyxf.workdivision.fragment;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.zyxf.workdivision.ContactUsActivity;
import com.zyxf.workdivision.IntroduceActivity;
import com.zyxf.workdivision.MonthCheckinginActivity;
import com.zyxf.workdivision.ProjectActivity;
import com.zyxf.workdivision.R;
import com.zyxf.workdivision.TimeCheckinginActivity;
import com.zyxf.workdivision.WeekCheckinginActivity;
import com.zyxf.workdivision.adapter.GridAdapter;
import com.zyxf.workdivision.base.BaseFragment;
import com.zyxf.workdivision.bean.GridItem;
import com.zyxf.workdivision.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeathPluto on 2015/5/18.
 */
public class MainFragment extends BaseFragment {
    private GridView mGridView;
    private GridAdapter adapter;
    private List<GridItem> list = new ArrayList<>();
    private String[] names = UIUtils.getStringArray(R.array.main_function);

    @Override
    protected void initView(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        for (int i = 0; i < names.length; i++) {
            list.add(new GridItem(names[i]));
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
                        startActivity(TimeCheckinginActivity.class);
                        break;
                    case 1:
                        startActivity(WeekCheckinginActivity.class);
                        break;
                    case 2:
                        startActivity(MonthCheckinginActivity.class);
                        break;
                    case 3:
                        startActivity(IntroduceActivity.class);
                        break;
                    case 4:
                        startActivity(ContactUsActivity.class);
                        break;
                    case 5:
                        //todo 第三方分享
                        break;
                    case 6:
                        //TODO 暂无功能
                        break;
                    case 7:
                        startActivity(ProjectActivity.class);
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
