package org.knowm.xchange.deribit.v2.service;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.knowm.xchange.deribit.v2.DeribitExchange;
import org.knowm.xchange.deribit.v2.dto.account.DeribitAccountSummary;
import org.knowm.xchange.deribit.v2.dto.account.DeribitDeposit;
import org.knowm.xchange.deribit.v2.dto.account.DeribitTransactionLog;
import org.knowm.xchange.deribit.v2.dto.account.DeribitTransfer;
import org.knowm.xchange.deribit.v2.dto.account.DeribitWithdrawal;

public class DeribitAccountServiceRaw extends DeribitBaseService {

  public DeribitAccountServiceRaw(DeribitExchange exchange) {
    super(exchange);
  }

  public DeribitAccountSummary getAccountSummary(String currency, Boolean extended)
      throws IOException {
    return deribitAuthenticated.getAccountSummary(currency, extended, deribitDigest).getResult();
  }

  public List<DeribitAccountSummary> getAccountSummaries(Boolean extended) throws IOException {
    return deribitAuthenticated
        .getAccountSummaries(extended, deribitDigest)
        .getResult()
        .getAccountSummaries();
  }

  public List<DeribitDeposit> getDeposits(String currency, Integer count, Long offset)
      throws IOException {
    return deribitAuthenticated
        .getDeposits(currency, count, offset, deribitDigest)
        .getResult()
        .getData();
  }

  public List<DeribitTransfer> getTransfers(String currency, Integer count, Long offset)
      throws IOException {
    return deribitAuthenticated
        .getTransfers(currency, count, offset, deribitDigest)
        .getResult()
        .getData();
  }

  public List<DeribitWithdrawal> getWithdrawals(String currency, Integer count, Long offset)
      throws IOException {
    return deribitAuthenticated
        .getWithdrawals(currency, count, offset, deribitDigest)
        .getResult()
        .getData();
  }

  public List<DeribitTransactionLog> getTransactionLogs(
      String currency, Instant startTime, Instant endTime, Integer count) throws IOException {
    long start = Optional.ofNullable(startTime).map(Instant::toEpochMilli).orElse(0L);
    long end =
        Optional.ofNullable(endTime)
            .map(Instant::toEpochMilli)
            .orElse(Instant.now().toEpochMilli());
    return deribitAuthenticated
        .getTransactionLogs(currency, start, end, count, deribitDigest)
        .getResult()
        .getLogs();
  }
}
