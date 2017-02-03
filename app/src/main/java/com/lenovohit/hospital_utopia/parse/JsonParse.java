package com.lenovohit.hospital_utopia.parse;

import com.lenovohit.hospital_utopia.domain.CapitalAndCity;
import com.mg.core.base.BaseParse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JsonParse extends BaseParse {

	/**
	 * 验证返回JSON
	 * @param content
	 * @return
	 */
	public static String setJsonCheck(String content){
		String jsonString = "";
		try {
			JSONObject jsonObject = new JSONObject(content);
			if (content.indexOf("\"HasError\":1") > -1) {
				jsonString = jsonObject.getString("ErrorMessage");
			}else{
				return "";
			}
		} catch (JSONException e) {
		}
		return jsonString;
	}
	
	private static String getJsonString(String content) {
		String jsonString = "";
		try {
			JSONObject jsonObject = new JSONObject(content);
			if (content.indexOf("\"HasError\":0") > -1) {
				jsonString = jsonObject.getString("Data");
			}else{
				return "";
			}
		} catch (JSONException e) {
		}
		return jsonString;
	}


	public static List<CapitalAndCity> parserCapitalAndCitys(String content) {
		return getJsonList(getJsonString(content), CapitalAndCity.class);
	}
}
