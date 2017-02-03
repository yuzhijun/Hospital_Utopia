package com.mg.core.net;


import com.lenovohit.hospital_utopia.R;
import com.lenovohit.hospital_utopia.utils.ViewUtil;

/**
 * 操作符定义
 * 
 * @author LinHao 439224@qq.com 创建时间： 2013-7-26 下午3:51:02
 */

public enum OperateCode {

	i_getCapitalAndCity(R.string.i_getCapitalAndCity),
	i_getUpdateURL(R.string.i_getUpdateURL),
	i_SearchDisease(R.string.i_SearchDisease);

	private final int interfaceResId;

	private OperateCode(int interfaceResId) {
		this.interfaceResId = interfaceResId;
	}

	/**
	 * 获取资源文件的id
	 * 
	 * @author LinHao 439224@qq.com 创建时间： 2013-7-26 下午3:30:34
	 */
	public int getResId() {
		return interfaceResId;
	}

	public String getResString() {
		return ViewUtil.getString(interfaceResId);
	}

	/**
	 * 根据资源文件id查找数据
	 * 
	 * @author LinHao 439224@qq.com 创建时间： 2013-7-26 下午3:30:53
	 */
	public static OperateCode getOperateCodeByResId(int resId) {
		OperateCode[] datas = OperateCode.values();
		for (OperateCode operateCode : datas) {
			if (operateCode.getResId() == resId) {
				return operateCode;
			}
		}
		return null;
	}
}
