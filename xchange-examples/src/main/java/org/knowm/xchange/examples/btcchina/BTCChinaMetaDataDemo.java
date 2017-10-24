package org.knowm.xchange.examples.btcchina;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.btcchina.BTCChinaExchange;
import org.knowm.xchange.dto.meta.ExchangeMetaData;

public class BTCChinaMetaDataDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get ANX exchange API using default settings
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BTCChinaExchange.class.getName());
    ExchangeMetaData exchangeMetaData = exchange.getExchangeMetaData();
    System.out.println(exchangeMetaData.toJSONString());

  }

}
