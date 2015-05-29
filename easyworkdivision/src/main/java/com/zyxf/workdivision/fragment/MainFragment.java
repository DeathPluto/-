package com.zyxf.workdivision.fragment;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.zyxf.workdivision.ContactUsActivity;
import com.zyxf.workdivision.IntroduceActivity;
import com.zyxf.workdivision.MonthCheckinginActivity;
import com.zyxf.workdivision.ProjectActivity;
import com.zyxf.workdivision.R;
import com.zyxf.workdivision.TimeCheckinginActivity;
import com.zyxf.workdivision.WeekCheckinginActivity;
import com.zyxf.workdivision.adapter.GridAdapter;
import com.zyxf.workdivision.application.HalcyonApplication;
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
    private final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");

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
                        mController.openShare(getActivity(), false);
                        break;
                    case 6:
                        //TODO 修改密码(暂无)
                        Toast.makeText(HalcyonApplication.getApplication(), "建设中...", Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        startActivity(ProjectActivity.class);
                        break;
                }
            }
        });
        initShare();
    }

    private void initShare() {
        mController.setShareContent("易工分，http://www.umeng.com/social");

        mController.setShareMedia(new UMImage(getActivity(), R.drawable.ic_launcher));

        String appID = "wx967daebe835fbeac";
        String appSecret = "5fa9e68ca3970e87a1f83e563c8dcbce";
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(getActivity(), appID, appSecret);
        wxHandler.addToSocialSDK();
        // 添加微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(getActivity(), appID, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();

        //参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(getActivity(), "100424468",
                "c7394704798a158208a74ab60104f0ba");
        qqSsoHandler.addToSocialSDK();

        //参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(getActivity(), "100424468",
                "c7394704798a158208a74ab60104f0ba");
        qZoneSsoHandler.addToSocialSDK();

        //设置新浪SSO handler
        mController.getConfig().setSsoHandler(new SinaSsoHandler());

        //设置腾讯微博SSO handler
        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
    }


    @Override
    protected void setListeners() {

    }

    @Override
    public void onClick(View v) {

    }
}
