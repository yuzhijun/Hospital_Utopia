package com.lenovohit.hospital_utopia.net.receive;

import com.lenovohit.hospital_utopia.data.HospitalData;
import com.lenovohit.hospital_utopia.domain.CapitalAndCity;
import com.lenovohit.hospital_utopia.parse.JsonParse;
import com.mg.core.net.HttpReturnMessage;

import java.util.List;

public class InterfaceReceiveService {


	public static void i_getCapitalAndCity(HttpReturnMessage result) {
		String content = result.getResultContent();
		List<CapitalAndCity> capitalAndCities = JsonParse.parserCapitalAndCitys(content);
		HospitalData.setCapitalAndCities(capitalAndCities);
	}
}
