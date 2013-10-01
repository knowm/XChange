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
    specification.setApiKey("hklrCQOFOVnXFYwrkwqjpQ7VFLk7SBcunrpQ40lT1j4b3k9yOSsG53l4");
    specification.setSecretKey("0iELUjTasQrBjWiXUTAuw3dbD31jc60QQGs816AZn0F1t+6Q6fZ4r/2Sx2FZaCdgzZei5V4uPEGAaxKKie3uiA==");
    specification.setUserName("XChange");
    kraken.applySpecification(specification);
    return kraken;
  }
}
