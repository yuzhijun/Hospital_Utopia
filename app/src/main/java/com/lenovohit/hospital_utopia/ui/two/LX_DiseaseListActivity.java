package com.lenovohit.hospital_utopia.ui.two;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.lenovohit.hospital_utopia.R;
import com.lenovohit.hospital_utopia.data.CommonData;
import com.lenovohit.hospital_utopia.domain.CommonObj;
import com.lenovohit.hospital_utopia.utils.ViewUtil;
import com.mg.core.base.BaseActivity;
import com.mg.core.net.OperateCode;
import com.mg.core.net.ThreadMessage;
import com.mg.core.ui.adapter.CommonAdapter;
import com.mg.core.ui.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuzhijun on 2017/2/3.
 */

public class LX_DiseaseListActivity extends BaseActivity {
    private static final String ID = "ID";

    private CommonAdapter<CommonObj> adapter;
    private List<CommonObj> mDiseaseList = new ArrayList<>();
    private ListView lvListview;

    private String id = "";
    @Override
    protected void initViews() {
        setContentView(R.layout.lx_common_activity);
        mTopBar.setVisibility(View.VISIBLE);
        mBottombar.setVisibility(View.GONE);
        mTopBar.SetTvName("疾病列表");

        id = getIntent().getStringExtra(ID);

        lvListview = (ListView) findViewById(R.id.lvListview);
        adapter = new CommonAdapter<CommonObj>(this,mDiseaseList,R.layout.common_list_row) {
            @Override
            public void convert(ViewHolder viewHolder, CommonObj CommonObj, int i) {
                TextView name = viewHolder.getView(R.id.name);

                name.setText(ViewUtil.isStrEmpty(CommonObj.getTitle())?"":CommonObj.getTitle());
            }
        };
        lvListview.setAdapter(adapter);

        getDiseaseList();
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void receiveRequest(ThreadMessage threadMessage) {
        hideProgressDialog();
        OperateCode operateCode = threadMessage.getOperateCode();
        switch (operateCode){
            case i_SearchDisease:
                List<CommonObj> templist = CommonData.getCommonList();
                if (templist == null || templist.size() == 0) {
                    ViewUtil.showToast(templist == null ?"服务器未返回数据":"无对应查找关键字的数据", false);
                    return;
                }
                mDiseaseList.clear();
                mDiseaseList.addAll(templist);
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }

    }

    public void getDiseaseList(){
        showProgressDialog();
        sendToBackgroud(OperateCode.i_SearchDisease, new String[] { id });
    }

    public static void startDiseaseListActivity(Context context,String exid){
        Intent intent = new Intent(context,LX_DiseaseListActivity.class);
        intent.putExtra(ID,exid);
        context.startActivity(intent);
    }
}
