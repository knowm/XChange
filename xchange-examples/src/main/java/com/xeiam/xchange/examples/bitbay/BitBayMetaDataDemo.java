package com.xeiam.xchange.examples.bitbay;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bitbay.BitbayExchange;

public class BitBayMetaDataDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get bitbay exchange API using default settings
    Exchange anx = ExchangeFactory.INSTANCE.createExchange(BitbayExchange.class.getName());

    System.out.println(anx.getMetaData().toString());

  }

}
