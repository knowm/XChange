package org.knowm.xchange.deribit.v2.service;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.deribit.v2.DeribitAdapters;
import org.knowm.xchange.deribit.v2.DeribitExchange;
import org.knowm.xchange.deribit.v2.service.params.DeribitFundingHistoryParams;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

public class DeribitAccountService extends DeribitAccountServiceRaw implements AccountService {

  public DeribitAccountService(DeribitExchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    var balances = getAccountSummaries(false).stream()
        .map(DeribitAdapters::adapt)
        .collect(Collectors.toList());
    Wallet wallet = Wallet.Builder.from(balances).id("main").build();

    var openPositions = exchange.getTradeService().getOpenPositions().getOpenPositions();

    return new AccountInfo(null, null, Collections.singleton(wallet), openPositions, null);
  }


  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    Collection<Currency> currencies = null;

    if (params instanceof TradeHistoryParamCurrency) {
      var currency = ((TradeHistoryParamCurrency) params).getCurrency();
      if (currency != null) {
        currencies = List.of(currency);
      }
    }

    if (currencies == null) {
      currencies = currencies();
    }

    Integer limit = null;
    if (params instanceof TradeHistoryParamLimit) {
      limit = ((TradeHistoryParamLimit) params).getLimit();
    }

    Instant startTime = null;
    Instant endTime = null;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      startTime = Optional.of(params)
          .map(TradeHistoryParamsTimeSpan.class::cast)
          .map(TradeHistoryParamsTimeSpan::getStartTime)
          .map(Date::toInstant)
          .orElse(null);
      endTime = Optional.of(params)
          .map(TradeHistoryParamsTimeSpan.class::cast)
          .map(TradeHistoryParamsTimeSpan::getEndTime)
          .map(Date::toInstant)
          .orElse(null);
    }

    List<FundingRecord> fundingRecords = new ArrayList<>();
    for (Currency currency : currencies) {
      String currencyCode = currency.getCurrencyCode();

      getTransactionLogs(currencyCode, startTime, endTime, limit).stream()
          .map(DeribitAdapters::toFundingRecord)
          .forEach(fundingRecords::add);
    }

    return fundingRecords;
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return DeribitFundingHistoryParams.builder().build();
  }

  Collection<Currency> currencies() {
    return exchange.getExchangeMetaData().getCurrencies().keySet();
  }
}
