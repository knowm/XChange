package com.xeiam.xchange.justcoin.dto;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class Utils {

    public static String format(BigDecimal num) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(100);
        nf.setMinimumFractionDigits(0);
        nf.setGroupingUsed(false);
        return nf.format(num).replace(",", "."); //also replace fraction separator to '.' dot for different locales
    }
}
