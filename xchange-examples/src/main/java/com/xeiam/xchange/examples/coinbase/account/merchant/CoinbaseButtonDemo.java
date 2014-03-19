/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.examples.coinbase.account.merchant;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinbase.dto.merchant.CoinbaseButton;
import com.xeiam.xchange.coinbase.dto.merchant.CoinbaseButton.CoinbaseButtonBuilder;
import com.xeiam.xchange.coinbase.dto.merchant.CoinbaseButtonStyle;
import com.xeiam.xchange.coinbase.dto.merchant.CoinbaseButtonType;
import com.xeiam.xchange.coinbase.dto.merchant.CoinbaseOrder;
import com.xeiam.xchange.coinbase.service.polling.CoinbaseAccountService;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.examples.coinbase.CoinbaseDemoUtils;

/**
 * @author jamespedwards42
 */
public class CoinbaseButtonDemo {

  public static void main(String[] args) throws IOException {

    Exchange coinbase = CoinbaseDemoUtils.createExchange();
    CoinbaseAccountService accountService = (CoinbaseAccountService) coinbase.getPollingAccountService();

    CoinbaseButtonBuilder buttonBuilder = new CoinbaseButtonBuilder("Demo Button", Currencies.BTC, ".001");
    buttonBuilder.withDescription("Coinbase button demo for Coinbase.").withIncludeEmail(true).withStyle(CoinbaseButtonStyle.DONATION_LARGE).withType(CoinbaseButtonType.DONATION).withVariablePrice(
        true);

    CoinbaseButton createdButton = accountService.createCoinbaseButton(buttonBuilder.buildButton());
    System.out.println(createdButton);

    CoinbaseOrder createdOrder = accountService.createCoinbaseOrder(createdButton.getCode());
    System.out.println(createdOrder);

    createdOrder = accountService.createCoinbaseOrder(createdButton);
    System.out.println(createdOrder);
  }
}
