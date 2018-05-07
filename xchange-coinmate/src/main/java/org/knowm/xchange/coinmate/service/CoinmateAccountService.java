/*
 * The MIT License
 *
 * Copyright 2015 Coinmate.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.knowm.xchange.coinmate.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinmate.CoinmateAdapters;
import org.knowm.xchange.coinmate.dto.account.CoinmateDepositAddresses;
import org.knowm.xchange.coinmate.dto.trade.CoinmateTradeResponse;
import org.knowm.xchange.coinmate.dto.trade.CoinmateTransactionHistory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

/** @author Martin Stachon */
public class CoinmateAccountService extends CoinmateAccountServiceRaw implements AccountService {

  public static final int DEFAULT_RESULT_LIMIT = 100;

  public CoinmateAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return new AccountInfo(CoinmateAdapters.adaptWallet(getCoinmateBalance()));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    CoinmateTradeResponse response = coinmateBitcoinWithdrawal(amount, address);

    return Long.toString(response.getData());
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
      return withdrawFunds(
          defaultParams.getCurrency(), defaultParams.getAmount(), defaultParams.getAddress());
    }
    throw new IllegalStateException("Don't know how to withdraw: " + params);
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    CoinmateDepositAddresses addresses = coinmateBitcoinDepositAddresses();

    if (addresses.getData().isEmpty()) {
      return null;
    } else {
      // here we return only the first address
      return addresses.getData().get(0);
    }
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new CoinmateFundingHistoryParams();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {

    TradeHistoryParamsSorted.Order order = TradeHistoryParamsSorted.Order.asc;
    Integer limit = 1000;
    int offset = 0;

    if (params instanceof TradeHistoryParamOffset) {
      offset = Math.toIntExact(((TradeHistoryParamOffset) params).getOffset());
    }

    if (params instanceof TradeHistoryParamLimit) {
      limit = ((TradeHistoryParamLimit) params).getLimit();
    }

    if (params instanceof TradeHistoryParamsSorted) {
      order = ((TradeHistoryParamsSorted) params).getOrder();
    }

    CoinmateTransactionHistory coinmateTransactionHistory =
        getCoinmateTransactionHistory(offset, limit, CoinmateAdapters.adaptSortOrder(order));
    return CoinmateAdapters.adaptFundingHistory(coinmateTransactionHistory);
  }

  public static class CoinmateFundingHistoryParams
      extends CoinmateTradeService.CoinmateTradeHistoryHistoryParams {}
}
