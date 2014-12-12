package com.xeiam.xchange.examples.bitcointoyou;

import java.util.Scanner;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcointoyou.BitcoinToYouExchange;

/**
 * @author Felipe Micaroni Lalli
 */
public class InteractiveAuthenticatedExchange {

  public static Exchange createInstanceFromDefaultInput() {

    Exchange bitcoinToYou = ExchangeFactory.INSTANCE.createExchange(BitcoinToYouExchange.class.getName());
    ExchangeSpecification exchangeSpecification = bitcoinToYou.getExchangeSpecification();

    Scanner s = new Scanner(System.in);

    System.out.println("Enter your API key (chave):");
    exchangeSpecification.setApiKey(s.next().trim().toLowerCase());

    System.out.println("Enter your secret code (código secreto):");
    exchangeSpecification.setSecretKey(s.next().trim().toUpperCase());

    exchangeSpecification.setUserName("<optional username>");

    System.out.println("Please wait...");

    return bitcoinToYou;
  }
}
