package com.lenovohit.hospital_utopia.data;


import com.lenovohit.hospital_utopia.domain.CommonObj;

import java.util.List;

public class CommonData {
	private static List<CommonObj> commonList;

	public static List<CommonObj> getCommonList() {
		return commonList;
	}

	public static void setCommonList(List<CommonObj> commonList) {
		CommonData.commonList = commonList;
	}
}
