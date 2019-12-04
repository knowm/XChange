package org.knowm.xchange.examples.dsx.meta;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dsx.DSXExchangeV3;
import org.knowm.xchange.dsx.dto.marketdata.DSXExchangeInfo;
import org.knowm.xchange.dsx.dto.meta.DSXMetaData;
import org.knowm.xchange.dsx.service.DSXMarketDataServiceV3;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.examples.dsx.DSXExamplesUtils;

/** @author Mikhail Wall */
public class DSXMetaDataDemoV3 {

  public static void main(String[] args) throws IOException {

    DSXExchangeV3 dsx = (DSXExchangeV3) DSXExamplesUtils.createExchange(DSXExchangeV3.class);
    rawLocal(dsx);

    rawRemote(dsx);

    generic(dsx);
  }

  private static void rawLocal(DSXExchangeV3 exchange) throws IOException {

    DSXMetaData dsxMetaData = exchange.getDsxMetaData();
    System.out.println(
        "DSX local meta data: amountScale="
            + dsxMetaData.amountScale
            + " public data TTL seconds"
            + dsxMetaData.publicInfoCacheSeconds);
  }

  private static void rawRemote(Exchange dsx) throws IOException {

    DSXExchangeInfo dsxInfo = ((DSXMarketDataServiceV3) dsx.getMarketDataService()).getDSXInfo();
    System.out.println("DSX remote meta data: " + dsxInfo);
  }

  private static void generic(Exchange exchange) throws IOException {
    ExchangeMetaData metaData = exchange.getExchangeMetaData();
    System.out.println("DSX generic meta data: " + metaData);

    exchange
        .getTradeService()
        .verifyOrder(
            new MarketOrder.Builder(Order.OrderType.ASK, CurrencyPair.BTC_EUR)
                .originalAmount(BigDecimal.ONE)
                .build());
  }
}
