package com.xeiam.xchange.examples.kraken;

import org.xchange.kraken.KrakenExchange;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;

public class KrakenExampleUtils {
  private KrakenExampleUtils(){
    
  }
  public static Exchange createTestExchange() {

    Exchange kraken = ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class.getName());
    ExchangeSpecification specification = kraken.getDefaultExchangeSpecification();
    specification.setApiKey("cUQtvjUTeflRAyjXeQVhWTInsZh/VMLcCrugPX4nSpgqs0hadPJQATUc");
    specification.setSecretKey("BMTodAUWigFa+wCEEdjvFM03jAa0oovRoLKbFjjvkiQOcJmvrQoNs6mMTvS/vQMQzHfQI5EhGgfWftSES1CfkQ==");
    specification.setUserName("XChange");
    kraken.applySpecification(specification);
    return kraken;
  }
}
