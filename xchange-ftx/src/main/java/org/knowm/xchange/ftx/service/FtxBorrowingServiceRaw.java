package org.knowm.xchange.ftx.service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.ftx.FtxExchange;
import org.knowm.xchange.ftx.dto.account.FtxBorrowingHistoryDto;
import org.knowm.xchange.ftx.dto.account.FtxBorrowingInfoDto;
import org.knowm.xchange.ftx.dto.account.FtxBorrowingRatesDto;

public class FtxBorrowingServiceRaw extends FtxBaseService {

  public FtxBorrowingServiceRaw(FtxExchange exchange) {
    super(exchange);
  }

  public List<FtxBorrowingHistoryDto> histories(String subaccount) {
    try {
      return ftx.getBorrowHistory(
              exchange.getExchangeSpecification().getApiKey(),
              exchange.getNonceFactory().createValue(),
              signatureCreator,
              subaccount,
              null,
              null)
          .getResult();
    } catch (IOException e) {
      throw new FtxLendingServiceRaw.FtxLendingServiceException(
          "Can't get lending infos subAccount: " + subaccount, e);
    }
  }

  public List<FtxBorrowingHistoryDto> historiesByDates(
      String subAccount, Long startTime, Long endTime) {
    try {
      return ftx.getBorrowHistory(
              exchange.getExchangeSpecification().getApiKey(),
              exchange.getNonceFactory().createValue(),
              signatureCreator,
              subAccount,
              startTime,
              endTime)
          .getResult();
    } catch (IOException e) {
      throw new FtxLendingServiceRaw.FtxLendingServiceException(
          "Can't get lending infos subAccount: " + subAccount, e);
    }
  }

  public List<FtxBorrowingHistoryDto> histories(String subaccount, List<String> coins) {
    Objects.requireNonNull(coins);
    return histories(subaccount).stream()
        .filter(lendingHistory -> coins.contains(lendingHistory.getCoin()))
        .collect(Collectors.toList());
  }

  public FtxBorrowingHistoryDto history(String subaccount, String coin) {
    Objects.requireNonNull(coin);
    if (StringUtils.isNotBlank(coin))
      throw new FtxBorrowingServiceException("Coin are blank or empty");
    return histories(subaccount).stream()
        .filter(lendingHistory -> lendingHistory.getCoin().equalsIgnoreCase(coin))
        .findFirst()
        .orElse(null);
  }

  public List<FtxBorrowingInfoDto> infos(String subaccount) {
    try {
      return ftx.getBorrowingInfos(
              exchange.getExchangeSpecification().getApiKey(),
              exchange.getNonceFactory().createValue(),
              signatureCreator,
              subaccount)
          .getResult();
    } catch (IOException e) {
      throw new FtxLendingServiceRaw.FtxLendingServiceException("Can't get lending infos", e);
    }
  }

  public List<FtxBorrowingInfoDto> infos(String subaccount, List<String> coins) {
    Objects.requireNonNull(coins);
    return infos(subaccount).stream()
        .filter(lendingInfo -> coins.contains(lendingInfo.getCoin()))
        .collect(Collectors.toList());
  }

  public FtxBorrowingInfoDto info(String subaccount, String coin) {
    Objects.requireNonNull(coin);
    if (StringUtils.isNotBlank(coin))
      throw new FtxBorrowingServiceException("Coin are blank or empty");
    return infos(subaccount).stream()
        .filter(lendingInfo -> lendingInfo.getCoin().equalsIgnoreCase(coin))
        .findFirst()
        .orElse(null);
  }

  public List<FtxBorrowingRatesDto> rates() {
    try {
      return ftx.getBorrowRates(
              exchange.getExchangeSpecification().getApiKey(),
              exchange.getNonceFactory().createValue(),
              signatureCreator)
          .getResult();
    } catch (IOException e) {
      throw new FtxLendingServiceRaw.FtxLendingServiceException("Can't get lending rates", e);
    }
  }

  public List<FtxBorrowingRatesDto> rates(List<String> coins) {
    Objects.requireNonNull(coins);
    return rates().stream()
        .filter(lendingRates -> coins.contains(lendingRates.getCoin()))
        .collect(Collectors.toList());
  }

  public FtxBorrowingRatesDto rate(String coin) {
    Objects.requireNonNull(coin);
    if (StringUtils.isNotBlank(coin))
      throw new FtxBorrowingServiceException("Coin are blank or empty");
    try {
      return ftx
          .getBorrowRates(
              exchange.getExchangeSpecification().getApiKey(),
              exchange.getNonceFactory().createValue(),
              signatureCreator)
          .getResult()
          .stream()
          .filter(lendingRates -> lendingRates.getCoin().equalsIgnoreCase(coin))
          .findFirst()
          .orElse(null);
    } catch (IOException e) {
      throw new FtxLendingServiceRaw.FtxLendingServiceException(
          "Can't get lending rate coin: " + coin, e);
    }
  }

  public static class FtxBorrowingServiceException extends RuntimeException {
    public FtxBorrowingServiceException(String message, Throwable cause) {
      super(message, cause);
    }

    public FtxBorrowingServiceException(String message) {
      super(message);
    }
  }
}
