package com.xeiam.xchange.examples.bitcoincharts;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bitcoincharts.BitcoinChartsExchange;

public class BitcoinChartsMetaDataDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BitcoinChartsExchange.class.getName());
    exchange.remoteInit();
    System.out.println(exchange.getMetaData().toString());
  }

}
