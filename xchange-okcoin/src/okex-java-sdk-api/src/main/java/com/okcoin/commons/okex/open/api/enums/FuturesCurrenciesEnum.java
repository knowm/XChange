package com.okcoin.commons.okex.open.api.enums;

/**
 * Futures Currencies (Updated on: 2018-03-13 17:39:09)
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/13 17:37
 */
public enum FuturesCurrenciesEnum {

    BTC(0), LTC(1), ETH(2), ETC(4), BCH(5), XRP(15), EOS(20), BTG(10);
    private int symbol;

    FuturesCurrenciesEnum(int symbol) {
        this.symbol = symbol;
    }

    public int getSymbol() {
        return symbol;
    }
}
