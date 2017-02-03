package com.lenovohit.hospital_utopia.ui;

import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.lenovohit.hospital_utopia.R;
import com.mg.core.base.BaseActivity;
import com.mg.core.net.ThreadMessage;

public class WelcomeActivity extends BaseActivity {


	@Override
	protected void initViews() {
		setContentView(R.layout.welcome_activity);
		mTopBar.setVisibility(View.GONE);
		mBottombar.setVisibility(View.GONE);
		new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				startCOActivity(MainActivity.class);
				WelcomeActivity.this.finish();
			}
		}.sendEmptyMessageDelayed(1, 1000);
	}
	
	@Override
	protected void initEvents() {
	
	}

	@Override
	protected void receiveRequest(ThreadMessage arg0) {
		
	}

}
