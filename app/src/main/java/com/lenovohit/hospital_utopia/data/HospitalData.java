package com.lenovohit.hospital_utopia.data;

import com.lenovohit.hospital_utopia.domain.CapitalAndCity;

import java.util.List;

/**
 * Created by yuzhijun on 2017/2/3.
 */

public class HospitalData {

    public static List<CapitalAndCity> capitalAndCities;

    public static List<CapitalAndCity> getCapitalAndCities() {
        return capitalAndCities;
    }

    public static void setCapitalAndCities(List<CapitalAndCity> capitalAndCities) {
        HospitalData.capitalAndCities = capitalAndCities;
    }
}
