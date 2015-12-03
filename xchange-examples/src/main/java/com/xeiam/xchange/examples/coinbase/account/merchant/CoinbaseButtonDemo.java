package com.xeiam.xchange.examples.coinbase.account.merchant;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinbase.dto.merchant.CoinbaseButton;
import com.xeiam.xchange.coinbase.dto.merchant.CoinbaseButton.CoinbaseButtonBuilder;
import com.xeiam.xchange.coinbase.dto.merchant.CoinbaseButtonStyle;
import com.xeiam.xchange.coinbase.dto.merchant.CoinbaseButtonType;
import com.xeiam.xchange.coinbase.dto.merchant.CoinbaseOrder;
import com.xeiam.xchange.coinbase.service.polling.CoinbaseAccountService;
import com.xeiam.xchange.examples.coinbase.CoinbaseDemoUtils;

/**
 * @author jamespedwards42
 */
public class CoinbaseButtonDemo {

  public static void main(String[] args) throws IOException {

    Exchange coinbase = CoinbaseDemoUtils.createExchange();
    CoinbaseAccountService accountService = (CoinbaseAccountService) coinbase.getPollingAccountService();

    CoinbaseButtonBuilder buttonBuilder = new CoinbaseButtonBuilder("Demo Button", "BTC", ".001");
    buttonBuilder.withDescription("Coinbase button demo for Coinbase.").withIncludeEmail(true).withStyle(CoinbaseButtonStyle.DONATION_LARGE)
        .withType(CoinbaseButtonType.DONATION).withVariablePrice(true);

    CoinbaseButton createdButton = accountService.createCoinbaseButton(buttonBuilder.buildButton());
    System.out.println(createdButton);

    CoinbaseOrder createdOrder = accountService.createCoinbaseOrder(createdButton.getCode());
    System.out.println(createdOrder);

    createdOrder = accountService.createCoinbaseOrder(createdButton);
    System.out.println(createdOrder);
  }
}
