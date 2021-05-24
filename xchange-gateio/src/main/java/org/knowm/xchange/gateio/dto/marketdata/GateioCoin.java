package org.knowm.xchange.gateio.dto.marketdata;

public class GateioCoin {

    boolean delisted;
    boolean withdraw_disabled;
    boolean withdraw_delayed;
    boolean deposit_disabled;
    boolean trade_disabled;

    public GateioCoin(boolean delisted,
                      boolean withdraw_disabled,
                      boolean withdraw_delayed,
                      boolean deposit_disabled,
                      boolean trade_disabled) {
        this.delisted = delisted;
        this.withdraw_disabled = withdraw_disabled;
        this.withdraw_delayed = withdraw_delayed;
        this.deposit_disabled = deposit_disabled;
        this.trade_disabled = trade_disabled;
    }

    @Override
    public String toString() {
        return "GateioCoin{" +
                "delisted=" + delisted +
                ", withdraw_disabled=" + withdraw_disabled +
                ", withdraw_delayed=" + withdraw_delayed +
                ", deposit_disabled=" + deposit_disabled +
                ", trade_disabled=" + trade_disabled +
                '}';
    }

    public boolean isDelisted() {
        return delisted;
    }

    public boolean isWithdraw_disabled() {
        return withdraw_disabled;
    }

    public boolean isWithdraw_delayed() {
        return withdraw_delayed;
    }

    public boolean isDeposit_disabled() {
        return deposit_disabled;
    }

    public boolean isTrade_disabled() {
        return trade_disabled;
    }
}
