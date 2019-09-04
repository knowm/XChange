package org.knowm.xchange.utils;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class PriceUtilsTest {

    @Test
    public void tickSizeConvertionFormulaTest(){
        BigDecimal priceAfterRounding = PriceUtils.tickRounding(new BigDecimal(3502.31111111),BigDecimal.valueOf(0.25));
        System.out.println(priceAfterRounding);
        Assert.assertEquals(priceAfterRounding,BigDecimal.valueOf(3502.25));
    }
}
