/**
 * Copyright (C) 2013 Matija Mazi
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.bitcoincentral;

import java.util.Arrays;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import com.xeiam.xchange.bitcoincentral.dto.account.BitcoinCentralAccountInfo;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.trade.Wallet;

/**
 * Various adapters for converting from Bitcoin Central DTOs to XChange DTOs
 */
public final class BitcoinCentralAdapters {

  /**
   * private Constructor
   */
  private BitcoinCentralAdapters() {

  }

  /**
   * Adapts a BitcoinCentralAccountInfo to a AccountInfo
   */
  public static AccountInfo adaptAccountInfo(BitcoinCentralAccountInfo accountInfo, String userName) {

    Wallet btcWallet = new Wallet("BTC", BigMoney.of(CurrencyUnit.of("BTC"), accountInfo.getBtc()));
    Wallet usdWallet = new Wallet("CAD", BigMoney.of(CurrencyUnit.CAD, accountInfo.getCad()));
    Wallet eurWallet = new Wallet("EUR", BigMoney.of(CurrencyUnit.EUR, accountInfo.getEur()));
    Wallet inrWallet = new Wallet("INR", BigMoney.of(CurrencyUnit.getInstance("INR"), accountInfo.getInr()));
    // todo: other currencies?

    return new AccountInfo(userName, Arrays.asList(btcWallet, usdWallet, eurWallet, inrWallet));
  }
}
