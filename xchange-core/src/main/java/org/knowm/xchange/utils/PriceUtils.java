package org.knowm.xchange.utils;

import java.math.BigDecimal;

public class PriceUtils {

    public static BigDecimal tickRounding(BigDecimal price, double tickSize){
        return BigDecimal.valueOf(price.doubleValue() - price.doubleValue() % tickSize + ((price.doubleValue() % tickSize < tickSize / 2) ? 0.0 : tickSize)).stripTrailingZeros();
    }
}
