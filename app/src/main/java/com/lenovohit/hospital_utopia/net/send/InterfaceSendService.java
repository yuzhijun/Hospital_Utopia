package com.lenovohit.hospital_utopia.net.send;

import com.google.gson.Gson;
import com.mg.core.net.HttpEntity;
import com.mg.core.net.ThreadMessage;

public class InterfaceSendService {

	private static final String HTTP_GET = "GET";
	private static final String HTTP_POST_BODY = "POSTBODY";
	private static final String PLATFORM_SERVICE = "eHealthPlatformService";
	private static final String BASE_SERVICE = "NeweHealthServices";
	private static Gson gson = new Gson();

	public static HttpEntity i_getCapitalAndCity(ThreadMessage threadMessage) {
		threadMessage.setHttpType(HTTP_GET);
		HttpEntity result = new HttpEntity(threadMessage);
		result.setUrl(result.getUrl());
		return  result;
	}
}
