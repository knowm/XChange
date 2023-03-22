package org.knowm.xchange.btcmarkets.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcmarkets.BTCMarketsAdapters;
import org.knowm.xchange.btcmarkets.dto.v3.account.BTCMarketsAddressesResponse;
import org.knowm.xchange.btcmarkets.dto.v3.account.BTCMarketsTradingFeesResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.RippleWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

/** @author Matija Mazi */
public class BTCMarketsAccountService extends BTCMarketsAccountServiceRaw
    implements AccountService {

  public BTCMarketsAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return new AccountInfo(
        exchange.getExchangeSpecification().getUserName(),
        BTCMarketsAdapters.adaptWallet(getBTCMarketsBalance()));
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultWithdrawFundsParams = (DefaultWithdrawFundsParams) params;
      String address = defaultWithdrawFundsParams.getAddress();
      if (params instanceof RippleWithdrawFundsParams) {
        address = address + "?dt=" + ((RippleWithdrawFundsParams) params).tag;
      }
      return withdrawCrypto(
          address,
          defaultWithdrawFundsParams.getAmount(),
          defaultWithdrawFundsParams.getCurrency());
    }
    throw new IllegalStateException("Cannot process " + params);
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new BTCMarketsTradeHistoryParams();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    return BTCMarketsAdapters.adaptFundingHistory(super.fundtransferHistory());
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    BTCMarketsAddressesResponse response = depositAddress(currency);
    if (response != null) {
      return response.address;
    } else {
      return null;
    }
  }

  @Override
  public Map<Instrument, Fee> getDynamicTradingFeesByInstrument() throws IOException {
    BTCMarketsTradingFeesResponse response = tradingFees();
    Map<Instrument, Fee> dynamicTradingFees = new HashMap<>();
    for (BTCMarketsTradingFeesResponse.FeeByMarket feeByMarket : response.feeByMarkets) {
      String[] splitMarketId = feeByMarket.marketId.split("-"); // BTC-AUD
      CurrencyPair cp = new CurrencyPair(splitMarketId[0], splitMarketId[1]);
      Fee fee = new Fee(feeByMarket.makerFeeRate, feeByMarket.takerFeeRate);

      dynamicTradingFees.put(cp, fee);
    }
    return dynamicTradingFees;
  }
}
