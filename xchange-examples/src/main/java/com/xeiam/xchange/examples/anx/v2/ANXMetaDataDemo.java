package com.xeiam.xchange.examples.anx.v2;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.anx.v2.ANXExchange;

public class ANXMetaDataDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get ANX exchange API using default settings
    Exchange anx = ExchangeFactory.INSTANCE.createExchange(ANXExchange.class.getName());
    System.out.println(anx.getMetaData().toString());

    ExchangeSpecification exSpec = new ExchangeSpecification(ANXExchange.class);
    exSpec.setMetaDataJsonFileOverride("/tmp/anxpro.json");
    anx = ExchangeFactory.INSTANCE.createExchange(exSpec);
    System.out.println(anx.getMetaData().toString());

  }

}
