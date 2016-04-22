package org.knowm.xchange.examples.mexbt;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.mexbt.MeXBTExchange;

public class MeXBTDemoUtils {

  public static Exchange createExchange(String[] args) {
    String username = args[0], publicKey = args[1], privateKey = args[2];
    Exchange exchange = MeXBTDemoUtils.createExchange(username, publicKey, privateKey);
    return exchange;
  }

  public static Exchange createExchange(String username, String publicKey, String privateKey) {
    ExchangeSpecification exSpec = new MeXBTExchange().getDefaultExchangeSpecification();
    exSpec.setExchangeSpecificParametersItem(MeXBTExchange.PRIVATE_API_URI_KEY, "https://private-api-sandbox.mexbt.com");
    exSpec.setUserName(username);
    exSpec.setApiKey(publicKey);
    exSpec.setSecretKey(privateKey);
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }

}
