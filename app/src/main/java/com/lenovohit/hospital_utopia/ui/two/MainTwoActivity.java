package com.lenovohit.hospital_utopia.ui.two;

import android.view.View;
import android.widget.Button;

import com.lenovohit.hospital_utopia.R;
import com.mg.core.base.BaseActivity;
import com.mg.core.net.ThreadMessage;

/**
 * Created by yuzhijun on 2017/2/3.
 */

public class MainTwoActivity extends BaseActivity {
    private Button btnPost;
    @Override
    protected void initViews() {
        setContentView(R.layout.lx_main_two_activity);

        btnPost = (Button) findViewById(R.id.btnPost);
    }

    @Override
    protected void initEvents() {
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LX_DiseaseListActivity.startDiseaseListActivity(MainTwoActivity.this,"0");
            }
        });
    }

    @Override
    protected void receiveRequest(ThreadMessage threadMessage) {

    }
}
