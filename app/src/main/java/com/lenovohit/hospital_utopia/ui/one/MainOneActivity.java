package com.lenovohit.hospital_utopia.ui.one;

import android.view.View;
import android.widget.Button;

import com.lenovohit.hospital_utopia.R;
import com.lenovohit.hospital_utopia.ui.two.LX_CapitalAndCityActivity;
import com.mg.core.base.BaseActivity;
import com.mg.core.net.ThreadMessage;

/**
 * Created by yuzhijun on 2017/2/3.
 */

public class MainOneActivity extends BaseActivity {
    private Button btnNetTest;
    @Override
    protected void initViews() {
        setContentView(R.layout.lx_main_one_activity);

        btnNetTest = (Button) findViewById(R.id.btnNetTest);
    }

    @Override
    protected void initEvents() {
        btnNetTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCOActivity(LX_CapitalAndCityActivity.class);
            }
        });
    }

    @Override
    protected void receiveRequest(ThreadMessage threadMessage) {

    }
}
