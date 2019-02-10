package org.knowm.xchange.cobinhood.service;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;

import java.io.IOException;

public class CobinhoodPrivateTest {

    private Exchange exchange = CobinhoodKeys.getExchange();

    @Test
    public void accountTest() throws IOException {
        System.out.println(exchange.getAccountService().getAccountInfo().getWallet().getBalance(Currency.ETH));
    }
}
