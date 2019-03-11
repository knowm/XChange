package org.knowm.xchange.examples.wex.meta;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.examples.wex.WexExamplesUtils;
import org.knowm.xchange.wex.v3.WexExchange;
import org.knowm.xchange.wex.v3.dto.marketdata.WexExchangeInfo;
import org.knowm.xchange.wex.v3.dto.meta.WexMetaData;
import org.knowm.xchange.wex.v3.service.WexMarketDataService;

/** Demo requesting account info at BTC-E */
public class WexMetaDataDemo {

  public static void main(String[] args) throws IOException {

    WexExchange btce = (WexExchange) WexExamplesUtils.createExchange();
    rawLocal(btce);

    rawRemote(btce);

    generic(btce);
  }

  private static void rawLocal(WexExchange exchange) throws IOException {
    WexMetaData wexMetaData = exchange.getWexMetaData();
    System.out.println(
        "Wex local meta data: amountScale="
            + wexMetaData.amountScale
            + " public data TTL seconds"
            + wexMetaData.publicInfoCacheSeconds);
  }

  private static void rawRemote(Exchange btce) throws IOException {
    WexExchangeInfo btceInfo = ((WexMarketDataService) btce.getMarketDataService()).getBTCEInfo();
    System.out.println("Wex remote meta data: " + btceInfo);
  }

  private static void generic(Exchange exchange) throws IOException {
    ExchangeMetaData metaData = (ExchangeMetaData) exchange.getExchangeMetaData();
    System.out.println("Wex generic meta data: " + metaData);

    exchange
        .getTradeService()
        .verifyOrder(
            new MarketOrder.Builder(Order.OrderType.ASK, CurrencyPair.BTC_EUR)
                .originalAmount(BigDecimal.ONE)
                .build());
  }
}
