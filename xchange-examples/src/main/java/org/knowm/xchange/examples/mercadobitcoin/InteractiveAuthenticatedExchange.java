package org.knowm.xchange.examples.mercadobitcoin;

import java.util.Scanner;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.mercadobitcoin.MercadoBitcoinExchange;

/** @author Felipe Micaroni Lalli */
public class InteractiveAuthenticatedExchange {

  public static Exchange createInstanceFromDefaultInput() {
    Exchange mercadoBitcoin =
        ExchangeFactory.INSTANCE.createExchange(MercadoBitcoinExchange.class.getName());
    ExchangeSpecification exchangeSpecification = mercadoBitcoin.getExchangeSpecification();

    Scanner s = new Scanner(System.in);

    System.out.println("Enter your API key (chave):");
    exchangeSpecification.setApiKey(s.next().trim().toLowerCase());

    System.out.println("Enter your secret code (código):");
    exchangeSpecification.setSecretKey(s.next().trim().toLowerCase());

    System.out.println("Enter your PIN (IMPORTANT: This is your PIN, not your user password):");
    exchangeSpecification.setPassword(s.next().trim().toLowerCase());

    exchangeSpecification.setUserName("<optional username>");

    System.out.println("Please wait...");

    return mercadoBitcoin;
  }
}
