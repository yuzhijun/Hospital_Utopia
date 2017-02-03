package com.lenovohit.hospital_utopia.ui.two;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.lenovohit.hospital_utopia.R;
import com.lenovohit.hospital_utopia.data.HospitalData;
import com.lenovohit.hospital_utopia.domain.CapitalAndCity;
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

public class LX_CapitalAndCityActivity extends BaseActivity{
    private CommonAdapter<CapitalAndCity> adapter;
    private List<CapitalAndCity> mCapitalAndCities = new ArrayList<>();
    private ListView lvListview;

    @Override
    protected void initViews() {
        setContentView(R.layout.lx_common_activity);
        mTopBar.setVisibility(View.VISIBLE);
        mBottombar.setVisibility(View.GONE);
        mTopBar.SetTvName("省市列表");

        lvListview = (ListView) findViewById(R.id.lvListview);
        adapter = new CommonAdapter<CapitalAndCity>(this,mCapitalAndCities,R.layout.common_list_row) {
            @Override
            public void convert(ViewHolder viewHolder, CapitalAndCity capitalAndCity, int i) {
                TextView name = viewHolder.getView(R.id.name);

                name.setText(ViewUtil.isStrEmpty(capitalAndCity.getTitle())?"":capitalAndCity.getTitle());
            }
        };
        lvListview.setAdapter(adapter);


        getCapitalAndCity();
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void receiveRequest(ThreadMessage threadMessage) {
        hideProgressDialog();
        OperateCode code = threadMessage.getOperateCode();
        switch (code) {
            case i_getCapitalAndCity:
                //逻辑在receiveService编写了！！！
                List<CapitalAndCity> capitalAndCities = HospitalData.getCapitalAndCities();
                if (capitalAndCities == null || capitalAndCities.size() <= 0){
                    ViewUtil.showToast("列表为空",false);
                    return;
                }

                mCapitalAndCities.clear();
                mCapitalAndCities.addAll(capitalAndCities);
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }


    public void getCapitalAndCity(){
        showProgressDialog();
        sendToBackgroud(OperateCode.i_getCapitalAndCity);
    }
}
