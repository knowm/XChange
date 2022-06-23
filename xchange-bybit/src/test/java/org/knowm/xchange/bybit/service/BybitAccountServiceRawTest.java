package org.knowm.xchange.bybit.service;

import org.junit.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.BybitBalances;

import java.io.IOException;


public class BybitAccountServiceRawTest {

    @Test
    public void testGetWalletBalances() throws IOException {
        BybitExchange bybitExchange = new BybitExchange();
        ExchangeSpecification exchangeSpecification = bybitExchange.getDefaultExchangeSpecification();
        exchangeSpecification.setApiKey(System.getenv("API_KEY"));
        exchangeSpecification.setSecretKey(System.getenv("SECRET_KEY"));
        exchangeSpecification.setShouldLoadRemoteMetaData(false);
        exchangeSpecification.setMetaDataJsonFileOverride(null);

        bybitExchange.applySpecification(exchangeSpecification);

        BybitAccountServiceRaw bybitAccountServiceRaw = new BybitAccountServiceRaw(bybitExchange);
        BybitResult<BybitBalances> walletBalances = bybitAccountServiceRaw.getWalletBalances();
        System.out.println(walletBalances);

    }

}
