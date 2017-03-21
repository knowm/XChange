package org.knowm.xchange.bitfinex.v1.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitfinex.v1.BitfinexAdapters;
import org.knowm.xchange.bitfinex.v1.BitfinexUtils;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexDepositAddressResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundsInfo;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

public class BitfinexAccountService extends BitfinexAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitfinexAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return new AccountInfo(BitfinexAdapters.adaptWallet(getBitfinexAccountInfo()));
  }

  /**
   * Withdrawal suppport
   *
   * @param currency
   * @param amount
   * @param address
   * @return
   * @throws IOException
   */
  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
    //determine withdrawal type
    String type = BitfinexUtils.convertToBitfinexWithdrawalType(currency.toString());
    //Bitfinex withdeawal can be from different type of wallets    *
    // we have to use one of these for now: Exchange -
    //to be able to withdraw instantly after trading for example
    //The wallet to withdraw from, can be “trading”, “exchange”, or “deposit”.
    String walletSelected = "exchange";
    //We have to convert XChange currencies to Bitfinex currencies: can be “bitcoin”, “litecoin” or “ether” or “tether” or “wire”.
    return withdraw(type, walletSelected, amount, address);
  }

  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {
    final BitfinexDepositAddressResponse response = super.requestDepositAddressRaw(currency.getCurrencyCode());
    return response.getAddress();
  }

  @Override
  public FundsInfo getFundsInfo(TradeHistoryParams params) throws IOException {
    BitfinexFundsInfoHistoryParams histParams = (BitfinexFundsInfoHistoryParams) params;

    return BitfinexAdapters.adaptFundsInfo(getDepositWithdrawalHistory(histParams.getCcy().getCurrencyCode(),
            null, histParams.getStartTime(), histParams.getEndTime(), histParams.getLimit()));
  }

  public static class BitfinexFundsInfoHistoryParams extends DefaultTradeHistoryParamsTimeSpan {

    private final Integer limit;
    private final Currency ccy;

    public BitfinexFundsInfoHistoryParams(final Date startTime, final Date endTime, final Integer limit, final Currency ccy) {

      super(startTime, endTime);

      this.limit = limit;
      this.ccy = ccy;
    }

    public Integer getLimit() {
      return limit;
    }

    public Currency getCcy() {
      return ccy;
    }
  }
}
