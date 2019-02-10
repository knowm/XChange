package org.knowm.xchange.cobinhood.service;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;

import java.io.IOException;

public class CobinhoodPublicTest {

    private Exchange exchange = CobinhoodKeys.getExchange();

    @Test
    public void marketDataTest() throws IOException {

        System.out.println(exchange.getMarketDataService().getOrderBook(CurrencyPair.ETH_BTC).getBids());
        System.out.println(exchange.getMarketDataService().getOrderBook(CurrencyPair.ETH_BTC).getAsks());
    }

    @Test //failed
    public void metaDataTest(){
        exchange.getExchangeSymbols().forEach(currencyPair -> {
            System.out.println(currencyPair);
        });
        exchange.getExchangeMetaData().getCurrencyPairs().entrySet().forEach(currencyPairCurrencyPairMetaDataEntry -> {
            System.out.println(currencyPairCurrencyPairMetaDataEntry.getKey());
            System.out.println(currencyPairCurrencyPairMetaDataEntry.getValue());
        });
    }

    @Test
    public void decimalTest(){
        String numberD = String.valueOf("0.000001");
        numberD = numberD.substring ( numberD.indexOf ( "." ) +1);
        System.out.println(numberD.length());
    }
}
