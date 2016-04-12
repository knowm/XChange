package org.knowm.xchange.examples.anx.v2;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.anx.v2.ANXExchange;
import org.knowm.xchange.dto.meta.ExchangeMetaData;

public class ANXMetaDataDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get ANX exchange API using default settings
    Exchange anx = ExchangeFactory.INSTANCE.createExchange(ANXExchange.class.getName());
    ExchangeMetaData metaData = anx.getMetaData();
    System.out.println(metaData.toString());
    System.out.println("private poll delay ms: " + ExchangeMetaData.getPollDelayMillis(metaData.getPrivateRateLimits()));
    System.out.println("public  poll delay ms: " + ExchangeMetaData.getPollDelayMillis(metaData.getPublicRateLimits()));

    ExchangeSpecification exSpec = new ExchangeSpecification(ANXExchange.class);
    exSpec.setMetaDataJsonFileOverride("/tmp/anxpro.json");
    anx = ExchangeFactory.INSTANCE.createExchange(exSpec);
    System.out.println(anx.getMetaData().toString());

  }

}
