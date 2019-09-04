package org.knowm.xchange.utils;

import java.math.BigDecimal;

public class PriceUtils {

    public static BigDecimal tickRounding(BigDecimal price, BigDecimal tickSize){
        return BigDecimal.valueOf(price.doubleValue() - price.doubleValue() % tickSize.doubleValue() + ((price.doubleValue() % tickSize.doubleValue() < tickSize.doubleValue() / 2) ? 0.0 : tickSize.doubleValue())).stripTrailingZeros();
    }
}
