package com.okcoin.commons.okex.open.api.bean.swap.param;

import java.math.BigDecimal;

public class LevelRateParam {

    /**
     * 1.LONG
     * 2.SHORT
     * 3.全仓杠杆
     */
    private Integer side;
    private BigDecimal leverage;

    public LevelRateParam(Integer side, BigDecimal levelRate) {
        this.side = side;
        this.leverage = levelRate;
    }

    public LevelRateParam() {
    }

    public Integer getSide() {
        return side;
    }

    public void setSide(Integer side) {
        this.side = side;
    }

    public BigDecimal getLevelRate() {
        return leverage;
    }

    public void setLevelRate(BigDecimal levelRate) {
        this.leverage = levelRate;
    }


}
