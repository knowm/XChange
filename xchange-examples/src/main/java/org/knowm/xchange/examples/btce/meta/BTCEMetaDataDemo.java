package org.knowm.xchange.examples.btce.meta;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btce.v3.BTCEExchange;
import org.knowm.xchange.btce.v3.dto.marketdata.BTCEExchangeInfo;
import org.knowm.xchange.btce.v3.dto.meta.BTCEMetaData;
import org.knowm.xchange.btce.v3.service.BTCEMarketDataService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.examples.btce.BTCEExamplesUtils;

/**
 * Demo requesting account info at BTC-E
 */
public class BTCEMetaDataDemo {

  public static void main(String[] args) throws IOException {

    BTCEExchange btce = (BTCEExchange) BTCEExamplesUtils.createExchange();
    rawLocal(btce);

    rawRemote(btce);

    generic(btce);
  }

  private static void rawLocal(BTCEExchange exchange) throws IOException {
    BTCEMetaData btceMetaData = exchange.getBtceMetaData();
    System.out
        .println("BTCE local meta data: amountScale=" + btceMetaData.amountScale + " public data TTL seconds" + btceMetaData.publicInfoCacheSeconds);
  }

  private static void rawRemote(Exchange btce) throws IOException {
    BTCEExchangeInfo btceInfo = ((BTCEMarketDataService) btce.getMarketDataService()).getBTCEInfo();
    System.out.println("BTCE remote meta data: " + btceInfo);

  }

  private static void generic(Exchange exchange) throws IOException {
    ExchangeMetaData metaData = (ExchangeMetaData) exchange.getExchangeMetaData();
    System.out.println("BTCE generic meta data: " + metaData);

    exchange.getTradeService().verifyOrder(new MarketOrder.Builder(Order.OrderType.ASK, CurrencyPair.BTC_EUR).tradableAmount(BigDecimal.ONE).build());
  }

}
