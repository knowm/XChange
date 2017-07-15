package org.knowm.xchange.examples.dsx.meta;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dsx.DSXExchange;
import org.knowm.xchange.dsx.dto.marketdata.DSXExchangeInfo;
import org.knowm.xchange.dsx.dto.meta.DSXMetaData;
import org.knowm.xchange.dsx.service.DSXMarketDataService;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.examples.dsx.DSXExamplesUtils;

/**
 * @author Mikhail Wall
 */
public class DSXMetaDataDemo {

  public static void main(String[] args) throws IOException {

    DSXExchange dsx = (DSXExchange) DSXExamplesUtils.createExchange();
    rawLocal(dsx);

    rawRemote(dsx);

    generic(dsx);
  }

  private static void rawLocal(DSXExchange exchange) throws IOException {

    DSXMetaData dsxMetaData = exchange.getDsxMetaData();
    System.out.println("DSX local meta data: amountScale=" + dsxMetaData.amountScale + " public data TTL seconds" + dsxMetaData.publicInfoCacheSeconds);
  }

  private static void rawRemote(Exchange dsx) throws IOException {

    DSXExchangeInfo dsxInfo = ((DSXMarketDataService) dsx.getMarketDataService()).getDSXInfo();
    System.out.println("DSX remote meta data: " + dsxInfo);
  }

  private static void generic(Exchange exchange) throws IOException {
    ExchangeMetaData metaData = exchange.getExchangeMetaData();
    System.out.println("DSX generic meta data: " + metaData);

    exchange.getTradeService().verifyOrder(new MarketOrder.Builder(Order.OrderType.ASK, CurrencyPair.BTC_EUR).tradableAmount(BigDecimal.ONE).build());
  }
}
