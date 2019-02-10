package org.knowm.xchange.examples.btcturk.trade;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcturk.dto.BTCTurkOrderTypes;
import org.knowm.xchange.btcturk.dto.trade.BTCTurkExchangeResult;
import org.knowm.xchange.btcturk.service.BTCTurkTradeServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.examples.btcturk.BTCTurkDemoUtils;
import org.knowm.xchange.service.trade.TradeService;

/** @author mertguner */
public class BTCTrukTradeDemo {

  public static void main(String[] args) throws IOException {
    // Use the factory to get BTCTurk exchange API using default settings
    Exchange exchange = BTCTurkDemoUtils.createExchange();

    // Interested in the public market data feed (no authentication)
    TradeService tradeDataService = exchange.getTradeService();

    generic(tradeDataService);
    raw((BTCTurkTradeServiceRaw) tradeDataService);
  }

  private static void generic(TradeService tradeService) throws IOException {
    MarketOrder marketOrder =
        new MarketOrder((OrderType.ASK), new BigDecimal(".1"), CurrencyPair.BTC_USD);
    String marketOrderReturnValue = tradeService.placeMarketOrder(marketOrder);
    System.out.println("Market Order return value: " + marketOrderReturnValue);
  }

  private static void raw(BTCTurkTradeServiceRaw tradeService) throws IOException {
    BTCTurkExchangeResult result =
        tradeService.placeMarketOrder(
            new BigDecimal(".1"), CurrencyPair.ETH_TRY, BTCTurkOrderTypes.Buy);
    System.out.println(result.getId());
  }
}
