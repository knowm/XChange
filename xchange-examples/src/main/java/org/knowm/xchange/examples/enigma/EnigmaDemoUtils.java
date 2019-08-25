package org.knowm.xchange.examples.enigma;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.enigma.EnigmaExchange;
import org.knowm.xchange.enigma.service.EnigmaAccountService;

public class EnigmaDemoUtils {

  private static String username = "iSemyonova";
  private static String password = "irinaEnigmaSecuritiesRestApi123!";
  private static String infra = "dev";

  public static Exchange createExchange() {

    EnigmaExchange enigmaExchange = new EnigmaExchange();
    ExchangeSpecification exchangeSpec = enigmaExchange.getDefaultExchangeSpecification();
    exchangeSpec.setExchangeSpecificParametersItem("infra", infra);
    exchangeSpec.setUserName(username);
    exchangeSpec.setPassword(password);
    enigmaExchange.applySpecification(exchangeSpec);
    try {
      ((EnigmaAccountService) enigmaExchange.getAccountService()).login();
    } catch (IOException e) {
      throw new RuntimeException("Login exception", e);
    }
    return enigmaExchange;
  }
}
