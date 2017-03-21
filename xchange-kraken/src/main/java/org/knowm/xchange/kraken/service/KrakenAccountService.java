package org.knowm.xchange.kraken.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundsInfo;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.kraken.KrakenAdapters;
import org.knowm.xchange.kraken.dto.account.KrakenDepositAddress;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.utils.DateUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

public class KrakenAccountService extends KrakenAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public KrakenAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return new AccountInfo(exchange.getExchangeSpecification().getUserName(), KrakenAdapters.adaptWallet(getKrakenBalance()));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
    return withdraw(null, currency.toString(), address, amount).getRefid();
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    KrakenDepositAddress[] depositAddresses = getDepositAddresses(currency.toString(), "Bitcoin", false);
    return KrakenAdapters.adaptKrakenDepositAddress(depositAddresses);
  }

  @Override
  public FundsInfo getFundsInfo(TradeHistoryParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException{
    KrakenFundsInfoHistoryParams histParams = (KrakenFundsInfoHistoryParams) params;
    String startTime = null;
    if (histParams.getStartTime() != null){
      startTime = String.valueOf(DateUtils.toUnixTime(histParams.getStartTime()));
    }
    String endTime = null;
    if (histParams.getEndTime() != null){
      endTime = String.valueOf(DateUtils.toUnixTime(histParams.getEndTime()));
    }
    String offset = null;
    if (histParams.getOffset() != null){
      offset = String.valueOf(histParams.getOffset());
    }

    return KrakenAdapters.adaptFundsInfo(getKrakenLedgerInfo(null, startTime, endTime, offset, histParams.assets));
  }


  public static class KrakenFundsInfoHistoryParams extends DefaultTradeHistoryParamsTimeSpan implements TradeHistoryParamOffset{

    private Long offset;
    private Currency[] assets;

    public KrakenFundsInfoHistoryParams(final Date startTime, final Date endTime, final Long offset, final Currency... assets) {
      super(startTime, endTime);
      this.offset = offset;
      this.assets = assets;
    }

    @Override
    public void setOffset(final Long offset) {
      this.offset = offset;
    }

    @Override
    public Long getOffset() {
      return offset;
    }

    public Currency[] getAssets() {
      return assets;
    }
  }

}
