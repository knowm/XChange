package org.knowm.xchange.bybit.examples;

import java.io.IOException;
import java.util.Date;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.CandleStick;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParam;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParamWithLimit;

public class BybitGetCandlesHistoryExample {

  public static void main(String[] args) throws IOException {
    ExchangeSpecification exchangeSpecification =
        new BybitExchange().getDefaultExchangeSpecification();
    exchangeSpecification.setExchangeSpecificParametersItem(Exchange.USE_SANDBOX, true);
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

    MarketDataService marketDataService = exchange.getMarketDataService();

    // 3600 seconds interval (1 hour)
    long periodInSecs = 3600;
    // Last 240 minutes
    long endTime = System.currentTimeMillis();
    long startTime = endTime - (240 * 60 * 1000);

    DefaultCandleStickParam params =
        new DefaultCandleStickParam(new Date(startTime), new Date(endTime), periodInSecs);

    CandleStickData candleStickData =
        marketDataService.getCandleStickData(CurrencyPair.BTC_USDT, params);

    System.out.println("Instrument: " + candleStickData.getInstrument());
    for (CandleStick candleStick : candleStickData.getCandleSticks()) {
      System.out.println(candleStick);
    }
    startTime = endTime - (24 * 10 * 60 * 60 * 1000); // 10 days, 240 bars
    DefaultCandleStickParamWithLimit paramsWithLimit =
        new DefaultCandleStickParamWithLimit(new Date(startTime), new Date(endTime), periodInSecs, 240);
    candleStickData =
        marketDataService.getCandleStickData(CurrencyPair.BTC_USDT, paramsWithLimit);
    System.out.println("response size: " + candleStickData.getCandleSticks().size());
  }
}
