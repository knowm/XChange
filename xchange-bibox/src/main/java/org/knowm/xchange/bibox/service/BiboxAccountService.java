package org.knowm.xchange.bibox.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bibox.dto.BiboxAdapters;
import org.knowm.xchange.bibox.dto.account.BiboxFundsCommandBody;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

/** @author odrotleff */
public class BiboxAccountService extends BiboxAccountServiceRaw implements AccountService {

  public BiboxAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    // TODO could be improved with a batched call to get other infos
    return BiboxAdapters.adaptAccountInfo(getBiboxAccountInfo());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    throw new NotYetImplementedForExchangeException(
        "This operation is not yet implemented for this exchange");
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    throw new NotYetImplementedForExchangeException(
        "This operation is not yet implemented for this exchange");
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    return requestBiboxDepositAddress(currency);
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new BiboxFundingHistoryParams();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) {

    if (!(params instanceof TradeHistoryParamCurrency)) {
      throw new RuntimeException("You must provide the currency for funding history @ Bibox.");
    }
    Currency c = ((TradeHistoryParamCurrency) params).getCurrency();
    if (c == null) {
      throw new RuntimeException("You must provide the currency for funding history @ Bibox.");
    }

    boolean deposits = false;
    boolean withdrawals = false;
    if (params instanceof HistoryParamsFundingType) {
      HistoryParamsFundingType typeParams = (HistoryParamsFundingType) params;
      Type type = typeParams.getType();
      deposits = type == null || type == Type.DEPOSIT;
      withdrawals = type == null || type == Type.WITHDRAWAL;
    }
    BiboxFundsCommandBody body = new BiboxFundsCommandBody(c.getCurrencyCode());
    ArrayList<FundingRecord> result = new ArrayList<>();
    if (deposits) {
      requestBiboxDeposits(body).getItems().forEach(d -> result.add(BiboxAdapters.adaptDeposit(d)));
    }
    if (withdrawals) {
      requestBiboxWithdrawals(body)
          .getItems()
          .forEach(d -> result.add(BiboxAdapters.adaptDeposit(d)));
    }
    return result;
  }

  public static class BiboxFundingHistoryParams
      implements TradeHistoryParamCurrency, HistoryParamsFundingType {

    private Type type;
    private Currency currency;

    @Override
    public Type getType() {
      return type;
    }

    @Override
    public void setType(Type type) {
      this.type = type;
    }

    @Override
    public Currency getCurrency() {
      return currency;
    }

    @Override
    public void setCurrency(Currency currency) {
      this.currency = currency;
    }
  }
}
