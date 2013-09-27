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
    specification.setSecretKey("72jljpmHoGv9973tzBeSBamNgGkew1LxJRPPVjiC0nzt40Vj0gt/uc47O+TxPzH2cPaY7MbQeZ8pcV3nm2ClCg==");
    specification.setUserName("XChange");
    kraken.applySpecification(specification);
    return kraken;
  }
}
