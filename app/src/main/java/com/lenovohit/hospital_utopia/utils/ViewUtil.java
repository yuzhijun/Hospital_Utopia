package com.lenovohit.hospital_utopia.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.mg.core.utils.BaseViewUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class ViewUtil extends BaseViewUtil {

	private static Bitmap compressImage(Bitmap image, String filePath) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			options -= 10;// 每次都减少10
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		// 保存压缩后的图片到SD卡
		try {
			FileOutputStream out = new FileOutputStream(filePath);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, 70, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		return bitmap;
	}

	/**
	 * 从路径中获取文件名
	 * 
	 * @author LinHao 439224@qq.com
	 * @version 创建时间： 2013-11 -30 下午3:39:16
	 */
	public static String getFileNameByFilePath(String filePath) {
		return filePath.substring(filePath.lastIndexOf("/") + 1);
	}

	/**
	 * 根据路径获得图片并压缩，省内存的方式返回bitmap用于显示
	 * 
	 * @author LinHao 439224@qq.com
	 * @version 创建时间： 2013-8-10 下午3:42:50
	 */
	public static Bitmap getSmallBitmap(String filePath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(filePath, newOpts);// 此时返回bm为空

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(filePath, newOpts);
		// 获取图片的旋转角度，有些系统把拍照的图片旋转了，有的没有旋转
		int degree = readPictureDegree(filePath);
		bitmap = rotaingImageView(degree, bitmap);
		return compressImage(bitmap, filePath);// 压缩好比例大小后再进行质量压缩
	}

	/**
	 * 旋转图片
	 * 
	 * @author LinHao 439224@qq.com
	 * @version 创建时间： 2013-12-9 下午5:03:51
	 */
	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		if (!isNotEmpty(bitmap)) {
			return null;
		}
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		;
		matrix.postRotate(angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

	/**
	 * 读取图片属性：旋转的角度
	 * 
	 * @author LinHao 439224@qq.com
	 * @version 创建时间： 2013-12-9 下午5:04:00
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * 生成缩略图。
	 * 
	 * @author LinHao 439224@qq.com
	 * @version 创建时间： 2014-1-2 下午3:56:02
	 *          -------------------------------------begin
	 *          -----------------------------------------
	 */
	public static Bitmap extractMiniThumb(Bitmap source, int width, int height) {
		return extractMiniThumb(source, width, height, true);
	}

	public static Bitmap extractMiniThumb(Bitmap source, int width, int height,
										  boolean recycle) {
		if (source == null) {
			return null;
		}

		float scale;
		if (source.getWidth() < source.getHeight()) {
			scale = width / (float) source.getWidth();
		} else {
			scale = height / (float) source.getHeight();
		}
		Matrix matrix = new Matrix();
		matrix.setScale(scale, scale);
		Bitmap miniThumbnail = transform(matrix, source, width, height, false);

		if (recycle && miniThumbnail != source) {
			source.recycle();
		}
		return miniThumbnail;
	}

	public static Bitmap transform(Matrix scaler, Bitmap source,
								   int targetWidth, int targetHeight, boolean scaleUp) {
		int deltaX = source.getWidth() - targetWidth;
		int deltaY = source.getHeight() - targetHeight;
		if (!scaleUp && (deltaX < 0 || deltaY < 0)) {
			Bitmap b2 = Bitmap.createBitmap(targetWidth, targetHeight,
					Bitmap.Config.ARGB_8888);
			Canvas c = new Canvas(b2);

			int deltaXHalf = Math.max(0, deltaX / 2);
			int deltaYHalf = Math.max(0, deltaY / 2);
			Rect src = new Rect(deltaXHalf, deltaYHalf, deltaXHalf
					+ Math.min(targetWidth, source.getWidth()), deltaYHalf
					+ Math.min(targetHeight, source.getHeight()));
			int dstX = (targetWidth - src.width()) / 2;
			int dstY = (targetHeight - src.height()) / 2;
			Rect dst = new Rect(dstX, dstY, targetWidth - dstX, targetHeight
					- dstY);
			c.drawBitmap(source, src, dst, null);
			return b2;
		}
		float bitmapWidthF = source.getWidth();
		float bitmapHeightF = source.getHeight();

		float bitmapAspect = bitmapWidthF / bitmapHeightF;
		float viewAspect = (float) targetWidth / targetHeight;

		if (bitmapAspect > viewAspect) {
			float scale = targetHeight / bitmapHeightF;
			if (scale < .9F || scale > 1F) {
				scaler.setScale(scale, scale);
			} else {
				scaler = null;
			}
		} else {
			float scale = targetWidth / bitmapWidthF;
			if (scale < .9F || scale > 1F) {
				scaler.setScale(scale, scale);
			} else {
				scaler = null;
			}
		}
		Bitmap b1;
		if (scaler != null) {
			b1 = Bitmap.createBitmap(source, 0, 0, source.getWidth(),
					source.getHeight(), scaler, true);
		} else {
			b1 = source;
		}
		int dx1 = Math.max(0, b1.getWidth() - targetWidth);
		int dy1 = Math.max(0, b1.getHeight() - targetHeight);
		Bitmap b2 = Bitmap.createBitmap(b1, dx1 / 2, dy1 / 2, targetWidth,
				targetHeight);
		if (b1 != source) {
			b1.recycle();
		}
		return b2;
	}

	/**
	 * -------------------------------------end--------------------------------
	 * ---------
	 **/


    public static String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

	// 重新设置listview的高度
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);

			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() *
				(listAdapter.getCount() - 1));

		listView.setLayoutParams(params);
	}



	/**
	 * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
	 * @param context
	 * @return true 表示开启
	 */
	public static final boolean gPSIsOPen(final Context context) {
		LocationManager locationManager
				= (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		// 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
		boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
		boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		if (gps || network) {
			return true;
		}
		return false;
	}

	/**
	 * 返回计算得到的身份证号码
	 * */
	public static String getCalculateIDCardStr(String IDCardStr) {
		int sum = 0;
		int result = 0;
		int[] factor = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };// 所乘因数
		String[] verifyCode = { "1", "0", "x", "9", "8", "7", "6", "5", "4",
				"3", "2" };// 验证表

		if (ViewUtil.isStrEmpty(IDCardStr) || IDCardStr.length() != 18) {
			return "";
		}

		for (int i = 0; i < IDCardStr.length() - 1; i++) {
			sum = sum + Integer.parseInt(IDCardStr.substring(i, i + 1))
					* factor[i];
		}

		result = sum % 11;
		String resultIDCard = IDCardStr.substring(0, 17) + verifyCode[result];
		return resultIDCard;
	}

	public static boolean isMobileNO(String var0) {
		return Pattern.compile("[1][34578]\\d{9}").matcher(var0).matches();
	}

	/**
	 * 验证身份证号是否符合规则
	 * @param text 身份证号
	 * @return
	 */
	public static boolean personIdValidation(String text) {
		String regx = "[0-9]{17}x";
		String reg1 = "[0-9]{15}";
		String regex = "[0-9]{18}";
		return text.matches(regx) || text.matches(reg1) || text.matches(regex);
	}

	/**
	 * 当前时间的后一年
	 * @return
	 */
	public static String getNowAfterYear(int year){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, year);
		return  (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(cal.getTime());
	}

	/**
	 * 当前时间加1天
	 * @return
	 */
	public static String getTimeAfterDay(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE,1);
		return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(cal.getTime());
	}

	/**
	 * 获取两个日期之间的间隔天数
	 * @return
	 */
	public static int getGapCount(Date startDate, Date endDate) {
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTime(startDate);
		fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
		fromCalendar.set(Calendar.MINUTE, 0);
		fromCalendar.set(Calendar.SECOND, 0);
		fromCalendar.set(Calendar.MILLISECOND, 0);

		Calendar toCalendar = Calendar.getInstance();
		toCalendar.setTime(endDate);
		toCalendar.set(Calendar.HOUR_OF_DAY, 0);
		toCalendar.set(Calendar.MINUTE, 0);
		toCalendar.set(Calendar.SECOND, 0);
		toCalendar.set(Calendar.MILLISECOND, 0);
		return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
	}

	/**
	 * 把字符串转为日期  
	 * @return
	 */
	public static Date ConverToDate(String strDate) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.parse(strDate);
	}

	public static Date fullConverToDate(String strDate) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		return df.parse(strDate);
	}

	/**
	 * 把两个日期间的所有日期返回(间隔小于一周则返回一周的所有时间)
	 * */
	public static List<String> getDatePeriod(String startDateStr, String endDateStr) throws ParseException {
		//将字符串时间转化成date格式
		Date startDate = ConverToDate(startDateStr);
		Date endDate = ConverToDate(endDateStr);
		//获取两个时间间的间隔天数
		int gapDay = getGapCount(startDate,endDate) < 7 ? 6 : getGapCount(startDate,endDate);

		ArrayList timeList = new ArrayList();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar startCal =  Calendar.getInstance();
		startCal.setTime(startDate);
		timeList.add(dateFormat.format(startCal.getTime()));

		for(int var5 = 0; var5 < gapDay; ++var5) {
			startCal.add(Calendar.DATE,1);
			timeList.add(dateFormat.format(startCal.getTime()));
		}
		return timeList;
	}

	/**
	 * 判断当前时间是星期几
	 * */
	public static String getWeekDay(String dateStr) throws ParseException {
		String Week = "周";
		Date date = ConverToDate(dateStr);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar startCal =  Calendar.getInstance();
		startCal.setTime(date);
		switch (startCal.get(Calendar.DAY_OF_WEEK)){
			case 1:
				Week += "天";
				break;
			case 2:
				Week += "一";
				break;
			case 3:
				Week += "二";
				break;
			case 4:
				Week += "三";
				break;
			case 5:
				Week += "四";
				break;
			case 6:
				Week += "五";
				break;
			case 7:
				Week += "六";
				break;
			default:
				break;
		}
		return Week;
	}

	/**
	 * 判断时间是上午还是下午
	 * */
	public static boolean isMorning(String dateStr) throws Exception {
		Date date = fullConverToDate(dateStr);
		Calendar startCal =  Calendar.getInstance();
		startCal.setTime(date);
		int apm = startCal.get(Calendar.AM_PM);
		//0上午1下午
		return apm == 0? true:false;
	}

	/**
	 * 判断是否是同一天
	 * */
	public static boolean isSameDay(String dateOne, String dateTwo){
		if (dateOne.length() >= 10 && dateTwo.length() >= 10)
		{
			if (dateOne.substring(0,10).equalsIgnoreCase(dateTwo.substring(0,10))){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}

	/**
	 * 替代findviewById方法
	 */
	public static <T extends View> T find(View view, int id)
	{
		return (T) view.findViewById(id);
	}

	/**
	 * 设置名字中间为星号
	 * */
	public static String setMiddleAsterisks(String str){
		if (ViewUtil.isStrEmpty(str)){
			return "";
		}else{
			try{
				int index = str.length() >= 2 ? 2 : 1;
				String AsteriskStr = "*";
				if (index == 2){
					String subStr = str.substring(1, index);
					return str.replaceAll(subStr,AsteriskStr);
				}else{
					return str;
				}
			}catch (Exception e){
				return "";
			}
		}
	}
}