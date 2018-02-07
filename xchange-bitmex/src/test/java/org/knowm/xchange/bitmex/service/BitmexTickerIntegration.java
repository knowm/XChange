package org.knowm.xchange.bitmex.service;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitmex.BitmexExchange;
import org.knowm.xchange.bitmex.dto.BitmexInstrument;
 import org.knowm.xchange.currency.Currency;

import java.io.IOException;
import java.util.List;

public class BitmexTickerIntegration {

    @Test
    public void fetchTickerTest() throws IOException {
        Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BitmexExchange.class.getName());
        BitmexMarketDataServiceRaw service = (BitmexMarketDataServiceRaw) exchange.getMarketDataService();

        List<BitmexInstrument> tickers = service.getTicker(Currency.XBT.getSymbol());
//    BitmexTicker ticker = tickers.get(0);
//    assertThat(ticker.getAskPrice()).isGreaterThan(BigDecimal.ZERO);
//    assertThat(ticker.getAskPrice()).isGreaterThan(BigDecimal.ZERO);
//    assertThat(ticker.getReferenceSymbol()).isEqualTo("");
//    assertThat(ticker.getSymbol()).isEqualTo("");
//    assertThat(ticker.getRootSymbol()).isEqualTo("");
//    assertThat(ticker.getUnderlyingSymbol()).isEqualTo("");
    }
}
