package org.knowm.xchange.okex.service;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;

/**
 * It is used to obtain the list according to the SWAP type, sometimes some CurrencyPair may not be passed
 * <p> @Date : 2023/4/24 </p>
 * <p> @Project : XChange</p>
 *
 * <p> @author konbluesky </p>
 */
public class EmptyFuturesContract extends FuturesContract {

    private static final String DEFAULT_SYMBOL = "//SWAP";

    public EmptyFuturesContract(CurrencyPair currencyPair, String prompt) {
        super(currencyPair, prompt);
    }

    public EmptyFuturesContract(String symbol) {
        super(DEFAULT_SYMBOL);
    }

    public EmptyFuturesContract() {
        super(DEFAULT_SYMBOL);
    }

    @Override
    public String toString() {
        return "";
    }
}
