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
package com.xeiam.xchange.coinbase;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.money.BigMoney;

import com.xeiam.xchange.coinbase.dto.account.CoinbaseUser;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseCurrency;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransfer;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransferType;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransfers;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.Wallet;

/**
 * jamespedwards42
 */
public final class CoinbaseAdapters {

  private CoinbaseAdapters() {
  }

  public static AccountInfo adaptAccountInfo(final CoinbaseUser user) {
    
    final String username = user.getEmail();
    final BigMoney balance = user.getBalance();
    final Wallet wallet = new Wallet(balance.getCurrencyUnit().getCode(), balance);
    final List<Wallet> wallets = new ArrayList<Wallet>();
    wallets.add(wallet);
    
    final AccountInfo accountInfo = new AccountInfo(username, wallets);
    return accountInfo;
  }

  public static Trades adaptTrades(final CoinbaseTransfers transfers) {
    
    final List<Trade> trades = new ArrayList<Trade>();
    for (final CoinbaseTransfer transfer : transfers.getTransfers()) 
      trades.add(adaptTrade(transfer));
    
    final Trades adaptedTrades = new Trades(trades);
    return adaptedTrades;
  }

  public static Trade adaptTrade(final CoinbaseTransfer transfer) {
    
    final OrderType orderType = adaptOrderType(transfer.getType());
    final BigMoney btcAmount = transfer.getBtcAmount();
    final BigDecimal tradableAmount = btcAmount.getAmount();
    final String tradableIdentifier = btcAmount.getCurrencyUnit().getCode();
    final BigMoney subTotal = transfer.getSubtotal();
    final String transactionCurrency = subTotal.getCurrencyUnit().getCode();
    final BigMoney price = subTotal.dividedBy(tradableAmount, RoundingMode.HALF_EVEN);
    final Date timestamp = transfer.getCreatedAt();
    final String id = transfer.getTransactionId();
    
    final Trade adaptedTrade = new Trade(orderType, tradableAmount, tradableIdentifier, transactionCurrency, price, timestamp, id, id);
    return adaptedTrade;
  }
  
  public static OrderType adaptOrderType(final CoinbaseTransferType transferType) {
    switch(transferType) {
    case BUY:
      return OrderType.BID;
    case SELL:
      return OrderType.ASK;
    }
    return null;
  }
  
  public static List<CurrencyPair> adaptCurrencyPairs(final List<CoinbaseCurrency> currencies) {
    
    final List<CurrencyPair> currencyPairs = new ArrayList<CurrencyPair>();
    for (final CoinbaseCurrency currency : currencies) {
      final CurrencyPair currencyPair = new CurrencyPair("BTC", currency.getIsoCode());
      currencyPairs.add(currencyPair);
    }
    
    return currencyPairs;
  }
}
