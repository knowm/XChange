package org.knowm.xchange.test.getbtc;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.getbtc.GetbtcExchange;

public class GetbtcConfig {
  public static Exchange getExchange() throws IOException {      
	    Exchange getbtc = ExchangeFactory.INSTANCE.createExchange(GetbtcExchange.class.getName());
	    
	    ExchangeSpecification exchangeSpecification = getbtc.getExchangeSpecification();
	    exchangeSpecification.setApiKey(apiKey());
	    exchangeSpecification.setSecretKey(secretKey());
	    getbtc.applySpecification(exchangeSpecification);

    return getbtc;
  }
    
  private static String apiKey() {
	return "4LFNrkVQW7iPgp0x";
  }
  
  private static String secretKey() {
	return "WKsXO98bICDxwYpHjiN3T7tedZ14gAuM";
  }
}
