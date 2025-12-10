package com.springboot.bankingapp.utils;

import com.springboot.bankingapp.domain.entities.types.CurrencyType;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CurrencyConvertor {
    private static final Map<String, BigDecimal> conversionRates = new HashMap<>();

    static {
        conversionRates.put("EUR-RON", new BigDecimal("4.97"));
        conversionRates.put("EUR-USD", new BigDecimal("1.09"));
        conversionRates.put("EUR-EUR", new BigDecimal("1.00"));

        conversionRates.put("USD-EUR", new BigDecimal("0.92"));
        conversionRates.put("USD-RON", new BigDecimal("4.56"));
        conversionRates.put("USD-USD", new BigDecimal("1.00"));

        conversionRates.put("RON-EUR", new BigDecimal("0.20"));
        conversionRates.put("RON-USD", new BigDecimal("0.22"));
        conversionRates.put("RON-RON", new BigDecimal("1.00"));
    }

    public static BigDecimal getConversionRate(CurrencyType source, CurrencyType target) {
        String key = source.name() + "-" + target.name();
        return conversionRates.get(key);
    }
}