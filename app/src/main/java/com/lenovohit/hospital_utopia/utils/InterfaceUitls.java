package com.lenovohit.hospital_utopia.utils;

import com.lenovohit.hospital_utopia.R;
import com.mg.core.utils.BaseViewUtil;
import com.mg.core.utils.MGExtendUtil;

import java.io.IOException;
import java.util.Properties;

/**
 * 接口的帮助类
 * 
 * @author LinHao 439224@qq.com 创建时间： 2013-7-26 下午3:24:12
 */

public class InterfaceUitls {
	private static Properties pro;
	/**
	 * 基础链接
	 * @return
	 */
	public static String getBaseUrl() {
		String result = ""; //4900
		if (!ViewUtil.isNotEmpty(pro)){
			pro = new Properties();
			try {
				pro.load(InterfaceUitls.class.getResourceAsStream("/assets/config.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (BaseViewUtil.getIsDebug()) {
			result = pro.getProperty("base.url");
		}else{
			result = pro.getProperty("base.debug.url");
		}
		return result;
	}
	
	/**
	 * 上传接口
	 * 
	 * @author LinHao 439224@qq.com
	 * @version 创建时间： 2014-11-27 下午1:25:15
	 */
	public static String getUploadUrl() {
		return getBaseUrl() + ViewUtil.getString(R.string.i_setMessage);
	}
	
	/**
	 * httpkey
	 * @return
	 */
	public static String getHttpsKey(){
		String result = MGExtendUtil.decode("dkdrojd^Zez^pjzlc0U");
		return result;
	}
	
	/**
	 * 获取SO文件APPID
	 * 
	 * @author LinHao 439224@qq.com
	 * @version 创建时间： 2015-7 -16   上午9:32:45
	 */
	public static String getHospitalAppId(){
		String result = "LeHealthAndroid";
		return result;
	}
	
	/**
	 * 获取SO文件Secret
	 * 
	 * @author LinHao 439224@qq.com
	 * @version 创建时间： 2015-7 -16   上午9:35:45
	 */
	public static String getHospitalSecret(){
		String result = "3524280E18A3330F11390A8A48086CC3";
		return result;
	}
}
