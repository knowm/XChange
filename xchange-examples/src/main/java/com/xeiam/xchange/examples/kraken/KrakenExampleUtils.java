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
    specification.setApiKey("rOkckzK+auTaBmwjbN1NPkLr6W0RcCo0ckpdylNavNyR+ZRv/RyDil4K");
    specification.setSecretKey("GTU3yVFB22zeWsN/sAUfmN3PgKU2lyces2IVuc7Ay0o1Qb9imFycboXYMwhzsq7YICJO5O9UkyZyUBkye4g5sA==");
    specification.setUserName("XChange");
    kraken.applySpecification(specification);
    return kraken;
  }
}
