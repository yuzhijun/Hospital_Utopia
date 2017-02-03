package com.lenovohit.hospital_utopia.publics.crash;

import com.lenovohit.hospital_utopia.utils.ViewUtil;
import com.mg.core.base.BaseApp;
import com.mg.core.colog.COLog;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 全局错误信息搜集
 * 
 * @author LinHao 439224@qq.com
 * @version 创建时间： 2013-12-13 下午2:01:33
 */
public class MyCrashHandler implements UncaughtExceptionHandler {
	private static final String TAG = "MythouCrashHandler";
	public static final String ERROR_LOG = "Error_Log";
	private UncaughtExceptionHandler defaultUEH;
	private static MyCrashHandler INSTANCE = new MyCrashHandler();

	public static MyCrashHandler getInstance() {
		return INSTANCE;
	}

	// 构造函数，获取默认的处理方法
	public MyCrashHandler() {
		this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
	}

	// 这个接口必须重写，用来处理我们的异常信息
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		// 获取跟踪的栈信息，除了系统栈信息，还把手机型号、系统版本、编译版本的唯一标示
		StackTraceElement[] trace = ex.getStackTrace();
		StackTraceElement[] trace2 = new StackTraceElement[trace.length + 3];
		System.arraycopy(trace, 0, trace2, 0, trace.length);
		trace2[trace.length + 0] = new StackTraceElement("Android", "MODEL-机型",
				android.os.Build.MODEL, -1);
		trace2[trace.length + 1] = new StackTraceElement("Android",
				"VERSION-版本", android.os.Build.VERSION.RELEASE, -1);
		trace2[trace.length + 2] = new StackTraceElement("Android",
				"FINGERPRINT-编译环境", android.os.Build.FINGERPRINT, -1);
		trace2[trace.length + 3] = new StackTraceElement("Android", "APP版本=",
				ViewUtil.getVersionName(), -1);
		// 追加信息，因为后面会回调默认的处理方法
		ex.setStackTrace(trace2);
		ex.printStackTrace(printWriter);
		// 把上面获取的堆栈信息转为字符串，打印出来
		String stacktrace = result.toString();
		printWriter.close();
		COLog.d(TAG, stacktrace);
		// 这里把刚才异常堆栈信息写入SD卡的Log日志里面
		// if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		// {

		// String sdcardPath =
		// Environment.getExternalStorageDirectory().getPath();
		writeLog(stacktrace);
		// }
		defaultUEH.uncaughtException(thread, ex);
	}

	// 写入Log信息的方法，写入到SD卡里面
	private void writeLog(String log) {
		// CharSequence timestamp = DateFormat.format("yyyyMMdd_kkmmss",
		// System.currentTimeMillis());
		// String filename = UploadImage.mars_file.getAbsolutePath() + "/_" +
		// timestamp + ".log";
		// try{
		// FileOutputStream stream = new FileOutputStream(filename);
		// OutputStreamWriter output = new OutputStreamWriter(stream);
		// BufferedWriter bw = new BufferedWriter(output);
		// //写入相关Log到文件
		// bw.write(log);
		// bw.newLine();
		// bw.close();
		// output.close();
		ViewUtil.setShardPString(ERROR_LOG, log);
		BaseApp.getInstance().exit();
		// } catch (IOException e){
		// e.printStackTrace();
		// }
	}
}
