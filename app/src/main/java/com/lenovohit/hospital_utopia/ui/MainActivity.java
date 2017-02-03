package com.lenovohit.hospital_utopia.ui;


import android.app.ActivityGroup;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.lenovohit.hospital_utopia.R;
import com.lenovohit.hospital_utopia.publics.crash.MyCrashHandler;
import com.lenovohit.hospital_utopia.ui.four.MainFourActivity;
import com.lenovohit.hospital_utopia.ui.one.MainOneActivity;
import com.lenovohit.hospital_utopia.ui.three.MainThreeActivity;
import com.lenovohit.hospital_utopia.ui.two.MainTwoActivity;
import com.lenovohit.hospital_utopia.utils.ViewUtil;
import com.lenovohit.hospital_utopia.view.AlertDialog;
import com.mg.core.base.BaseActivity;
import com.mg.core.base.BaseApp;
import com.mg.core.net.HttpService;
import com.mg.core.net.OperateCode;
import com.mg.core.view.BottomBar;

public class MainActivity extends ActivityGroup {
    private ViewFlipper container;
    private BottomBar mBottombar;
    private Window oneActivity;
    private Window twoActivity;
    private Window threeActivity;
    private Window fourActivity;
    private int aTag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.lx_main_activity);
        registerSonToMain();
        container = (ViewFlipper) findViewById(R.id.container);
        mBottombar = (BottomBar) findViewById(R.id.mBottombar);
        mBottombar.setVisibility(View.VISIBLE);
        mBottombar.getImageOne().setImageResource(R.mipmap.i_one_press);
        mBottombar.getTextOne().setTextColor(ViewUtil.getResColor(R.color.bottom_text_press_colors));

        if (!ViewUtil.getIsDebug()) {
            MyCrashHandler myCrash = new MyCrashHandler();
            Thread.setDefaultUncaughtExceptionHandler(myCrash);
            BaseActivity.currentActivity.sendToBackgroud(OperateCode.i_getUpdateURL);
            // new UpdateManager().checkUpdate();
        }

        loadLinearLayout(1);
        initEvents();
    }

    /**
     * 设置界面
     *
     * @author LinHao 439224@qq.com
     * @version 创建时间： 2015-3-19 上午10:01:50
     */
    private void loadLinearLayout(int index) {
        if (aTag == index) {
            return;
        }
        aTag = index;
        container.removeAllViews();
        mBottombar.getImageOne().setImageResource(R.mipmap.i_one_normal);
        mBottombar.getImageTwo().setImageResource(R.mipmap.i_two_normal);
        mBottombar.getImageThree().setImageResource(R.mipmap.i_three_normal);
        mBottombar.getImageFour().setImageResource(R.mipmap.i_four_normal);
        mBottombar.getTextOne().setTextColor(ViewUtil.getResColor(R.color.bottom_text_normal_colors));
        mBottombar.getTextTwo().setTextColor(ViewUtil.getResColor(R.color.bottom_text_normal_colors));
        mBottombar.getTextThree().setTextColor(ViewUtil.getResColor(R.color.bottom_text_normal_colors));
        mBottombar.getTextFour().setTextColor(ViewUtil.getResColor(R.color.bottom_text_normal_colors));
        switchActivity(aTag);
        switch (index) {
            case 1:
                mBottombar.getImageOne().setImageResource(R.mipmap.i_one_press);
                mBottombar.getTextOne().setTextColor(ViewUtil.getResColor(R.color.app_theme_colors));
                break;
            case 2:
                mBottombar.getImageTwo().setImageResource(R.mipmap.i_two_press);
                mBottombar.getTextTwo().setTextColor(ViewUtil.getResColor(R.color.app_theme_colors));
                break;
            case 3:
                mBottombar.getImageThree().setImageResource(R.mipmap.i_three_press);
                mBottombar.getTextThree().setTextColor(ViewUtil.getResColor(R.color.app_theme_colors));
                break;
            case 4:
                mBottombar.getImageFour().setImageResource(R.mipmap.i_four_press);
                mBottombar.getTextFour().setTextColor(ViewUtil.getResColor(R.color.app_theme_colors));
                break;
        }
    }


    protected void initEvents() {
        mBottombar.getLayoutOne().setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        loadLinearLayout(1);
                    }
                });
        mBottombar.getLayoutTwo().setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        loadLinearLayout(2);
                    }
                });
        mBottombar.getLayoutThree().setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        loadLinearLayout(3);
                    }
                });
        mBottombar.getLayoutFour().setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        loadLinearLayout(4);
                    }
                });
    }

    /**
     * 启动Activity
     * 作者：LinHao
     * 邮箱：439224@qq.com
     * 创建时间：2016/6/2 16:03
     */
    private void switchActivity(int index) {
        Window subActivity = null;
        switch (index){
            case 1:
//                if (oneActivity == null) {
                Intent intent = new Intent(this, MainOneActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                oneActivity = getLocalActivityManager().startActivity("OneActivity", intent);//
//                }
                subActivity = oneActivity;
                break;
            case 2:
//                if (twoActivity == null) {
                Intent intentTwo = new Intent(this, MainTwoActivity.class);
                intentTwo.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                twoActivity = getLocalActivityManager().startActivity("TwoActivity", intentTwo);
//                }
                subActivity = twoActivity;
                break;
            case 3:
//                if (threeActivity == null) {
                Intent intentThree = new Intent(this, MainThreeActivity.class);
                intentThree.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                threeActivity = getLocalActivityManager().startActivity("ThreeActivity", intentThree);
//                }
                subActivity = threeActivity;
                break;
            case 4:
                //if (fourActivity == null) {
                Intent intentFour = new Intent(this, MainFourActivity.class);
                intentFour.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                fourActivity = getLocalActivityManager().startActivity("FourActivity", intentFour);
                // }
                subActivity = fourActivity;
                break;
        }

        container.addView(subActivity.getDecorView(), new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
    }

    /**
     * 两次BACK键退出
     *
     * @author LinHao 439224@qq.com
     * @version 创建时间： 2013-8-13 下午2:16:55
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog(MainActivity.this).builder()
                    .setTitle("提示")
                    .setMsg("确定要退出吗？")
                    .setCancelable(false)
                    .setPositiveButton("确认", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 退出服务
                            Intent intentService = new Intent(
                                    BaseApp.AppContext,
                                    HttpService.class);
                            stopService(intentService);

                            BaseApp.getInstance().exit();
                        }
                    }).setNegativeButton("取消", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            }).show();
        }
        return false;
    }


    private IntentFilter sInentFilte;
    public static final String beoadcastString = "SonActivityToMainActivity";
    private BroadcastReceiver broadcastReceiverFromMain = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (beoadcastString.equals(action)) {
                String type = intent.getStringExtra("TYPE");
                if (type.equals("five")){
                    loadLinearLayout(4);
                }
            }
        }
    };

    public void registerSonToMain() {
        sInentFilte = new IntentFilter();
        sInentFilte.addAction(beoadcastString);

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (null != sInentFilte && null != broadcastReceiverFromMain) {
            this.registerReceiver(broadcastReceiverFromMain, sInentFilte);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != sInentFilte && null != broadcastReceiverFromMain) {
            this.unregisterReceiver(broadcastReceiverFromMain);
        }
    }
}
