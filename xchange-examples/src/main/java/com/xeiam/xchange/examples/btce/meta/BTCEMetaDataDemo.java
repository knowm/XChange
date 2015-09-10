package com.xeiam.xchange.examples.btce.meta;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btce.v3.BTCEExchange;
import com.xeiam.xchange.btce.v3.dto.marketdata.BTCEExchangeInfo;
import com.xeiam.xchange.btce.v3.dto.meta.BTCEMetaData;
import com.xeiam.xchange.btce.v3.service.polling.BTCEMarketDataService;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.meta.ExchangeMetaData;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.examples.btce.BTCEExamplesUtils;

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
    BTCEExchangeInfo btceInfo = ((BTCEMarketDataService) btce.getPollingMarketDataService()).getBTCEInfo();
    System.out.println("BTCE remote meta data: " + btceInfo);

  }

  private static void generic(Exchange exchange) throws IOException {
    ExchangeMetaData metaData = (ExchangeMetaData) exchange.getMetaData();
    System.out.println("BTCE generic meta data: " + metaData);

    exchange.getPollingTradeService()
        .verifyOrder(new MarketOrder.Builder(Order.OrderType.ASK, CurrencyPair.BTC_EUR).tradableAmount(BigDecimal.ONE).build());
  }

}
