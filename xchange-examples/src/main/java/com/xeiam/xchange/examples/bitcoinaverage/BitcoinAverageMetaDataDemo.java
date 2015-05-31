package com.xeiam.xchange.examples.bitcoinaverage;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bitcoinaverage.BitcoinAverageExchange;

public class BitcoinAverageMetaDataDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BitcoinAverageExchange.class.getName());
    exchange.remoteInit();

    System.out.println(exchange.getMetaData().toString());

  }

}
