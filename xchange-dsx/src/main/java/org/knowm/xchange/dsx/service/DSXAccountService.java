package org.knowm.xchange.dsx.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dsx.DSXAdapters;
import org.knowm.xchange.dsx.dto.account.DSXAccountInfo;
import org.knowm.xchange.dsx.dto.account.DSXTransaction;
import org.knowm.xchange.dsx.dto.account.DSXTransactionHistoryParams;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.utils.DateUtils;

/**
 * @author Mikhail Wall
 */
public class DSXAccountService extends DSXAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public DSXAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    DSXAccountInfo info = getDSXAccountInfo();
    return new AccountInfo(DSXAdapters.adaptWallet(info));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
      String c = currency.getCurrencyCode();
      // currently DSX support 3 fiat currencies: EUR, USD, RUB
      boolean fiat = "EUR".equals(currency) || "USD".equals(currency) || "RUB".equals(currency);
      long transactionId = fiat
              ? withdrawFiat(c, amount)
              : withdrawCrypto(c, address, amount, null);
      return Long.toString(transactionId);
  }
  
  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    return requestAddress(currency.toString(), Integer.parseInt(args[0]));
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new DSXTransactionHistoryParams();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws ExchangeException,
          NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
      Long from = null;
      Long to = null;
      Long fromId = null;
      Long toId = null;
      String currency = null;

      if (params instanceof TradeHistoryParamsTimeSpan) {
          TradeHistoryParamsTimeSpan timeSpan = (TradeHistoryParamsTimeSpan) params;
          from = nullSafeUnixTime(timeSpan.getStartTime());
          to = nullSafeUnixTime(timeSpan.getEndTime());
      }
      if (params instanceof TradeHistoryParamsIdSpan) {
          TradeHistoryParamsIdSpan idSpan = (TradeHistoryParamsIdSpan) params;
          fromId = nullSafeToLong(idSpan.getStartId());
          toId = nullSafeToLong(idSpan.getEndId());
      }
      if (params instanceof TradeHistoryParamCurrency) {
          Currency c = ((TradeHistoryParamCurrency) params).getCurrency();
          currency = c == null ? null : c.getCurrencyCode();
      }

      List<FundingRecord> result = new ArrayList<>();
      for (DSXTransaction t : getDSXTransHistory(from, to, fromId, toId, null, null, currency)) {
          result.add(new FundingRecord(t.getAddress(), t.getTimestamp(), Currency.getInstance(t.getCurrency()), t.getAmount(),
              Long.toString(t.getId()), null, convert(t.getType()), convert(t.getStatus()), null, t.getCommission(), null));
      }
      return result;
  }

  private FundingRecord.Status convert(DSXTransaction.Status status) {
      switch (status) {
      case Completed:
          return FundingRecord.Status.COMPLETE;
      case Failed:
          return FundingRecord.Status.FAILED;
      case Processing:
          return FundingRecord.Status.PROCESSING;
      case Rejected:
          return FundingRecord.Status.CANCELLED;
      default:
          throw new RuntimeException("Unknown DSX transaction status: " + status);
      }
  }

  private FundingRecord.Type convert(DSXTransaction.Type type) {
      switch (type) {
      case Incoming: return FundingRecord.Type.DEPOSIT;
      case Withdraw: return FundingRecord.Type.WITHDRAWAL;
      default:
          throw new RuntimeException("Unknown DSX transaction type: " + type);
      }
  }

  private static Long nullSafeUnixTime(Date time) {
      return time != null ? DateUtils.toUnixTime(time) : null;
  }

  private static Long nullSafeToLong(String str) {
      try {
          return (str == null || str.isEmpty()) ? null : Long.valueOf(str);
      } catch (NumberFormatException e) {
          return null;
      }
  }
}
