package org.knowm.xchange.examples.coinbase.account.merchant;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbase.dto.merchant.CoinbaseButton;
import org.knowm.xchange.coinbase.dto.merchant.CoinbaseButton.CoinbaseButtonBuilder;
import org.knowm.xchange.coinbase.dto.merchant.CoinbaseButtonStyle;
import org.knowm.xchange.coinbase.dto.merchant.CoinbaseButtonType;
import org.knowm.xchange.coinbase.dto.merchant.CoinbaseOrder;
import org.knowm.xchange.coinbase.service.CoinbaseAccountService;
import org.knowm.xchange.examples.coinbase.CoinbaseDemoUtils;

/** @author jamespedwards42 */
public class CoinbaseButtonDemo {

  public static void main(String[] args) throws IOException {

    Exchange coinbase = CoinbaseDemoUtils.createExchange();
    CoinbaseAccountService accountService = (CoinbaseAccountService) coinbase.getAccountService();

    CoinbaseButtonBuilder buttonBuilder = new CoinbaseButtonBuilder("Demo Button", "BTC", ".001");
    buttonBuilder
        .withDescription("Coinbase button demo for Coinbase.")
        .withIncludeEmail(true)
        .withStyle(CoinbaseButtonStyle.DONATION_LARGE)
        .withType(CoinbaseButtonType.DONATION)
        .withVariablePrice(true);

    CoinbaseButton createdButton = accountService.createCoinbaseButton(buttonBuilder.buildButton());
    System.out.println(createdButton);

    CoinbaseOrder createdOrder = accountService.createCoinbaseOrder(createdButton.getCode());
    System.out.println(createdOrder);

    createdOrder = accountService.createCoinbaseOrder(createdButton);
    System.out.println(createdOrder);
  }
}
